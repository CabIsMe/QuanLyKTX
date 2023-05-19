package root.quanlyktx.service;

import com.google.api.gax.rpc.NotFoundException;
import org.hibernate.ResourceClosedException;
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
import root.quanlyktx.dto.GiaNuocTheoThangDTO;
import root.quanlyktx.dto.PhieuNuocKTXDTO;
import root.quanlyktx.entity.*;
import root.quanlyktx.repository.HopDongKTXRepository;
import root.quanlyktx.repository.PhieuNuocKTXRepository;
import root.quanlyktx.repository.TermRepository;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PhieuNuocKTXService {

    @Autowired
    private HopDongKTXRepository hopDongKTXRepository;
    @Autowired
    private PhieuNuocKTXRepository phieuNuocKTXRepository;
    @Autowired
    private ModelMapper modelMapper;

    private static final Logger logger = LoggerFactory.getLogger(PhieuNuocKTXService.class);

    List<Integer> getPreviousMonthAndYear(){
        ZoneId zoneId = ZoneId.of("Asia/Ho_Chi_Minh");
        LocalDate currentDate = LocalDate.now(zoneId);
        int currentMonth = currentDate.getMonthValue();
        int currentYear = currentDate.getYear();
        YearMonth previousMonth = YearMonth.of(currentYear, currentMonth).minusMonths(1);
        Integer previousMonthValue = previousMonth.getMonthValue();
        Integer previousYearValue = previousMonth.getYear();
        List<Integer> list= new ArrayList<>();
        list.add(previousMonthValue);
        list.add(previousYearValue);
        return list;
    }

    public static String getUsernameFromSecurityContextHolder(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication.getName().equals("anonymousUser")){
            logger.error("Is not authenticated");
            return null;
        }

        return authentication.getName();
    }

    public List<PhieuNuocKTX> getAll(){return phieuNuocKTXRepository.findAll();}
    public PhieuNuocKTX findById(Integer id){ return phieuNuocKTXRepository.findById(id).get();}

    public ResponseEntity<?> updatePhieuNuocKTX(Integer id){
        Optional<PhieuNuocKTX> optional= phieuNuocKTXRepository.findById(id);
        if(optional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found: "+id);
        }
        PhieuNuocKTX phieuNuocKTX= optional.get();
        Integer yearOfInvoice=phieuNuocKTX.getGiaNuocTheoThang().getNam();
        Integer monthOfInvoice=phieuNuocKTX.getGiaNuocTheoThang().getThang();
        YearMonth current= YearMonth.now();
        if(current.isAfter(YearMonth.of(yearOfInvoice, monthOfInvoice))){
            phieuNuocKTX.setTrangThai(!phieuNuocKTX.isTrangThai());
            try{
                phieuNuocKTXRepository.save(phieuNuocKTX);
                return ResponseEntity.noContent().build();
            }catch (Exception e){
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Server Error");
            }
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Unable to done this time");
    }

    public ResponseEntity<?> getStudentWaterBills(){
        String mssv = getUsernameFromSecurityContextHolder();
        Date currentDate = new Date();
        Optional<HopDongKTX> hopDongKTX = Optional.ofNullable(hopDongKTXRepository.findHopDongKTXByMSSVAndTerm_NgayKetThucDangKyBeforeAndTerm_NgayKetThucAfter(mssv,currentDate,currentDate));
        if(hopDongKTX.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Empty");
        else{
            LocalDate dateStart = hopDongKTX.get().getNgayLamDon().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            List<PhieuNuocKTX> phieuNuocKTXList = phieuNuocKTXRepository.findAllByMaSoKTXAndGiaNuocTheoThang_ThangGreaterThanEqualAndGiaNuocTheoThang_NamGreaterThanEqual(hopDongKTX.get().getIdPhongKTX(),dateStart.getMonthValue(),dateStart.getYear());
            List<PhieuNuocKTXDTO> phieuNuocKTXDTOList = new ArrayList<>();
            Double total;
            for(PhieuNuocKTX phieuNuocKTX: phieuNuocKTXList){
                total = phieuNuocKTX.getLuongNuocTieuThu()*phieuNuocKTX.getGiaNuocTheoThang().getGiaNuoc();
                phieuNuocKTXDTOList.add(new PhieuNuocKTXDTO(phieuNuocKTX.getId(),
                                                            phieuNuocKTX.getMaSoKTX(),
                                                            phieuNuocKTX.getLuongNuocTieuThu(),
                                                            phieuNuocKTX.isTrangThai(),
                                                            modelMapper.map(phieuNuocKTX.getGiaNuocTheoThang(),GiaNuocTheoThangDTO.class),
                                                            total));
            }
            return ResponseEntity.ok(phieuNuocKTXDTOList);
        }
    }

    public ResponseEntity<?> waterListManagement(Short numPage,Integer month, Integer year,Boolean status){
        Pageable pageable = PageRequest.of(0,9*numPage);
        if(month==null){
            month= getPreviousMonthAndYear().get(0);
        }
        if(year==null){
            year= getPreviousMonthAndYear().get(1);
        }
        List<PhieuNuocKTX> phieuNuocKTXList = phieuNuocKTXRepository.findAllByTrangThaiAndGiaNuocTheoThang_ThangAndGiaNuocTheoThang_Nam(status,month,year,pageable);
        List<PhieuNuocKTXDTO> phieuNuocKTXDTOList= phieuNuocKTXList.stream()
               .map(c -> new PhieuNuocKTXDTO(c.getId(), c.getMaSoKTX(), c.getLuongNuocTieuThu(), c.isTrangThai(),
                       modelMapper.map(c.getGiaNuocTheoThang(),GiaNuocTheoThangDTO.class),
                       c.getLuongNuocTieuThu()*c.getGiaNuocTheoThang().getGiaNuoc()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(phieuNuocKTXDTOList);
    }


}
