package root.quanlyktx.firebase;


import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import root.quanlyktx.dto.PhieuDienKTXDTO;
import root.quanlyktx.dto.PhieuNuocKTXDTO;
import root.quanlyktx.dto.StudentDto;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class FBPhieuDienNuocService {
    public List<PhieuNuocKTXDTO> loadAllPhieuNuocFromFB() throws InterruptedException, ExecutionException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        CollectionReference collectionReference=dbFirestore.collection("bills");
        Iterable<DocumentReference> documentReferences= collectionReference.listDocuments();
        List<PhieuNuocKTXDTO>phieuNuocKTXDTOS= new ArrayList<>();

        documentReferences.forEach(documentReference1 -> {
            ApiFuture<DocumentSnapshot> future=documentReference1.get();
            try {
                DocumentSnapshot snapshot=future.get();
                phieuNuocKTXDTOS.add(snapshot.toObject(PhieuNuocKTXDTO.class));

            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        });

        return phieuNuocKTXDTOS;
    }

    public List<PhieuDienKTXDTO> loadAllPhieuDienFromFB() throws InterruptedException, ExecutionException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        CollectionReference collectionReference=dbFirestore.collection("bills");
        Iterable<DocumentReference> documentReferences= collectionReference.listDocuments();
        List<PhieuDienKTXDTO>phieuDienKTXDTOS= new ArrayList<>();

        documentReferences.forEach(documentReference1 -> {
            ApiFuture<DocumentSnapshot> future=documentReference1.get();
            try {
                DocumentSnapshot snapshot=future.get();
                phieuDienKTXDTOS.add(snapshot.toObject(PhieuDienKTXDTO.class));

            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        });

        return phieuDienKTXDTOS;
    }
}
