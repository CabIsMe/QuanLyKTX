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
import root.quanlyktx.dto.GiaDienTheoThangDTO;
import root.quanlyktx.dto.PhieuDienKTXDTO;
import root.quanlyktx.entity.HopDongKTX;
import root.quanlyktx.entity.PhieuDienKTX;
import root.quanlyktx.entity.Term;
import root.quanlyktx.firebase.FBPhieuDienNuocService;
import root.quanlyktx.repository.HopDongKTXRepository;
import root.quanlyktx.repository.PhieuDienKTXRepository;
import root.quanlyktx.repository.TermRepository;

import java.time.YearMonth;
import java.util.*;

@Service
@EnableScheduling
public class PhieuDienKTXService {
    @Autowired
    private TermRepository termRepository;
    @Autowired
    private HopDongKTXRepository hopDongKTXRepository;
    @Autowired
    private PhieuDienKTXRepository phieuDienKTXRepository;
    @Autowired
    private ModelMapper modelMapper;

    private static final Logger logger = LoggerFactory.getLogger(PhieuDienKTXService.class);

    public static String getUsernameFromSecurityContextHolder(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication.getName().equals("anonymousUser")){
            logger.error("Is not authenticated");
            return null;
        }

        return authentication.getName();
    }

    public ResponseEntity<?> getElectricBills() {
        String mssv = getUsernameFromSecurityContextHolder();
        Date currentDate = new Date();
        Optional<HopDongKTX> hopDongKTX = Optional.ofNullable(hopDongKTXRepository.findHopDongKTXByMSSVAndTerm_NgayKetThucDangKyBeforeAndTerm_NgayKetThucAfter(mssv, currentDate, currentDate));
        if (hopDongKTX.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Empty");
        else {
            YearMonth dateStart = YearMonth.from(hopDongKTX.get().getNgayLamDon().toInstant());
            List<PhieuDienKTX> phieuDienKTXList = phieuDienKTXRepository.findAllByMaSoKTXAndGiaDienTheoThang_ThangGreaterThanEqualAndGiaDienTheoThang_NamGreaterThanEqual(hopDongKTX.get().getIdPhongKTX(), dateStart.getMonthValue(), dateStart.getYear());
            List<PhieuDienKTXDTO> phieuDienKTXDTOList = new ArrayList<>();
            Double total;
            for (PhieuDienKTX phieuDienKTX : phieuDienKTXList) {
                total = phieuDienKTX.getSoDienTieuThu() * phieuDienKTX.getGiaDienTheoThang().getGiaDien();
                phieuDienKTXDTOList.add(new PhieuDienKTXDTO(phieuDienKTX.getId(),
                                                            phieuDienKTX.getMaSoKTX(),
                                                            phieuDienKTX.getSoDienTieuThu(),
                                                            phieuDienKTX.isTrangThai(),
                                                            modelMapper.map(phieuDienKTX.getGiaDienTheoThang(), GiaDienTheoThangDTO.class),
                                                            total));
            }
            return ResponseEntity.ok(phieuDienKTXDTOList);
        }
    }

    public ResponseEntity<?> getElectricList(Integer numPage,Integer idTerm, Boolean status) {
        Pageable pageable = PageRequest.of(0*numPage,9*numPage);
        Term term = termRepository.findTermById(idTerm);
        YearMonth termDateStart = YearMonth.from(term.getNgayKetThucDangKy().toInstant());
        YearMonth termDateEnd = YearMonth.from(term.getNgayKetThuc().toInstant());
        List<PhieuDienKTX> phieuDienKTXList = new ArrayList<>();
        if (termDateEnd.getMonthValue() < termDateStart.getMonthValue()){
            phieuDienKTXList = phieuDienKTXRepository.findByStatusAndMonthRange(status,termDateStart.getMonthValue(),12,termDateStart.getYear(),pageable);
            List<PhieuDienKTX> phieuDienKTXList1 = phieuDienKTXRepository.findByStatusAndMonthRange(status,1,termDateEnd.getMonthValue(),termDateEnd.getYear(),pageable);
            phieuDienKTXList.addAll(phieuDienKTXList1);
        }
        else {
            phieuDienKTXList = phieuDienKTXRepository.findByStatusAndMonthRange(status,termDateStart.getMonthValue(),termDateEnd.getMonthValue(),termDateEnd.getYear(),pageable);
        }
        if (phieuDienKTXList.isEmpty()) return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Not Found");
        else{
            List<PhieuDienKTXDTO> phieuDienKTXDTOList = new ArrayList<>();
            Double total;
            for (PhieuDienKTX phieuDienKTX : phieuDienKTXList){
                total = phieuDienKTX.getSoDienTieuThu()*phieuDienKTX.getGiaDienTheoThang().getGiaDien();
                phieuDienKTXDTOList.add(new PhieuDienKTXDTO(phieuDienKTX.getId(),
                        phieuDienKTX.getMaSoKTX(),
                        phieuDienKTX.getSoDienTieuThu(),
                        phieuDienKTX.isTrangThai(),
                        modelMapper.map(phieuDienKTX.getGiaDienTheoThang(), GiaDienTheoThangDTO.class),
                        total));
            }
            return ResponseEntity.ok(phieuDienKTXDTOList);
        }
    }

    public ResponseEntity<?> updateStatus(PhieuDienKTXDTO phieuDienKTXDTO) {
        if(!phieuDienKTXDTO.isTrangThai()) phieuDienKTXDTO.setTrangThai(true);
        else{
            YearMonth thangTruoc = YearMonth.now().minusMonths(1);
            if(phieuDienKTXDTO.getGiaDienTheoThang().getThang()==thangTruoc.getMonthValue())
                phieuDienKTXDTO.setTrangThai(false);
        }
        try {
            phieuDienKTXRepository.save(modelMapper.map(phieuDienKTXDTO,PhieuDienKTX.class));
            return ResponseEntity.ok().body("success");
        }catch (Exception e){
            e.getStackTrace();
            return ResponseEntity.badRequest().body("save fail");
        }
    }
}

