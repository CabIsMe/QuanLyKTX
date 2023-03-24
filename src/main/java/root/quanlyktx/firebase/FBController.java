package root.quanlyktx.firebase;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import root.quanlyktx.dto.StudentDto;
import root.quanlyktx.entity.Student;

import java.util.ArrayList;
import java.util.List;

import java.util.concurrent.ExecutionException;

@RestController
public class FBController {
    @Autowired
    FBStudentService fbStudentService;
    @PostMapping("/createInFirebase")
    public String createPatient(@RequestBody Student student ) throws InterruptedException, ExecutionException {
        try{
            Firestore dbFirestore = FirestoreClient.getFirestore();
            ApiFuture<WriteResult> collectionsApiFuture = dbFirestore.collection("students").document(student.getUsername()).set(student);
            return collectionsApiFuture.get().getUpdateTime().toString();
        }
        catch (Exception e){
            return e.getMessage();
        }
    }

    @GetMapping("/getFirebase")

    public Student getStudentInFB() throws InterruptedException, ExecutionException{
        Firestore dbFirestore = FirestoreClient.getFirestore();
        DocumentReference documentReference = dbFirestore.collection("students").document("n19dccn018");
        ApiFuture<DocumentSnapshot> future = documentReference.get();
        DocumentSnapshot document = future.get();
        Student student = null;

        if(document.exists()) {
            student = document.toObject(Student.class);
            return student;
        }else {
            return null;
        }
    }
    @GetMapping("getAllStudentInFB")
    List<StudentDto> loadAllStudent() throws InterruptedException, ExecutionException{
       return fbStudentService.loadAllStudentFromFB();
    }
}
