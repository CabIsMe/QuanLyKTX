package root.quanlyktx.firebase;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Service;
import root.quanlyktx.dto.StudentDto;
import root.quanlyktx.entity.Student;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
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



    public void createMockStudent()throws InterruptedException, ExecutionException{
        String[] firstNames = {"John", "Emma", "Nguyen Thi", "Tran Thi", "Le Thi", "Olivia","Tran Van" ,"Nguyen Van", "Le Van", "Ho Duc", "Le Thanh", "Nguyen Ngoc", "Tran Quang Ngoc" };
        String[] lastNames = {"Doe", "Smith", "Johnson", "Williams", "Jones", "Nam", "Trung","Thanh", "Lam", "Son", "Tuan", "Thao", "Huynh", "Duc"};

        // create an obj
        Random random = new Random();

        for(int i =0; i<20; i++){
            String randomName = firstNames[random.nextInt(firstNames.length)] + " " + lastNames[random.nextInt(lastNames.length)];
            boolean randomGender= random.nextBoolean();
            String randomMSSV= "n19dccn" + String.valueOf(310+i);

            MockStudent mockStudent= new MockStudent(randomMSSV, randomName, randomGender );

            try{
                Firestore dbFirestore = FirestoreClient.getFirestore();
                dbFirestore.collection("students").document(mockStudent.getUsername()).set(mockStudent);

            }
            catch (Exception e){
                System.out.println(e.getMessage());
            }
        }

    }

}
@Getter
@Setter
@ToString
class MockStudent{
    String username;
    String hoTen;
    boolean gioiTinh;

    public MockStudent(String username, String name, boolean gender) {
        this.username = username;
        this.hoTen = name;
        this.gioiTinh = gender;
    }

}
