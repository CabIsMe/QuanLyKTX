package root.quanlyktx.service;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import root.quanlyktx.dto.HopDongKTXDTO;
import root.quanlyktx.dto.LoaiKTXDto;
import root.quanlyktx.dto.StudentDto;
import root.quanlyktx.dto.TermDTO;
import root.quanlyktx.entity.*;
import root.quanlyktx.model.*;
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
        return hopDongKTXList.stream().map(hopDongKTX -> modelMapper.map(hopDongKTX, HopDongKTXDTO.class)).collect(Collectors.toList());
    }

    public List<HopDongKTXDTO> getById(String id) {
        List<HopDongKTX> hopDongKTXList = new ArrayList<>();
        try{
            int intValue = Integer.parseInt(id);
            hopDongKTXList.add(hopDongKTXRepository.getHopDongKTXById(intValue));
            if (hopDongKTXList.isEmpty()) {
                throw new Exception("List empty");
            }
        }catch (Exception e){
            hopDongKTXList = hopDongKTXRepository.findAllByMSSVLike("%"+id+"%");
        }
        if(hopDongKTXList.isEmpty())
            return new ArrayList<>();
        return hopDongKTXList.stream()
                .map(hopDongKTX -> modelMapper.map(hopDongKTX, HopDongKTXDTO.class))
                .collect(Collectors.toList());
    }

    public List<HopDongKTXDTO> getByPhongKTX(Integer idPhongKTX) {
        List<HopDongKTX> hopDongKTXList = hopDongKTXRepository.findAllByIdPhongKTX(idPhongKTX);
        return hopDongKTXList.stream()
                .map(hopDongKTX -> modelMapper.map(hopDongKTX, HopDongKTXDTO.class))
                .collect(Collectors.toList());
    }


    public Double amountTotal(Term term, LoaiKTX loaiKTX){
        LocalDate dateStart = term.getNgayKetThucDangKy().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate dateEnd = term.getNgayKetThuc().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        Period period = Period.between(dateStart,dateEnd);
        int totalMonthPayment = period.getMonths()+1;
        return loaiKTX.getGiaPhong()*totalMonthPayment;
    }


    public boolean createContract(Integer idPhong){
        String mssv= getUsernameFromSecurityContextHolder();
        if(mssv==null)
            return false;
        Date date= new Date();
        Term term= termRepository.getByNgayMoDangKyBeforeAndNgayKetThucDangKyAfter(date,date);
        if(term == null){
            logger.error("This time not allowed");
            return false;
        }
        if(hopDongKTXRepository.existsByIdTermAndMSSV(term.getId(),mssv)){
            logger.error("Contract already exists");
            return false;
        }
//        logger.info("check Room And Bed");
        Optional<PhongKTX> optionalPhongKTX=phongKTXRepository.findByIdAndTrangThaiTrue(idPhong);
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
        Double total= amountTotal(term, loaiKTX);
        HopDongKTX hopDongKTX=new HopDongKTX(idPhong, mssv,date, term.getId(), total);

        try{
            hopDongKTXRepository.save(hopDongKTX);

        }catch (Exception e){
            logger.error("Save failed");
            return false;
        }

        return true;
    }

    public static String getUsernameFromSecurityContextHolder(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication.getName().equals("anonymousUser")){
            logger.error("Is not authenticated");
            return null;
        }

        return authentication.getName();
    }

    public ViewContractRoom getViewBeforeCreateContract(Integer idPhong){
        String mssv= getUsernameFromSecurityContextHolder();
        Student student= studentRepository.findByUsername(mssv);
        Date date= new Date();
        Term term= termRepository.getByNgayMoDangKyBeforeAndNgayKetThucDangKyAfter(date,date);

        PhongKTX phongKTX=phongKTXRepository.findByIdAndTrangThaiTrue(idPhong).get();
        LoaiKTX loaiKTX=loaiKTXRepository.findById(phongKTX.getIdLoaiKTX()).get();
        Double total= amountTotal(term, loaiKTX);
        HopDongKTX hopDongKTX=new HopDongKTX(idPhong, mssv,date, term.getId(), total);
        logger.info("Get View Before Create Contract - "+hopDongKTX.toString());
        ViewContractRoom viewContractRoom = new ViewContractRoom(modelMapper.map(hopDongKTX,HopDongKTXDTO.class),
                loaiKTX.getTenLoai(),
                loaiKTX.getGiaPhong(),
                student.getHoTen(),
                student.getMail(),
                student.getSDT(),
                new Date(hopDongKTX.getNgayLamDon().getTime()+term.getHanDongPhi()*86400000),
                term.getNgayKetThucDangKy(), term.getNgayKetThuc());
        return viewContractRoom;

    }


    public ResponseEntity<?> contractExtension(){
        String mssv=getUsernameFromSecurityContextHolder();
        if(mssv==null){
            return ResponseEntity.badRequest().body("Is not authenticated");
        }
        Date current= new Date();
        Term currentTerm= termRepository.getByNgayKetThucDangKyBeforeAndNgayKetThucAfter(current,current);
        if(currentTerm == null){
            return ResponseEntity.badRequest().body("Can't register at this time (1)");
        }

        HopDongKTX hopDongKTXOld= hopDongKTXRepository.getHopDongKTXByIdTermAndMSSVAndTrangThaiTrue(currentTerm.getId(), mssv);
        if(hopDongKTXOld==null){
            return ResponseEntity.badRequest().body("Students are not allowed to extend the contract");
        }

        // Check Exist contract at current Term of Student
        Optional<PhongKTX> optionalPhongKTX=phongKTXRepository.findByIdAndTrangThaiTrue(hopDongKTXOld.getIdPhongKTX());
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
        Double total= amountTotal(newTerm, loaiKTX);
        HopDongKTX hopDongKTX=new HopDongKTX(hopDongKTXOld.getIdPhongKTX(), mssv,current, newTerm.getId(), total);
        try{
            hopDongKTXRepository.save(hopDongKTX);

        }catch (Exception e){
            return ResponseEntity.badRequest().body("Create contract failed");
        }
        return ResponseEntity.ok(true);
    }

    public ResponseEntity<?> cancelContract(){
        String mssv= getUsernameFromSecurityContextHolder();
        if(mssv==null){
            return ResponseEntity.badRequest().body("Is not Authenticated");
        }
        Date date= new Date();
        Term term= termRepository.getByNgayMoDangKyBeforeAndNgayKetThucDangKyAfter(date,date);

//        if(term == null && nextTerm ==null){
//            return ResponseEntity.badRequest().body("Can't cancel a contract at this time");
//        }
        if(term != null){
            HopDongKTX hopDongKTX=hopDongKTXRepository.findByMSSVAndTrangThaiFalseAndIdTerm(mssv,term.getId());
            if(hopDongKTX==null){
                return ResponseEntity.badRequest().body("A contract not found");
            }

            try{
                hopDongKTXRepository.delete(hopDongKTX);
            }catch (Exception e){
                e.printStackTrace();
                return ResponseEntity.badRequest().body("Cancel a contract failed");
            }
            return ResponseEntity.ok().body("Successfully canceled contract");
        }
        else{
            Term nextTerm= termRepository.getTheNextTerm();
            if(nextTerm != null){
                HopDongKTX newHopDongKTX=hopDongKTXRepository.findByMSSVAndTrangThaiFalseAndIdTerm(mssv,nextTerm.getId());
                if(newHopDongKTX==null){
                    return ResponseEntity.badRequest().body("Next term contract not found");
                }
                try{
                    hopDongKTXRepository.delete(newHopDongKTX);
                }catch (Exception e){
                    e.printStackTrace();
                    return ResponseEntity.badRequest().body("Cancel next term contract failed");
                }
                return ResponseEntity.ok().body("Successfully canceled next term contract");
            }
            return ResponseEntity.badRequest().body("Can't cancel next term contract at this time");
        }

    }

    public boolean checkStatus(String MSSV) {
        return hopDongKTXRepository.getHopDongKTXByMSSVAndTrangThaiTrue(MSSV);
    }

    public boolean checkConditionToRegistration(){
        String mssv= getUsernameFromSecurityContextHolder();
        Date date= new Date();
        Term term= termRepository.getByNgayMoDangKyBeforeAndNgayKetThucDangKyAfter(date,date);
        if(term==null)
            return false;

        return !hopDongKTXRepository.existsByIdTermAndMSSV(term.getId(),mssv);
    }


    public ResponseEntity<?> getViewContractRoom() {
        String mssv= getUsernameFromSecurityContextHolder();
        if(mssv==null){
            return ResponseEntity.badRequest().body("Is not authenticated");
        }
        Date curDate = new Date();
        Optional<HopDongKTX> optional = Optional.ofNullable(hopDongKTXRepository.findFirstByMSSVAndTerm_NgayMoDangKyBeforeAndTerm_NgayKetThucAfterOrderByNgayLamDonDesc(mssv,curDate,curDate));
        if (optional.isEmpty()) return ResponseEntity.badRequest().body("Empty");
        HopDongKTX hopDongKTX =optional.get();
//        LoaiKTX loaiKTX = loaiKTXRepository.findLoaiKTXById(hopDongKTX.getPhongKTX().getIdLoaiKTX());
        LoaiKTX loaiKTX = hopDongKTX.getPhongKTX().getLoaiKTX();
        Student student = hopDongKTX.getStudent();
        ViewContractRoom viewContractRoom = new ViewContractRoom(modelMapper.map(hopDongKTX,HopDongKTXDTO.class),
                loaiKTX.getTenLoai(),
                loaiKTX.getGiaPhong(),
                student.getHoTen(),
                student.getMail(),
                student.getSDT(),
                new Date(hopDongKTX.getNgayLamDon().getTime()+hopDongKTX.getTerm().getHanDongPhi()*86400000),
                hopDongKTX.getTerm().getNgayKetThucDangKy(), hopDongKTX.getTerm().getNgayKetThuc());
        return ResponseEntity.ok(viewContractRoom);
    }



    public Date calculateDatePayment(Date date,Short numDatePayments){
        LocalDate datePay = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        datePay = datePay.plusDays(numDatePayments);
        return Date.from(datePay.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public ResponseEntity<?> getViewContractRoomList(Integer numPage,Integer idPhongKTX,Integer idTerm,boolean status) {

        Pageable pageable = PageRequest.of(0,numPage);
        List<HopDongKTX> hopDongKTXList;
        if(idPhongKTX==0 && idTerm==0){
            hopDongKTXList = hopDongKTXRepository.findAllByTrangThai(status,pageable);
        }
        else{
            if(idPhongKTX==0) {
                hopDongKTXList = hopDongKTXRepository.findAllByTerm_IdAndTrangThai(idTerm,status,pageable);
            }
            else if (idTerm==0){
                hopDongKTXList = hopDongKTXRepository.findAllByIdPhongKTXAndTrangThai(idPhongKTX,status,pageable);
            }
            else{
                hopDongKTXList = hopDongKTXRepository.findByIdPhongKTXAndTerm_IdAndTrangThaiOrderByNgayLamDonDesc(idPhongKTX,idTerm,status,pageable);
            }
        }

            List<HopDongKTXDTO> hopDongKTXDTOList = hopDongKTXList.stream()
                    .map(hopDongKTX -> modelMapper
                    .map(hopDongKTX,HopDongKTXDTO.class))
                    .collect(Collectors.toList());

            return ResponseEntity.ok(hopDongKTXDTOList);
//        }
    }

    public ResponseEntity<?> updateStatusContract(Integer idHopDong) {
        Optional<HopDongKTX> hopDongKTXOptional = Optional.ofNullable(hopDongKTXRepository.getHopDongKTXById(idHopDong));
        if(hopDongKTXOptional.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Exception, Not Found");
        else {
            HopDongKTX hopDongKTX = hopDongKTXOptional.get();
            Date datePayment = calculateDatePayment(hopDongKTX.getNgayLamDon(),hopDongKTX.getTerm().getHanDongPhi());
            if(new Date().compareTo(datePayment)>=0)
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No edit contract status at this time");
            else
            {

                try {
                    hopDongKTX.setTrangThai(!hopDongKTX.isTrangThai());

                    hopDongKTXRepository.save(hopDongKTX);
                    return ResponseEntity.ok().body("Success");
                }catch (Exception ex){
                    ex.printStackTrace();
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("save status contract is error");
                }
            }
        }
    }


    public List<HopDongKTXDTO> sortListContractById(List<HopDongKTXDTO> hopdongKTXDTOS){
        if(hopdongKTXDTOS.isEmpty())
            return new ArrayList<>();
        List<Integer> ids=hopdongKTXDTOS.stream()
                                        .map(HopDongKTXDTO :: getId)
                                        .collect(Collectors.toList());

        List<Integer> idss = QuickSort.sort(ids);
        return hopDongKTXRepository.findAllById(idss).stream()
                                                        .map(hopDongKTX -> modelMapper
                                                                .map(hopDongKTX,HopDongKTXDTO.class))
                                                                    .collect(Collectors.toList());

    }
}
