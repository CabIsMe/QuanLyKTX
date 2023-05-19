package root.quanlyktx.firebase;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import root.quanlyktx.service.HopDongKTXService;

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

    private static final Logger logger = LoggerFactory.getLogger(FBBillService.class);

//    @Scheduled(fixedRate = 10000)
//    @Scheduled(cron = "0 0 0 1 * ?")
    public void createNewBillInFB() throws InterruptedException, ExecutionException {
        try{
            Integer currentTerm=termRepository.getCurrentTerm();
            if(currentTerm==null){
                logger.error("Term is Null");
                return;
            }

            List<PhongKTX> phongKTXList= phongKTXRepository.findAllByTrangThaiTrue();
            Firestore dbFirestore = FirestoreClient.getFirestore();
            InputBillPerMonth inputBillPerMonth= new InputBillPerMonth();
            CollectionReference billsCollection = dbFirestore.collection("bills");
            Iterable<DocumentReference> documentReferences= billsCollection.listDocuments();
            for (DocumentReference documentRef : documentReferences) {
                documentRef.delete();
            }
            for (PhongKTX phongKTX: phongKTXList){

                if(hopDongKTXRepository.existsByIdPhongKTXAndIdTermAndTrangThaiTrue(phongKTX.getId(), currentTerm)){

                    billsCollection.document(phongKTX.getId().toString()).set(inputBillPerMonth);
                    System.out.println(inputBillPerMonth.toString());

                }
            }
        }
        catch (Exception e){
            logger.error("Fail to create data on FB");
        }
    }

}
