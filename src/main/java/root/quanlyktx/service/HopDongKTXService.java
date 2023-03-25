package root.quanlyktx.service;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
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

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
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

    public String deleteContractRoom(Integer idBillRoom) {
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
        LocalDate dateStart = hopDongKTX.getTerm().getNgayMoDangKy().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate dateEnd = hopDongKTX.getTerm().getNgayKetThuc().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        Period period = Period.between(dateStart,dateEnd);
        int totalMonthPayment = period.getMonths()+1;
        Double total = loaiKTX.getGiaPhong()*totalMonthPayment;
        ViewContractRoom viewContractRoom = new ViewContractRoom(modelMapper.map(hopDongKTX,HopDongKTXDTO.class),
                                                                modelMapper.map(loaiKTX,LoaiKTXDto.class),
                                                                modelMapper.map(student, StudentDto.class),
                                                                new Date(hopDongKTX.getNgayLamDon().getTime()+hopDongKTX.getTerm().getHanDongPhi()*86400000),
                                                                hopDongKTX.getTerm().getNgayKetThuc(),
                                                                total);
        return ResponseEntity.ok(viewContractRoom);
    }

    public ResponseEntity<?> getViewContractRoomList(Integer numPage,Integer idPhongKTX,Integer idTerm) {
        Pageable pageable = PageRequest.of(0*numPage,9*numPage);
        List<HopDongKTX> hopDongKTXList = hopDongKTXRepository.findByIdPhongKTXAndTerm_IdOrderByNgayLamDonDesc(idPhongKTX,idTerm,pageable);
        if (hopDongKTXList.isEmpty()) return ResponseEntity.badRequest().body("Empty");
        else{
            List<HopDongKTXDTO> hopDongKTXDTOList = hopDongKTXList.stream().map(hopDongKTX -> modelMapper.map(hopDongKTX,HopDongKTXDTO.class)).collect(Collectors.toList());
            List<ViewContractRoomList> viewContractRoomList = new ArrayList<>();
            String fullName = "";
            Double total;
            LocalDate dateStart;
            LocalDate dateEnd;
            Period period;
            int totalMonthPayment;
            for (HopDongKTXDTO hopDongKTXDTO : hopDongKTXDTOList){
                fullName = studentRepository.findByUsername(hopDongKTXDTO.getMSSV()).getUsername();
                dateStart = hopDongKTXDTO.getTerm().getNgayMoDangKy().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                dateEnd = hopDongKTXDTO.getTerm().getNgayKetThuc().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                period = Period.between(dateStart,dateEnd);
                totalMonthPayment = period.getMonths()+1;
                total = hopDongKTXDTO.getPhongKTX().getLoaiKTX().getGiaPhong()*totalMonthPayment;
                viewContractRoomList.add(new ViewContractRoomList(hopDongKTXDTO,fullName,total));
            }
            return ResponseEntity.ok(viewContractRoomList);
        }
    }

    public ResponseEntity<?> updateStatusContract(Integer idHopDong) {
        if(!hopDongKTXRepository.existsById(idHopDong))
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Exception, Not Found");
        else {
            HopDongKTX hopDongKTX = hopDongKTXRepository.getHopDongKTXById(idHopDong);
            Date datePayment = new Date(hopDongKTX.getNgayLamDon().getTime()+hopDongKTX.getTerm().getHanDongPhi()*86400000);
            if(new Date().compareTo(datePayment)>=0) return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No edit contract status allow,because current date >= date payment");
            else
            {
                if(hopDongKTX.isTrangThai()) hopDongKTX.setTrangThai(false);
                else hopDongKTX.setTrangThai(true);
                try {
                    hopDongKTXRepository.save(hopDongKTX);
                    return ResponseEntity.ok().body("Success");
                }catch (Exception ex){
                    ex.printStackTrace();
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).body("save status contract is error");
                }
            }
        }
    }
}
