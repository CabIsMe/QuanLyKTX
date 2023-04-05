package root.quanlyktx.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import root.quanlyktx.entity.GiaDienTheoThang;
import root.quanlyktx.entity.GiaNuocTheoThang;
import root.quanlyktx.repository.GiaDienTheoThangRepository;

import java.time.LocalDate;

@Service
@EnableScheduling
public class GiaDienTheoThangService {
    @Autowired
    GiaDienTheoThangRepository giaDienTheoThangRepository;

    @Scheduled(cron = "0 0 0 1 * ?")
    public ResponseEntity<?> addGiaDienNextMonth(){
        LocalDate currentDate = LocalDate.now();
        int month = currentDate.getMonth().getValue();
        int year = currentDate.getYear();

        GiaDienTheoThang giaDienTheoThang = giaDienTheoThangRepository.findGiaDienTheoThangByNamAndThang(month,year);
        Double giaThangTruoc = giaDienTheoThang.getGiaDien();
        if(month==12){
            month=1;
            year+=1;
        }
        giaDienTheoThang = new GiaDienTheoThang(month,year,giaThangTruoc);
        try {
            giaDienTheoThangRepository.save(giaDienTheoThang);
            return ResponseEntity.ok().body("success");
        }catch (Exception e){
            e.getStackTrace();
            return ResponseEntity.badRequest().body("save fail");
        }
    }
}
