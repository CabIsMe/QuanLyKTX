package root.quanlyktx.firebase;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import root.quanlyktx.dto.PhieuDienKTXDTO;
import root.quanlyktx.dto.PhieuNuocKTXDTO;
import root.quanlyktx.entity.HopDongKTX;
import root.quanlyktx.entity.PhieuDienKTX;
import root.quanlyktx.entity.PhongKTX;
import root.quanlyktx.entity.Term;
import root.quanlyktx.model.InputBillPerMonth;
import root.quanlyktx.repository.HopDongKTXRepository;
import root.quanlyktx.repository.PhongKTXRepository;
import root.quanlyktx.repository.TermRepository;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ExecutionException;

@Service
public class FBBillService {
    @Autowired
    private HopDongKTXRepository hopDongKTXRepository;

    @Autowired
    private PhongKTXRepository phongKTXRepository;

    @Autowired
    private TermRepository termRepository;


//    @Scheduled(fixedRate = 2000)
//    @Scheduled(cron = "0 0 0 1 * ?")
    public boolean createNewBillInFB() throws InterruptedException, ExecutionException {
        try{
            Integer currentTerm=termRepository.getCurrentTerm();
            if(currentTerm==null)
                return false;

            List<PhongKTX> phongKTXList= phongKTXRepository.findAllByTrangThaiTrue();
            for (PhongKTX phongKTX: phongKTXList){

                if(hopDongKTXRepository.existsByIdPhongKTXAndIdTermAndTrangThaiTrue(phongKTX.getId(), currentTerm)){
                    Firestore dbFirestore = FirestoreClient.getFirestore();
                    InputBillPerMonth inputBillPerMonth= new InputBillPerMonth();

                    dbFirestore.collection("bills")
                            .document(phongKTX.getId().toString()).set(inputBillPerMonth);
                    System.out.println(inputBillPerMonth.toString());

                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
