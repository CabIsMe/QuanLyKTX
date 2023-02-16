package root.quanlyktx.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import root.quanlyktx.entity.GiaNuocTheoThang;
import root.quanlyktx.repository.GiaNuocTheoThangRepository;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
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
}
