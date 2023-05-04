package root.quanlyktx.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import root.quanlyktx.entity.GiaNuocTheoThang;
import root.quanlyktx.repository.GiaNuocTheoThangRepository;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

@Service
@EnableScheduling
public class GiaNuocTheoThangSerive {
    @Autowired
    GiaNuocTheoThangRepository giaNuocTheoThangRepository;

    private static final Logger logger = LoggerFactory.getLogger(GiaNuocTheoThangSerive.class);

    public List<GiaNuocTheoThang> getAllWaterPrice(){
        return giaNuocTheoThangRepository.findAll();
    }

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
//    @Scheduled(fixedRate = 10000)
    @Scheduled(cron = "0 0 0 1 * ?")
    public void addGiaNuocNextMonth(){
        LocalDate currentDate = LocalDate.now();
        int month = currentDate.getMonth().getValue();
        int year = currentDate.getYear();

        GiaNuocTheoThang giaNuocTheoThang = giaNuocTheoThangRepository.findGiaNuocTheoThangByNamAndThang(year, month);
        if(giaNuocTheoThang==null){
            logger.error("Current Cost of Water Not Found!");
            return;
        }

        if(month==12){
            month=1;
            year+=1;
        }
        else
            month+=1;
        if(giaNuocTheoThangRepository.findGiaNuocTheoThangByNamAndThang(year, month)!=null){
            logger.info("Saved that Record");
            return;
        }
        GiaNuocTheoThang giaNuocTheoThangNew = new GiaNuocTheoThang(month,year,giaNuocTheoThang.getGiaNuoc());
        try {
            giaNuocTheoThangRepository.save(giaNuocTheoThangNew);
            logger.info("success");
        }catch (Exception e){
            e.getStackTrace();
            logger.error("save fail");
        }
    }

    public boolean editWaterPrice(Integer id, Double price){
        Optional<GiaNuocTheoThang> optional= giaNuocTheoThangRepository.findById(id);
        if(optional.isEmpty() || price == null){
           logger.error("Null of value or not found id");
            return false;
        }
        GiaNuocTheoThang giaNuocTheoThang=optional.get();
        YearMonth yearMonth1 = YearMonth.of(giaNuocTheoThang.getNam(), giaNuocTheoThang.getThang());
        YearMonth yearMonth2 = YearMonth.now();
        if(yearMonth1.isAfter(yearMonth2)){
            giaNuocTheoThang.setGiaNuoc(price);
            try{
                giaNuocTheoThangRepository.save(giaNuocTheoThang);
            }catch (Exception e){
                e.printStackTrace();
                logger.error("2");
                return false;
            }
            return true;
        }
        else{
            logger.error("3");
            return false;
        }

    }
}
