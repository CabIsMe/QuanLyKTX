package root.quanlyktx.service;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import root.quanlyktx.dto.HopDongKTXDTO;
import root.quanlyktx.dto.LoaiKTXDto;
import root.quanlyktx.dto.StudentDto;
import root.quanlyktx.entity.*;
import root.quanlyktx.model.*;
import root.quanlyktx.repository.*;

import javax.annotation.PostConstruct;
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
    private final int oneDay=86400000;
    @Transactional
//    @Scheduled(fixedRate = 2000)
//    @Scheduled(cron = "0 0 0 1 * ?")
    public void runAuto() throws Exception {
        // Code to run after the application has started
        Date date= new Date();
        Term term= termRepository.getByNgayMoDangKyBeforeAndNgayKetThucDangKyAfter(date,date);
        Date dateExpired= new Date(date.getTime()-(term.getHanDongPhi()*60*60*1000*24));
        if(hopDongKTXRepository.findAllByNgayLamDonBeforeAndTrangThaiFalse(dateExpired).isEmpty()){
            return;
        }
//        hopDongKTXRepository.deleteAllByNgayLamDonBeforeAndTrangThaiFalse(dateExpired);
    }

    public List<HopDongKTXDTO> getAll() {
        List<HopDongKTX> hopDongKTXList = hopDongKTXRepository.findAll();
//        logger.info("OKLA");
//        OperationHistory operationHistory=new OperationHistory("src/main/resources/static/logfile.log");
//        List<String>strings= operationHistory.LoadDataToList();

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
            return -1;
        }
        return hopDongKTXRepository.countHopDongKTXByIdPhongKTXAndIdTerm(idPhong, term.getId());
    }
    public List<ThongTinPhong> thongTinPhongs(Integer idLoaiPhong){
        LoaiKTX loaiKTX=loaiKTXRepository.findLoaiKTXById(idLoaiPhong);
        List<PhongKTX> phongKTXDTOList=phongKTXRepository .findAllByIdLoaiKTX(idLoaiPhong);

        List<ThongTinPhong> thongTinPhongList= new ArrayList<>();
        if(countHopDongInPhong(phongKTXDTOList.get(0).getId())==-1){
            return null;
        }
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


//    public Double amountTotal(HopDongKTXDTO hopDongKTXDTO,LoaiKTX loaiKTX){
//        LocalDate dateStart = hopDongKTXDTO.getTerm().getNgayMoDangKy().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//        LocalDate dateEnd = hopDongKTXDTO.getTerm().getNgayKetThuc().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//        Period period = Period.between(dateStart,dateEnd);
//        int totalMonthPayment = period.getMonths()+1;
//        return loaiKTX.getGiaPhong()*totalMonthPayment;
//    }

    public boolean createContract(InputContract inputContract){
        Date date= new Date();
        Term term= termRepository.getByNgayMoDangKyBeforeAndNgayKetThucDangKyAfter(date,date);
        if(term == null){
            logger.error("This time not allowed");
            return false;
        }
        if(hopDongKTXRepository.existsByIdTermAndMSSV(term.getId(),inputContract.getMSSV())){
            logger.error("Contract already exists");
            return false;
        }
        logger.info("check Room And Bed");
        Optional<PhongKTX> optionalPhongKTX=phongKTXRepository.findById(inputContract.getIdPhongKTX());
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

        HopDongKTX hopDongKTX=new HopDongKTX(inputContract.getIdPhongKTX(), inputContract.getMSSV(),date, term.getId());

//        Double total = amountTotal(hopDongKTXDTO,loaiKTX);
//        HopDongKTX hopDongKTX=new HopDongKTX(hopDongKTXDTO.getIdPhongKTX(), hopDongKTXDTO.getMSSV(),date,total,term.getId());
        System.out.println(hopDongKTX.toString());
        try{
            hopDongKTXRepository.save(hopDongKTX);

        }catch (Exception e){
            logger.error("Save failed");
            return false;
        }
        return true;
    }

    public ResponseEntity<?> contractExtension(InputContract inputContract){
        Date current= new Date();
        Term currentTerm= termRepository.getByNgayKetThucDangKyBeforeAndNgayKetThucAfter(current,current);
        if(currentTerm == null){
            return ResponseEntity.badRequest().body("Can't register at this time (1)");
        }
        if(!hopDongKTXRepository.existsByIdTermAndMSSVAndTrangThaiTrue(currentTerm.getId(),inputContract.getMSSV())){
            return ResponseEntity.badRequest().body("Students are not allowed to extend the contract");
        }
        // Check Exist contract at current Term of Student
        Optional<PhongKTX> optionalPhongKTX=phongKTXRepository.findById(inputContract.getIdPhongKTX());
        if(optionalPhongKTX.isEmpty())
            return ResponseEntity.badRequest().body("This room didn't exist");
        // Check exist room

        PhongKTX phongKTX=optionalPhongKTX.get();
        Integer idLoaiPhong=phongKTX.getIdLoaiKTX();
        Optional<LoaiKTX> optional=loaiKTXRepository.findById(idLoaiPhong);
        if(optional.isEmpty())
            return ResponseEntity.badRequest().body("This room type didn't exist.");
        // Check exist roomType
        LoaiKTX loaiKTX=optional.get();
        Term newTerm= termRepository.getTheNextTerm();
        if(newTerm==null){
            return ResponseEntity.badRequest().body("Can't register at this time (2)");
        }
        Integer countHopDong=hopDongKTXRepository.countHopDongKTXByIdPhongKTXAndIdTerm(phongKTX.getId(), newTerm.getId());
        if(loaiKTX.getSoGiuong()-countHopDong<=0){
            return ResponseEntity.badRequest().body("No more beds available");
        }
        if(currentTerm.getNgayKetThuc().getTime()-current.getTime()>= 7*oneDay){
            return ResponseEntity.badRequest().body("Can't register at this time (3)");
        }
        // Check that the registration date is valid.
        HopDongKTX hopDongKTX=new HopDongKTX(inputContract.getIdPhongKTX(), inputContract.getMSSV(),current, newTerm.getId());
        try{
            hopDongKTXRepository.save(hopDongKTX);

        }catch (Exception e){
            return ResponseEntity.badRequest().body("Create contract failed");
        }
        return ResponseEntity.ok(true);
    }

    public boolean cancelContract(HopDongKTXDTO hopDongKTXDTO){

        return true;
    }

    public boolean checkStatus(String MSSV) {
        return hopDongKTXRepository.getHopDongKTXByMSSVAndTrangThaiTrue(MSSV);
    }



//    public ResponseEntity<?> getHopDongTemp(HopDongKTXDTO hopDongKTXDTO) {
//        List<HopDongKTX> hopDongKTXList = hopDongKTXRepository.findAllByMSSV(hopDongKTXDTO.getMSSV());
//        System.out.println(hopDongKTXList.stream().toString());
//        return ResponseEntity.ok(hopDongKTXList);
//    }

    public ResponseEntity<?> getViewContractRoom(String mssv) {
        Optional<HopDongKTX> optional = Optional.ofNullable(hopDongKTXRepository.findHopDongKTXByMSSVAndTerm_NgayMoDangKyBeforeAndTerm_NgayKetThucAfter(mssv, new Date(),new Date()));
        if (optional.isEmpty()) return ResponseEntity.badRequest().body("Empty");
        HopDongKTX hopDongKTX =optional.get();
        LoaiKTX loaiKTX = loaiKTXRepository.findLoaiKTXById(hopDongKTX.getPhongKTX().getIdLoaiKTX());
        Student student = studentRepository.findByUsername(mssv);
        LocalDate dateStart = hopDongKTX.getTerm().getNgayMoDangKy().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate dateEnd = hopDongKTX.getTerm().getNgayKetThuc().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        Period period = Period.between(dateStart,dateEnd);
        int totalMonthPayment = period.getMonths()+1;
        loaiKTX.setGiaPhong(hopDongKTX.getTongTien()/totalMonthPayment);
        ViewContractRoom viewContractRoom = new ViewContractRoom(modelMapper.map(hopDongKTX,HopDongKTXDTO.class),
                                                                modelMapper.map(loaiKTX,LoaiKTXDto.class),
                                                                modelMapper.map(student, StudentDto.class),
                                                                new Date(hopDongKTX.getNgayLamDon().getTime()+hopDongKTX.getTerm().getHanDongPhi()*86400000),
                                                                hopDongKTX.getTerm().getNgayKetThuc(),
                                                                hopDongKTX.getTongTien());
        return ResponseEntity.ok(viewContractRoom);
    }

    public Date calculateDatePayment(Date date,Short numDatePayments){
        LocalDate datePay = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        datePay = datePay.plusDays(numDatePayments);
        return Date.from(datePay.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public ResponseEntity<?> getViewContractRoomList(Integer numPage,Integer idPhongKTX,Integer idTerm,boolean status) {
        Pageable pageable = PageRequest.of(0*numPage,9*numPage);
        List<HopDongKTX> hopDongKTXList = hopDongKTXRepository.findByIdPhongKTXAndTerm_IdAndTrangThaiOrderByNgayLamDonDesc(idPhongKTX,idTerm,status,pageable);
        if (hopDongKTXList.isEmpty()) return ResponseEntity.badRequest().body("Empty");
        else{
            List<HopDongKTXDTO> hopDongKTXDTOList = hopDongKTXList.stream().map(hopDongKTX -> modelMapper.map(hopDongKTX,HopDongKTXDTO.class)).collect(Collectors.toList());
            List<ViewContractRoomList> viewContractRoomList = new ArrayList<>();
            String fullName = "";
            Date datePayment;
//            LocalDate dateStart;
//            LocalDate dateEnd;
//            Period period;
            int totalMonthPayment;
            for (HopDongKTXDTO hopDongKTXDTO : hopDongKTXDTOList){
                fullName = studentRepository.findByUsername(hopDongKTXDTO.getMSSV()).getUsername();
//                dateStart = hopDongKTXDTO.getTerm().getNgayMoDangKy().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//                dateEnd = hopDongKTXDTO.getTerm().getNgayKetThuc().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//                period = Period.between(dateStart,dateEnd);
//                totalMonthPayment = period.getMonths()+1;
//                total = hopDongKTXDTO.getPhongKTX().getLoaiKTX().getGiaPhong()*totalMonthPayment;
                datePayment = calculateDatePayment(hopDongKTXDTO.getNgayLamDon(),hopDongKTXDTO.getTerm().getHanDongPhi());
                viewContractRoomList.add(new ViewContractRoomList(hopDongKTXDTO,fullName,datePayment,hopDongKTXDTO.getTongTien()));
            }
            return ResponseEntity.ok(viewContractRoomList);
        }
    }

    public ResponseEntity<?> updateStatusContract(Integer idHopDong,Integer idTerm) {
        Optional<HopDongKTX> hopDongKTXOptional = Optional.ofNullable(hopDongKTXRepository.getHopDongKTXById(idHopDong));
        if(hopDongKTXOptional.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Exception, Not Found");
        else {
            HopDongKTX hopDongKTX = hopDongKTXOptional.get();
            Date datePayment = calculateDatePayment(hopDongKTX.getNgayLamDon(),hopDongKTX.getTerm().getHanDongPhi());
            if(idTerm != termRepository.getCurrentTerm()) return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No edit contract status allow,contracts of the past period cannot be modified");
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
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("save status contract is error");
                }
            }
        }
    }

    public ResponseEntity<?> getAmountStudentInTerm() {
        List<StudentInTerm> studentInTerms = termRepository.countStudentInTerm();
        studentInTerms.forEach(o-> System.out.println(o.getIdTerm()+o.getDateStart().toString()+o.getDateEnd().toString()+o.getAmountStudent()));
        if(studentInTerms.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("empty");
        else return ResponseEntity.ok(studentInTerms);
    }
}
