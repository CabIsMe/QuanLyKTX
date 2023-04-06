package root.quanlyktx.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import root.quanlyktx.entity.GiaNuocTheoThang;
import root.quanlyktx.repository.GiaNuocTheoThangRepository;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@EnableScheduling
public class GiaNuocTheoThangSerive {

//    @Autowired
//    GiaNuocTheoThangRepository giaNuocTheoThangRepository;
//    public GiaNuocTheoThang findByLastMonth(){
//               int id;
//                Date today = new Date();
//        Calendar cal = Calendar.getInstance();
//        cal.setTime(today);
//        int month = cal.get(Calendar.MONTH);
//        int year = cal.get(Calendar.YEAR);
//        if(month == 1){
//            id = 120000+ year-1;
//        }else{
//            id = (month-1)*10000+year;
//        }
//        return giaNuocTheoThangRepository.findById(id).get();}
    @Autowired
    GiaNuocTheoThangRepository giaNuocTheoThangRepository;

    public List<GiaNuocTheoThang> getAll(){ return giaNuocTheoThangRepository.findAll();}
    public GiaNuocTheoThang findById(){
        int id;
        int month = LocalDate.now().getMonth().getValue();
        int year = LocalDate.now().getYear();
        if(month == 1){
            id = 120000+ year-1;
        }else{
            id = (month-1)*10000+year;
        }
        return giaNuocTheoThangRepository.findById(id).get();
    }

    @Scheduled(cron = "0 0 0 1 * ?")
    public ResponseEntity<?> addGiaNuocNextMonth(){
        LocalDate currentDate = LocalDate.now();
        int month = currentDate.getMonth().getValue();
        int year = currentDate.getYear();

        GiaNuocTheoThang giaNuocTheoThang = giaNuocTheoThangRepository.findGiaNuocTheoThangByNamAndThang(month,year);
        Double giaThangTruoc = giaNuocTheoThang.getGiaNuoc();
        if(month==12){
            month=1;
            year+=1;
        }
        giaNuocTheoThang = new GiaNuocTheoThang(month,year,giaThangTruoc);
        try {
            giaNuocTheoThangRepository.save(giaNuocTheoThang);
            return ResponseEntity.ok().body("success");
        }catch (Exception e){
            e.getStackTrace();
            return ResponseEntity.badRequest().body("save fail");
        }
    }
}
