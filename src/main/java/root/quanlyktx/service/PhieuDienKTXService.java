package root.quanlyktx.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;
import root.quanlyktx.dto.GiaDienTheoThangDTO;
import root.quanlyktx.dto.PhieuDienKTXDTO;
import root.quanlyktx.entity.HopDongKTX;
import root.quanlyktx.entity.PhieuDienKTX;
import root.quanlyktx.firebase.FBPhieuDienNuocService;
import root.quanlyktx.repository.HopDongKTXRepository;
import root.quanlyktx.repository.PhieuDienKTXRepository;

import java.time.YearMonth;
import java.util.*;

@Service
@EnableScheduling
public class PhieuDienKTXService {
    @Autowired
    private FBPhieuDienNuocService fbPhieuDienNuocService;
    @Autowired
    private HopDongKTXRepository hopDongKTXRepository;
    @Autowired
    private PhieuDienKTXRepository phieuDienKTXRepository;
    @Autowired
    private ModelMapper modelMapper;

    public ResponseEntity<?> getElectricBills(String mssv) {
        Date currentDate = new Date();
        Optional<HopDongKTX> hopDongKTX = Optional.ofNullable(hopDongKTXRepository.findHopDongKTXByMSSVAndTerm_NgayMoDangKyBeforeAndTerm_NgayKetThucAfter(mssv, currentDate, currentDate));
        if (!hopDongKTX.get().isTrangThai()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Empty");
        else {
            Date dateStart = hopDongKTX.get().getNgayLamDon();
            Integer month = dateStart.getMonth() + 1;
            Integer year = dateStart.getYear() + 1900;
            List<PhieuDienKTX> phieuDienKTXList = phieuDienKTXRepository.findAllByMaSoKTXAndGiaDienTheoThang_ThangGreaterThanEqualAndGiaDienTheoThang_NamGreaterThanEqual(hopDongKTX.get().getIdPhongKTX(), month, year);
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

    public ResponseEntity<?> getElectricList(Integer idPhongKTX, Integer year) {
        List<PhieuDienKTX> phieuDienKTXList = phieuDienKTXRepository.findAllByMaSoKTXAndGiaDienTheoThang_NamOrderByGiaDienTheoThang_ThangDesc(idPhongKTX,year);
        if (phieuDienKTXList.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("empty");
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

