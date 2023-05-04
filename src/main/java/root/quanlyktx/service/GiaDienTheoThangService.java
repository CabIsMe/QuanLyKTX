package root.quanlyktx.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import root.quanlyktx.entity.GiaDienTheoThang;
import root.quanlyktx.repository.GiaDienTheoThangRepository;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

@Service
@EnableScheduling
public class GiaDienTheoThangService {
    @Autowired
    GiaDienTheoThangRepository giaDienTheoThangRepository;
    private static final Logger logger = LoggerFactory.getLogger(GiaDienTheoThangService.class);
    public List<GiaDienTheoThang> getAllElectricPrice(){
        return  giaDienTheoThangRepository.findAll();
    }

//    @Scheduled(fixedRate = 10000)
    @Scheduled(cron = "0 0 0 1 * ?")
    public void addGiaDienNextMonth(){
        LocalDate currentDate = LocalDate.now();
        Integer month = currentDate.getMonth().getValue();
        Integer year = currentDate.getYear();

        GiaDienTheoThang giaDienTheoThang = giaDienTheoThangRepository.findGiaDienTheoThangByNamAndThang(year, month);
        if(giaDienTheoThang==null){
            logger.error("Current Cost of Electricity Not Found!");
            return;
        }

        if(month==12){
            month=1;
            year+=1;
        }
        else{
            month+=1;
        }
        if(giaDienTheoThangRepository.findGiaDienTheoThangByNamAndThang(year, month)!=null){
            logger.info("Saved that Record");
            return;
        }
        GiaDienTheoThang giaDienTheoThangNew = new GiaDienTheoThang(month,year,giaDienTheoThang.getGiaDien());
        try {
            giaDienTheoThangRepository.save(giaDienTheoThangNew);
            logger.info("success");
        }catch (Exception e){
            e.getStackTrace();
            logger.warn("save fail");
        }
    }


    public boolean editElectricPrice(Integer id, Double price){
        Optional<GiaDienTheoThang> optional= giaDienTheoThangRepository.findById(id);
        if(optional.isEmpty() || price==null){
            return false;
        }
        GiaDienTheoThang giaDienTheoThang=optional.get();
        YearMonth yearMonth1 = YearMonth.of(giaDienTheoThang.getNam(), giaDienTheoThang.getThang());
        YearMonth yearMonth2 = YearMonth.now();
        if(yearMonth1.isAfter(yearMonth2)){
            giaDienTheoThang.setGiaDien(price);
            try{
                giaDienTheoThangRepository.save(giaDienTheoThang);
            }catch (Exception e){
                e.printStackTrace();
                return false;
            }
            return true;
        }
        else
            return false;
    }
}
