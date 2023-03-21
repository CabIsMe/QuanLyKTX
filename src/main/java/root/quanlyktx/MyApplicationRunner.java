package root.quanlyktx;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import root.quanlyktx.entity.HopDongKTX;
import root.quanlyktx.entity.Term;
import root.quanlyktx.repository.HopDongKTXRepository;
import root.quanlyktx.repository.TermRepository;

import java.util.Date;

@Component
public class MyApplicationRunner implements ApplicationRunner {
    @Autowired
    TermRepository termRepository;
    @Autowired
    HopDongKTXRepository hopDongKTXRepository;
    @Override
    @Transactional
//    @Scheduled(cron = "0 * * ? * *")
    public void run(ApplicationArguments args) throws Exception {
        // Code to run after the application has started
//        Date date= new Date();
//        Term term= termRepository.getByNgayMoDangKyBeforeAndNgayKetThucDangKyAfter(date,date);
//        Date dateExpired= new Date(date.getTime()-(term.getHanDongPhi()*60*60*1000*24));
//        System.out.println(dateExpired);
//        if(hopDongKTXRepository.findAllByNgayLamDonBeforeAndTrangThaiFalse(dateExpired).isEmpty()){
//            return;
//        }
//        hopDongKTXRepository.deleteAllByNgayLamDonBeforeAndTrangThaiFalse(dateExpired);
    }
}