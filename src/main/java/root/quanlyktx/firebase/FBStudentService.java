package root.quanlyktx.firebase;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;
import root.quanlyktx.dto.StudentDto;
import root.quanlyktx.entity.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


@Service
public class FBStudentService {

    public List<StudentDto> loadAllStudentFromFB() throws InterruptedException, ExecutionException{
        Firestore dbFirestore = FirestoreClient.getFirestore();
        CollectionReference collectionReference=dbFirestore.collection("students");
        Iterable<DocumentReference> documentReferences= collectionReference.listDocuments();
        List<StudentDto>students= new ArrayList<>();

        documentReferences.forEach(documentReference1 -> {
            ApiFuture<DocumentSnapshot> future=documentReference1.get();
            try {
                DocumentSnapshot snapshot=future.get();
                StudentDto studentDto=snapshot.toObject(StudentDto.class);
                if (studentDto != null) {
                    studentDto.setUsername(snapshot.getId());
                }
                students.add(studentDto);

            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }


        });

        return students;
    }


}
