package root.quanlyktx.service;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import root.quanlyktx.dto.GiaDienTheoThangDTO;
import root.quanlyktx.dto.PhieuDienKTXDTO;
import root.quanlyktx.entity.HopDongKTX;
import root.quanlyktx.entity.PhieuDienKTX;
import root.quanlyktx.entity.Term;
import root.quanlyktx.repository.HopDongKTXRepository;
import root.quanlyktx.repository.PhieuDienKTXRepository;
import root.quanlyktx.repository.TermRepository;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
@EnableScheduling
public class PhieuDienKTXService {
    @Autowired
    private HopDongKTXRepository hopDongKTXRepository;
    @Autowired
    private PhieuDienKTXRepository phieuDienKTXRepository;
    @Autowired
    private ModelMapper modelMapper;

    private static final Logger logger = LoggerFactory.getLogger(PhieuDienKTXService.class);

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

    public ResponseEntity<?> getSudentElectricBills() {
        String mssv = getUsernameFromSecurityContextHolder();
        Date currentDate = new Date();
        Optional<HopDongKTX> hopDongKTX = Optional.ofNullable(hopDongKTXRepository.findHopDongKTXByMSSVAndTerm_NgayKetThucDangKyBeforeAndTerm_NgayKetThucAfter(mssv, currentDate, currentDate));
        if (hopDongKTX.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Empty");
        else {
            LocalDate dateStart = hopDongKTX.get().getNgayLamDon().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            List<PhieuDienKTX> phieuDienKTXList = phieuDienKTXRepository.findAllByMaSoKTXAndGiaDienTheoThang_ThangGreaterThanEqualAndGiaDienTheoThang_NamGreaterThanEqual(hopDongKTX.get().getIdPhongKTX(), dateStart.getMonthValue(), dateStart.getYear());
            List<PhieuDienKTXDTO> phieuDienKTXDTOList= phieuDienKTXList.stream()
                    .map(c -> new PhieuDienKTXDTO(c.getId(), c.getMaSoKTX(), c.getSoDienTieuThu(), c.isTrangThai(),
                            modelMapper.map(c.getGiaDienTheoThang(), GiaDienTheoThangDTO.class),
                            c.getSoDienTieuThu()*c.getGiaDienTheoThang().getGiaDien()))
                    .collect(Collectors.toList());
            return ResponseEntity.ok(phieuDienKTXDTOList);
        }
    }



    public ResponseEntity<?> electricityListManagement(Short numPage, Integer month, Integer year, Boolean status) {
        Pageable pageable = PageRequest.of(0,9*numPage);
        if(month==null){
            month= getPreviousMonthAndYear().get(0);
        }
        if(year==null){
            year= getPreviousMonthAndYear().get(1);
        }
        List<PhieuDienKTX> phieuDienKTXList = phieuDienKTXRepository.findAllByTrangThaiAndGiaDienTheoThang_ThangAndGiaDienTheoThang_Nam(status,month,year,pageable);

        List<PhieuDienKTXDTO> phieuDienKTXDTOList= phieuDienKTXList.stream()
                .map(c -> new PhieuDienKTXDTO(c.getId(), c.getMaSoKTX(), c.getSoDienTieuThu(), c.isTrangThai(),
                        modelMapper.map(c.getGiaDienTheoThang(), GiaDienTheoThangDTO.class),
                        c.getSoDienTieuThu()*c.getGiaDienTheoThang().getGiaDien()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(phieuDienKTXDTOList);
    }

    public ResponseEntity<?> updateStatus(Integer id) {
        Optional<PhieuDienKTX> optional= phieuDienKTXRepository.findById(id);
        if(optional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found: "+id);
        }
        PhieuDienKTX phieuDienKTX=optional.get();
        Integer yearOfInvoice=phieuDienKTX.getGiaDienTheoThang().getNam();
        Integer monthOfInvoice=phieuDienKTX.getGiaDienTheoThang().getThang();
        YearMonth current= YearMonth.now();
        if(current.isAfter(YearMonth.of(yearOfInvoice, monthOfInvoice))){
            phieuDienKTX.setTrangThai(!phieuDienKTX.isTrangThai());
            try{
                phieuDienKTXRepository.save(phieuDienKTX);
                return ResponseEntity.noContent().build();
            }catch (Exception e){
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Server Error");
            }
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Unable to done this time");
    }



}

