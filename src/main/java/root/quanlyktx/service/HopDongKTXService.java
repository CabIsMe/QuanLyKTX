package root.quanlyktx.service;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import root.quanlyktx.dto.HopDongKTXDTO;
import root.quanlyktx.dto.LoaiKTXDto;
import root.quanlyktx.dto.StudentDto;
import root.quanlyktx.entity.*;
import root.quanlyktx.model.ThongTinPhong;
import root.quanlyktx.model.ViewContractRoom;
import root.quanlyktx.model.ViewContractRoomList;
import root.quanlyktx.repository.*;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class HopDongKTXService {

    @Autowired
    private HopDongKTXRepository hopDongKTXRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PhongKTXRepository phongKTXRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private LoaiKTXService loaiKTXService;
    @Autowired
    private LoaiKTXRepository loaiKTXRepository;
    @Autowired
    private TermRepository termRepository;
    private static final Logger logger = LoggerFactory.getLogger(HopDongKTXService.class);

    @Transactional
//    @Scheduled(fixedRate = 2000)
    public void runAuto() throws Exception {
        // Code to run after the application has started
        Date date= new Date();
        Term term= termRepository.getByNgayMoDangKyBeforeAndNgayKetThucDangKyAfter(date,date);
        Date dateExpired= new Date(date.getTime()-(term.getHanDongPhi()*60*60*1000*24));
        logger.info(dateExpired.toString());
        if(hopDongKTXRepository.findAllByNgayLamDonBeforeAndTrangThaiFalse(dateExpired).isEmpty()){
            return;
        }
        hopDongKTXRepository.deleteAllByNgayLamDonBeforeAndTrangThaiFalse(dateExpired);
    }

    public List<HopDongKTXDTO> getAll() {
        List<HopDongKTX> hopDongKTXList = hopDongKTXRepository.findAll();
        logger.debug("OKLA");
        return hopDongKTXList.stream().map(hopDongKTX -> modelMapper.map(hopDongKTX, HopDongKTXDTO.class)).collect(Collectors.toList());
    }

    public HopDongKTXDTO getById(Integer id) {
        HopDongKTX hopDongKTX = hopDongKTXRepository.findById(id).get();

        return modelMapper.map(hopDongKTX, HopDongKTXDTO.class);
    }

    public List<HopDongKTXDTO> getByPhongKTX(Integer idPhongKTX) {
        List<HopDongKTX> hopDongKTXList = hopDongKTXRepository.findAllByIdPhongKTX(idPhongKTX);
        return hopDongKTXList.stream()
                .map(hopDongKTX -> modelMapper.map(hopDongKTX, HopDongKTXDTO.class))
                .collect(Collectors.toList());
    }


    public Integer countHopDongInPhong(Integer idPhong) {
        Date date= new Date();
        Term term= termRepository.getByNgayMoDangKyBeforeAndNgayKetThucDangKyAfter(date,date);
        if(term == null){
            return null;
        }
        return hopDongKTXRepository.countHopDongKTXByIdPhongKTXAndIdTerm(idPhong, term.getId());
    }
    public List<ThongTinPhong> thongTinPhongs(Integer idLoaiPhong){
        LoaiKTX loaiKTX=loaiKTXRepository.findLoaiKTXById(idLoaiPhong);
        List<PhongKTX> phongKTXDTOList=phongKTXRepository .findAllByIdLoaiKTX(idLoaiPhong);

        List<ThongTinPhong> thongTinPhongList= new ArrayList<>();
        for (PhongKTX phongKTX: phongKTXDTOList) {

            thongTinPhongList.add(new ThongTinPhong(phongKTX.getId(),loaiKTX.getGiaPhong(),
                    loaiKTX.getSoGiuong()- countHopDongInPhong(phongKTX.getId())
                    ,loaiKTX.getImage()));
        }
        if(thongTinPhongList.isEmpty())
            return null;
        return thongTinPhongList;
    }
    public ThongTinPhong thongTinPhong(Integer idLoaiPhong, Integer idPhong){
        LoaiKTX loaiKTX=loaiKTXRepository.findLoaiKTXById(idLoaiPhong);
        Optional <PhongKTX> optional=phongKTXRepository.findById(idPhong);
        if(optional.isEmpty())
            return null;
        PhongKTX phongKTX=optional.get();

        return new ThongTinPhong(phongKTX.getId(),loaiKTX.getGiaPhong()
                ,loaiKTX.getSoGiuong()-countHopDongInPhong(phongKTX.getId())
                ,loaiKTX.getImage());
    }
    public boolean createHopDong(HopDongKTXDTO hopDongKTXDTO){
        System.out.println(hopDongKTXDTO.toString());
        Date date= new Date();
        Term term= termRepository.getByNgayMoDangKyBeforeAndNgayKetThucDangKyAfter(date,date);
        if(term == null){
            return false;
        }
        if(hopDongKTXRepository.existsByIdTermAndMSSV(term.getId(),hopDongKTXDTO.getMSSV())){
            logger.error("Contract already exists");
            return false;
        }

        Optional<PhongKTX> optionalPhongKTX=phongKTXRepository.findById(hopDongKTXDTO.getIdPhongKTX());
        if(optionalPhongKTX.isEmpty())
            return false;
        PhongKTX phongKTX=optionalPhongKTX.get();
        Integer idLoaiPhong=phongKTX.getIdLoaiKTX();
        Optional<LoaiKTX> optional=loaiKTXRepository.findById(idLoaiPhong);
        if(optional.isEmpty())
            return false;
        LoaiKTX loaiKTX=optional.get();
        Integer countHopDong=hopDongKTXRepository.countHopDongKTXByIdPhongKTXAndIdTerm(phongKTX.getId(), term.getId());
        if(loaiKTX.getSoGiuong()-countHopDong<=0){
            logger.error("No more beds available");
            return false;
        }

        HopDongKTX hopDongKTX=new HopDongKTX(hopDongKTXDTO.getIdPhongKTX(), hopDongKTXDTO.getMSSV(),date, term.getId());
        System.out.println(hopDongKTX.toString());
        try{
            hopDongKTXRepository.save(hopDongKTX);

        }catch (Exception e){
            logger.error("Save failed");
            return false;
        }
        return true;
    }

    public boolean checkStatus(String MSSV) {
        return hopDongKTXRepository.getHopDongKTXByMSSVAndTrangThaiTrue(MSSV);
    }



    public List<HopDongKTX> getHopDongTemp(String mssv) {

        return hopDongKTXRepository.findAllByMSSV(mssv);
    }


//    public ViewBillRoom getBillRoom(String mssv) {
//        Student student = studentRepository.findByUsername(mssv);
//        HopDongKTX hopDongKTX = hopDongKTXRepository.findFirstByMSSVOrderByNgayLamDonDesc(mssv);
//        HopDongKTXDTO hopDongKTXDTO = modelMapper.map(hopDongKTX,HopDongKTXDTO.class);
//        if (hopDongKTX.getNgayKetThuc().getTime() > new Date().getTime() && hopDongKTX.isTrangThai() == false) {
//            return null;
//        } else {
//            LoaiKTXDto loaiKTXDto = hopDongKTXDTO.getPhongKTX().getLoaiKTX();
//            return new ViewBillRoom(hopDongKTXDTO,loaiKTXDto,student.getHoTen(),student.getSDT(),loaiKTXDto.getGiaPhong()*5);
//        }
//    }

//    public Integer checkAddBillRoom(String mssv) {
//        HopDongKTX hopDongKTX = hopDongKTXRepository.findFirstByMSSVOrderByNgayLamDonDesc(mssv);
//        if (hopDongKTX==null) return 1;
//        else {
//            LocalDate oneWeek = LocalDate.now().plusDays(7);
//            //if status is paid, check datEnd to less than (current date plus 7 days)
//            if (hopDongKTX.isTrangThai()) {
//                if (hopDongKTX.getNgayHieuLuc().compareTo(Date.from(oneWeek.atStartOfDay(ZoneId.systemDefault()).toInstant())) <= 0) {
//                    return 1;
//                } else return 0;
//            } else {
//                // Room registration is not allowed if payment has not been made and the time has not expired
//                if (hopDongKTX.getNgayKetThuc().compareTo(new Date()) > 0) {
//                    return 0;
//                }
//                return 1;
//            }
//
//        }
//    }


//    public Integer numBedEmpty(Integer phongKTX){
//        return   hopDongKTXRepository.countHopDongKTXByIdPhongKTXAndNgayKetThucAfter(phongKTX,new Date());
////                +hopDongKTXRepository.countHopDongKTXByIdPhongKTXAndTrangThaiTrue(phongKTX);
//    }

//    public Integer checkNumBedEmpty(Integer phongKTX,Integer numBed){
//        Integer numBedEmpty = numBedEmpty(phongKTX);
//        if(numBedEmpty > numBed) return 0;
//        return 1;
//    }

//    public String addBillRoom(String mssv, Integer phongKTX,Integer numBed) {
//        Integer checkAddBillRoom = checkAddBillRoom(mssv);
//        Integer checkNumBed = checkNumBedEmpty(phongKTX,numBed);
//        if (checkAddBillRoom == 0 || checkNumBed == 0) return "null";
//        else{
//            LocalDate paymentDueDate = LocalDate.now().plusDays(7);
//
//            HopDongKTX hopDongKTX = new HopDongKTX(phongKTX,mssv,new Date(),Date.from(paymentDueDate.atStartOfDay(ZoneId.systemDefault()).toInstant()),Date.from(paymentDueDate.plusMonths(5).atStartOfDay(ZoneId.systemDefault()).toInstant()),false);
////            hopDongKTX.setMSSV(mssv);
////            hopDongKTX.setIdPhongKTX(phongKTX);
////            hopDongKTX.setNgayLamDon(new Date());
////            hopDongKTX.setNgayKetThuc(Date.from(paymentDueDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
////            hopDongKTX.setNgayHieuLuc(Date.from(paymentDueDate.plusMonths(5).atStartOfDay(ZoneId.systemDefault()).toInstant()));
////            hopDongKTX.setTrangThai(false);
//            try{
//                hopDongKTXRepository.save(hopDongKTX);
//                return "success";
//            }catch (Exception e) {
//                e.getStackTrace();
//                return "error";
//            }
//        }
//    }

//    public String addBillRoom(HopDongKTXDTO hopDongKTXDTO) {
//        System.out.println(hopDongKTXDTO.getIdPhongKTX());
//        System.out.println(hopDongKTXDTO.getMSSV());
//        Integer checkAddBillRoom = checkAddBillRoom(hopDongKTXDTO.getMSSV());
//        Integer checkNumBed = checkNumBedEmpty(hopDongKTXDTO.getIdPhongKTX(),phongKTXRepository.findPhongKTXById(hopDongKTXDTO.getId()).getLoaiKTX().getSoGiuong());
//        if (checkAddBillRoom == 0 || checkNumBed == 0) return "null";
//        else{
//            LocalDate paymentDueDate = LocalDate.now().plusDays(7);
//
//            HopDongKTX hopDongKTX = new HopDongKTX(hopDongKTXDTO.getIdPhongKTX(),hopDongKTXDTO.getMSSV(),new Date(),Date.from(paymentDueDate.atStartOfDay(ZoneId.systemDefault()).toInstant()),Date.from(paymentDueDate.plusMonths(5).atStartOfDay(ZoneId.systemDefault()).toInstant()),false);
////            hopDongKTX.setMSSV(mssv);
////            hopDongKTX.setIdPhongKTX(phongKTX);
////            hopDongKTX.setNgayLamDon(new Date());
////            hopDongKTX.setNgayKetThuc(Date.from(paymentDueDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
////            hopDongKTX.setNgayHieuLuc(Date.from(paymentDueDate.plusMonths(5).atStartOfDay(ZoneId.systemDefault()).toInstant()));
////            hopDongKTX.setTrangThai(false);
//            try{
//                hopDongKTXRepository.save(hopDongKTX);
//                return "success";
//            }catch (Exception e) {
//                e.getStackTrace();
//                return "error";
//            }
//        }
//    }


    public String deleteBillRoom(Integer idBillRoom) {
        HopDongKTX hopDongKTX = hopDongKTXRepository.getHopDongKTXById(idBillRoom);
        try {
            hopDongKTXRepository.delete(hopDongKTX);
            return "success";
        }catch (Exception e){
            e.getStackTrace();
            return "error";
        }
    }

    public ResponseEntity<?> getViewContractRoom(String mssv) {
        Optional<HopDongKTX> optional = Optional.ofNullable(hopDongKTXRepository.findHopDongKTXByMSSVAndTerm_NgayKetThucAfter(mssv, new Date()));
        if (optional.isEmpty()) return ResponseEntity.badRequest().body("Empty");
        HopDongKTX hopDongKTX =optional.get();
        LoaiKTX loaiKTX = loaiKTXRepository.findLoaiKTXById(hopDongKTX.getPhongKTX().getIdLoaiKTX());
        Student student = studentRepository.findByUsername(mssv);
        int totalMonthPayment = (hopDongKTX.getTerm().getNgayKetThuc().getMonth()-hopDongKTX.getTerm().getNgayMoDangKy().getMonth())+1;
        Double total = loaiKTX.getGiaPhong()*totalMonthPayment;
        ViewContractRoom viewContractRoom = new ViewContractRoom(modelMapper.map(hopDongKTX,HopDongKTXDTO.class),
                                                                modelMapper.map(loaiKTX,LoaiKTXDto.class),
                                                                modelMapper.map(student, StudentDto.class),
                                                                new Date(hopDongKTX.getNgayLamDon().getTime()+hopDongKTX.getTerm().getHanDongPhi()*86400000),
                                                                hopDongKTX.getTerm().getNgayKetThuc(),
                                                                total);
        return ResponseEntity.ok(viewContractRoom);
    }

    public ResponseEntity<?> getViewContractRoomList(Integer idPhongKTX,Integer idTerm) {
        List<HopDongKTX> hopDongKTXList = hopDongKTXRepository.findAllByIdPhongKTXAndTerm_Id(idPhongKTX,idTerm);
        List<HopDongKTXDTO> hopDongKTXDTOList = new ArrayList<>();
        System.out.println("day la entity"+hopDongKTXList.get(0).getTerm().toString());
        if (hopDongKTXList.isEmpty()) return ResponseEntity.badRequest().body("Empty");
        else{
            hopDongKTXDTOList = hopDongKTXList.stream().map(hopDongKTX -> modelMapper.map(hopDongKTX,HopDongKTXDTO.class)).collect(Collectors.toList());
            List<ViewContractRoomList> viewContractRoomList = new ArrayList<>();
            String fullName = "";
            Double total;
            System.out.println("day la DTO"+hopDongKTXDTOList.get(0).getTerm().toString());
            int totalMonthPayment;
            for (HopDongKTXDTO hopDongKTXDTO : hopDongKTXDTOList){
                fullName = studentRepository.findByUsername(hopDongKTXDTO.getMSSV()).getUsername();
                System.out.println(fullName+"====="+hopDongKTXDTO.getMSSV()+"===="+studentRepository.findByUsername(hopDongKTXDTO.getMSSV()).getUsername());
                totalMonthPayment = (hopDongKTXDTO.getTerm().getNgayKetThuc().getMonth()-hopDongKTXDTO.getTerm().getNgayMoDangKy().getMonth())+1;
                total = hopDongKTXDTO.getPhongKTX().getLoaiKTX().getGiaPhong()*totalMonthPayment;
                viewContractRoomList.add(new ViewContractRoomList(hopDongKTXDTO,fullName,total));
            }
            return ResponseEntity.ok(viewContractRoomList);
        }
    }
}
