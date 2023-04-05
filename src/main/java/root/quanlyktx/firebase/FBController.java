package root.quanlyktx.firebase;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import root.quanlyktx.dto.StudentDto;
import root.quanlyktx.entity.Student;

import root.quanlyktx.model.RoomDetails;

import java.util.List;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/firebase/")
public class FBController {
    private static final Logger logger = LoggerFactory.getLogger(FBController.class);

    @Autowired
    FBStudentService fbStudentService;
    @Autowired
    FBPhieuDienNuocService fbPhieuDienNuocService;

    @Autowired
    IImageService imageService;

    @Autowired
    FBBillService fbBillService;

    @PostMapping("createInFirebase")
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

//    @GetMapping("createAutoBills")
//    public boolean createAutoBills() throws InterruptedException, ExecutionException{
//        return fbBillService.createNewBillInFB();
//    }

    @GetMapping("getFirebase")

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
//    @GetMapping("getAllStudentInFB")
//    List<StudentDto> loadAllStudent() throws InterruptedException, ExecutionException{
//       return fbStudentService.loadAllStudentFromFB();
//    }

    @PostMapping("upload-image")
    public ResponseEntity<?> create(@ModelAttribute RoomDetails roomDetails, @RequestParam(name = "files") MultipartFile[] files) {
        System.out.println(roomDetails.toString());
        for (MultipartFile file : files) {
            try {

                String fileName = imageService.save(file);

                String imageUrl = imageService.getImageUrl(fileName);
                System.out.println(imageUrl);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return ResponseEntity.ok().build();
    }

//    @GetMapping("getlink-image")
//    public String getLinkImg(){
//        return imageService.getImageUrl()
//    }
}
