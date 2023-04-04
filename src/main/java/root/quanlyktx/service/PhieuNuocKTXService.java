package root.quanlyktx.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
//import root.quanlyktx.entity.GiaNuocTheoThang;
import root.quanlyktx.dto.GiaNuocTheoThangDTO;
import root.quanlyktx.dto.PhieuNuocKTXDTO;
import root.quanlyktx.entity.*;
import root.quanlyktx.firebase.FBPhieuDienNuocService;
import root.quanlyktx.repository.GiaNuocTheoThangRepository;
import root.quanlyktx.repository.HopDongKTXRepository;
import root.quanlyktx.repository.PhieuNuocKTXRepository;

import java.time.YearMonth;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
@EnableScheduling
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

//    public ResponseEntity<?> addPhieuNuocKTX(PhieuNuocKTXDTO phieuNuocKTXDTO){
//        GiaNuocTheoThang giaNuocTheoThang = new GiaNuocTheoThang(phieuNuocKTXDTO.getGiaNuocTheoThang().getThang(),
//                                                                phieuNuocKTXDTO.getGiaNuocTheoThang().getNam(),
//                                                                phieuNuocKTXDTO.getGiaNuocTheoThang().getGiaNuoc());
//        try {
//            giaNuocTheoThangRepository.save(giaNuocTheoThang);
//            PhieuNuocKTX phieuNuocKTX = new PhieuNuocKTX(phieuNuocKTXDTO.getMaSoKTX(),giaNuocTheoThang,phieuNuocKTXDTO.getLuongNuocTieuThu(),false);
//            phieuNuocKTXRepository.save(phieuNuocKTX);
//            return ResponseEntity.ok().body("success");
//        }catch (Exception e){
//            e.getStackTrace();
//            return ResponseEntity.badRequest().body("error, save fail");
//        }
//    }
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

//    public List<ViewBills> getWaterBills(String mssv){
//        Optional<HopDongKTX> hopDongKTX = Optional.ofNullable(hopDongKTXRepository.findHopDongKTXByMSSVAndTrangThaiTrue(mssv));
//        if(hopDongKTX.isEmpty()) return Collections.emptyList();
//        Date dateStart = hopDongKTX.get().getNgayLamDon();
//        Integer month = dateStart.getMonth()+1;
//        Integer year = dateStart.getYear()+1900;
//        List<PhieuNuocKTX> phieuNuocKTXList = phieuNuocKTXRepository.findAllByMaSoKTXAndGiaNuocTheoThang_ThangGreaterThanEqualAndGiaNuocTheoThang_NamGreaterThanEqual(hopDongKTX.get().getIdPhongKTX(),month,year);
////        List<PhieuNuocKTX> phieuNuocKTXList = hopDongKTX.get().getPhongKTX().getPhieuNuocKTXList();
//        if(phieuNuocKTXList.isEmpty()) return Collections.emptyList();
//        List<PhieuNuocKTXDTO> phieuNuocKTXDTOList = phieuNuocKTXList.stream().map(phieuNuocKTX -> modelMapper.map(phieuNuocKTX,PhieuNuocKTXDTO.class)).collect(Collectors.toList());
//        List<Optional<PhieuNuocKTXDTO>> optionalPhieuNuocKTXDTOList = phieuNuocKTXDTOList.stream().map(phieuNuocKTXDTO -> Optional.ofNullable(phieuNuocKTXDTO)).collect(Collectors.toList());
////        Double total = phieuNuocKTXDTO.getGiaNuocTheoThang().getGiaNuoc()*phieuNuocKTXDTO;
//        List<ViewBills> viewBillsList = new ArrayList<>();
//        for(Optional<PhieuNuocKTXDTO> phieuNuocKTXDTO: optionalPhieuNuocKTXDTOList){
//            viewBillsList.add(new ViewBills(phieuNuocKTXDTO,phieuNuocKTXDTO.get().getGiaNuocTheoThang().getGiaNuoc()*phieuNuocKTXDTO.get().getLuongNuocTieuThu()));
//        }
//        return viewBillsList;
//    }

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
        List<PhieuNuocKTX> phieuNuocKTXList = phieuNuocKTXRepository.findAllByMaSoKTXAndAndGiaNuocTheoThang_NamOrderByGiaNuocTheoThang_ThangDesc(idPhongKTX,year);
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

    @Scheduled(cron = "0 0 0 2 * ?")
    public ResponseEntity<?> addPhieuNuoc() throws ExecutionException,InterruptedException {
        List<PhieuNuocKTXDTO> phieuNuocKTXDTOList = fbPhieuDienNuocService.loadAllPhieuNuocFromFB();
        if (phieuNuocKTXDTOList.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("empty");
        else {
            List<PhieuNuocKTX> phieuNuocKTX = phieuNuocKTXDTOList.stream()
                    .map(phieuNuocKTXDTO -> modelMapper.map(phieuNuocKTXDTO,PhieuNuocKTX.class))
                    .collect(Collectors.toList());
            phieuNuocKTX.forEach(phieuNuocKTX1 -> phieuNuocKTXRepository.save(phieuNuocKTX1));
            return ResponseEntity.ok().body("success");
        }
    }
}
