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
import root.quanlyktx.dto.GiaNuocTheoThangDTO;
import root.quanlyktx.dto.PhieuNuocKTXDTO;
import root.quanlyktx.entity.*;
import root.quanlyktx.firebase.FBPhieuDienNuocService;
import root.quanlyktx.repository.GiaNuocTheoThangRepository;
import root.quanlyktx.repository.HopDongKTXRepository;
import root.quanlyktx.repository.PhieuNuocKTXRepository;
import root.quanlyktx.repository.TermRepository;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.*;

@Service
public class PhieuNuocKTXService {

    @Autowired
    private TermRepository termRepository;
    @Autowired
    private HopDongKTXRepository hopDongKTXRepository;
    @Autowired
    private PhieuNuocKTXRepository phieuNuocKTXRepository;
    @Autowired
    private ModelMapper modelMapper;

    private static final Logger logger = LoggerFactory.getLogger(PhieuNuocKTXService.class);

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

    public ResponseEntity<?> updatePhieuNuocKTX(PhieuNuocKTXDTO phieuNuocKTXDTO){
        if(!phieuNuocKTXDTO.isTrangThai()) phieuNuocKTXDTO.setTrangThai(true);
        else{
            YearMonth thangTruoc = YearMonth.now().minusMonths(1);
            if(phieuNuocKTXDTO.getGiaNuocTheoThang().getThang()==thangTruoc.getMonthValue())
                phieuNuocKTXDTO.setTrangThai(false);
        }
        try {
            phieuNuocKTXRepository.save(modelMapper.map(phieuNuocKTXDTO,PhieuNuocKTX.class));
            return ResponseEntity.ok().body("success");
        }catch (Exception e){
            e.getStackTrace();
            return ResponseEntity.badRequest().body("save fail");
        }
    }

    public ResponseEntity<?> getWaterBills(){
        String mssv = getUsernameFromSecurityContextHolder();
        Date currentDate = new Date();
        Optional<HopDongKTX> hopDongKTX = Optional.ofNullable(hopDongKTXRepository.findHopDongKTXByMSSVAndTerm_NgayKetThucDangKyBeforeAndTerm_NgayKetThucAfter(mssv,currentDate,currentDate));
        if(hopDongKTX.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Empty");
        else{
            YearMonth dateStart = YearMonth.from(hopDongKTX.get().getNgayLamDon().toInstant());
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

    public ResponseEntity<?> getPhieuNuocList(Integer numPage,Integer idTerm,Boolean status) {
        Pageable pageable = PageRequest.of(0,9*numPage);
        Term term = termRepository.findTermById(idTerm);
        YearMonth termDateStart = YearMonth.from(term.getNgayKetThucDangKy().toInstant());
        YearMonth termDateEnd = YearMonth.from(term.getNgayKetThuc().toInstant());
        List<PhieuNuocKTX> phieuNuocKTXList = new ArrayList<>();
        if(termDateEnd.getMonthValue() < termDateStart.getMonthValue()){
            phieuNuocKTXList = phieuNuocKTXRepository.findByStatusAndMonthRange(status,termDateStart.getMonthValue(),12,termDateStart.getYear(),pageable);
            List<PhieuNuocKTX> phieuNuocKTXList1 = phieuNuocKTXRepository.findByStatusAndMonthRange(status,1,termDateEnd.getMonthValue(),termDateEnd.getYear(),pageable);
            phieuNuocKTXList.addAll(phieuNuocKTXList1);
        }
        else {
            phieuNuocKTXList = phieuNuocKTXRepository.findByStatusAndMonthRange(status,termDateStart.getMonthValue(),termDateEnd.getYear(),termDateEnd.getYear(),pageable);
        }
        if (phieuNuocKTXList.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("empty");
        else{
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


}
