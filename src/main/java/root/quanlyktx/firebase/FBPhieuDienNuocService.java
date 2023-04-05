package root.quanlyktx.firebase;


import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import root.quanlyktx.entity.GiaDienTheoThang;
import root.quanlyktx.entity.GiaNuocTheoThang;
import root.quanlyktx.entity.PhieuDienKTX;
import root.quanlyktx.entity.PhieuNuocKTX;
import root.quanlyktx.model.InputBillPerMonth;
import root.quanlyktx.repository.GiaDienTheoThangRepository;
import root.quanlyktx.repository.GiaNuocTheoThangRepository;
import root.quanlyktx.repository.PhieuDienKTXRepository;
import root.quanlyktx.repository.PhieuNuocKTXRepository;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.concurrent.ExecutionException;

@Service
@EnableScheduling
public class FBPhieuDienNuocService {
    @Autowired
    PhieuDienKTXRepository phieuDienKTXRepository;
    @Autowired
    PhieuNuocKTXRepository phieuNuocKTXRepository;
    @Autowired
    GiaDienTheoThangRepository giaDienTheoThangRepository;
    @Autowired
    GiaNuocTheoThangRepository giaNuocTheoThangRepository;

//    @Scheduled(fixedRate = 2000)
    @Scheduled(cron = "0 0 0 2 * ?")
    @Transactional
    public void loadAllAndSavePhieuDienNuocFromFB() throws InterruptedException, ExecutionException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        CollectionReference collectionReference=dbFirestore.collection("bills");
        Iterable<DocumentReference> documentReferences= collectionReference.listDocuments();

        documentReferences.forEach(documentReference1 -> {
            ApiFuture<DocumentSnapshot> future=documentReference1.get();
            try {
                DocumentSnapshot snapshot=future.get();
                InputBillPerMonth inputBillPerMonth = snapshot.toObject(InputBillPerMonth.class);
                LocalDate currentDate = LocalDate.now();
                GiaNuocTheoThang giaNuocTheoThang = giaNuocTheoThangRepository.findGiaNuocTheoThangByNamAndThang(currentDate.getYear(),currentDate.getMonthValue());
                GiaDienTheoThang giaDienTheoThang = giaDienTheoThangRepository.findGiaDienTheoThangByNamAndThang(currentDate.getYear(),currentDate.getMonthValue());
                PhieuNuocKTX phieuNuocKTX = new PhieuNuocKTX(Integer.parseInt(snapshot.getId()),giaNuocTheoThang,inputBillPerMonth.getAmountOfWater(),false);
                phieuNuocKTXRepository.save(phieuNuocKTX);
                PhieuDienKTX phieuDienKTX = new PhieuDienKTX(Integer.parseInt(snapshot.getId()),giaDienTheoThang,inputBillPerMonth.getAmountOfElectric(),false);
                phieuDienKTXRepository.save(phieuDienKTX);

            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        });
    }

}
