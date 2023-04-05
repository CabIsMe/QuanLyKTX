package root.quanlyktx.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import root.quanlyktx.dto.GiaNuocTheoThangDTO;
import root.quanlyktx.dto.PhieuNuocKTXDTO;
import root.quanlyktx.entity.*;
import root.quanlyktx.firebase.FBPhieuDienNuocService;
import root.quanlyktx.repository.GiaNuocTheoThangRepository;
import root.quanlyktx.repository.HopDongKTXRepository;
import root.quanlyktx.repository.PhieuNuocKTXRepository;

import java.time.YearMonth;
import java.util.*;

@Service
public class PhieuNuocKTXService {

    @Autowired
    FBPhieuDienNuocService fbPhieuDienNuocService;
    @Autowired
    HopDongKTXRepository hopDongKTXRepository;
    @Autowired
    PhieuNuocKTXRepository phieuNuocKTXRepository;
    @Autowired
    GiaNuocTheoThangRepository giaNuocTheoThangRepository;
    @Autowired
    ModelMapper modelMapper;


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

    public ResponseEntity<?> getWaterBills(String mssv){
        Date currentDate = new Date();
        Optional<HopDongKTX> hopDongKTX = Optional.ofNullable(hopDongKTXRepository.findHopDongKTXByMSSVAndTerm_NgayMoDangKyBeforeAndTerm_NgayKetThucAfter(mssv,currentDate,currentDate));
        if (hopDongKTX.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Empty");
        if(!hopDongKTX.get().isTrangThai()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Empty");
        else{
            Date dateStart = hopDongKTX.get().getNgayLamDon();
            Integer month = dateStart.getMonth()+1;
            Integer year = dateStart.getYear()+1900;
            List<PhieuNuocKTX> phieuNuocKTXList = phieuNuocKTXRepository.findAllByMaSoKTXAndGiaNuocTheoThang_ThangGreaterThanEqualAndGiaNuocTheoThang_NamGreaterThanEqual(hopDongKTX.get().getIdPhongKTX(),month,year);
            if (phieuNuocKTXList.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("empty");
            else {
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

    public ResponseEntity<?> getPhieuNuocList(Integer idPhongKTX, Integer year) {
        List<PhieuNuocKTX> phieuNuocKTXList = phieuNuocKTXRepository.findAllByMaSoKTXAndGiaNuocTheoThang_NamOrderByGiaNuocTheoThang_ThangDesc(idPhongKTX,year);
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
