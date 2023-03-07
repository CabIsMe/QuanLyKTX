package root.quanlyktx.service;


import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import root.quanlyktx.dto.StudentDto;

import root.quanlyktx.entity.Student;
import root.quanlyktx.jwt.AuthEntryPointJwt;
import root.quanlyktx.repository.StudentRepository;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService {
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PasswordEncoder encoder;
    private static final Logger logger = LoggerFactory.getLogger(StudentService.class);

//    @PostConstruct
//    public void handleOtpExpired() {
//        // your initialization code here
//        System.out.println("MyComponent initialized!");
//    }
    public StudentDto getInfo(String username){

//        HandleUserDetail userDetails =
//                (HandleUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Student student = studentRepository.findByUsername(username);
            return modelMapper.map(student, StudentDto.class);

    }
     public List<StudentDto> getAll(){
         List <Student> studentList = studentRepository.findAll();
         return studentList.stream()
                 .map(user -> modelMapper
                 .map(user, StudentDto.class))
                 .collect(Collectors.toList());
     }

     public StudentDto updateSinhVien(String MSSV, StudentDto studentDto){

         if (studentRepository.existsById(MSSV)){
             try{
                 Student student_root = studentRepository.getReferenceById(MSSV);
                 student_root.setHoTen(studentDto.getHoTen());
                 student_root.setGioiTinh(studentDto.isGioiTinh());
                 student_root.setNgaySinh(studentDto.getNgaySinh());
                 student_root.setCMND(studentDto.getCMND());
                 student_root.setSDT(studentDto.getSDT());
                 student_root.setMail(studentDto.getMail());
                 studentRepository.save(student_root);
                 return modelMapper.map(student_root, StudentDto.class);
             }
             catch (Exception e){
                 e.printStackTrace();
             }
         }
         return null;
     }
     public boolean registerStudent(Student std){
        if(studentRepository.existsById(std.getUsername())){
            Student student=studentRepository.getReferenceById(std.getUsername());
            System.out.println(student.toString());
            if(student.getPassword()==null && !student.isStatus()){
              student.setPassword(encoder.encode(std.getPassword()));
              studentRepository.save(student);
              return true;
            }
            logger.error("Account is already taken!");
            return false;
        }
        logger.error("Username does not exist or is not allowed!");
        return false;


     }

    public void updateStatus(String username, boolean stt){
        try {
            Student student = studentRepository.getReferenceById(username);
            student.setStatus(stt);
            studentRepository.save(student);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public Student getStudentByUsername(String username){
        return studentRepository.findByUsername(username);
    }
    public void deleteStudent(String username){
        if(studentRepository.existsById(username)){
            Student student=studentRepository.findByUsername(username);
            student.setPassword(null);
            studentRepository.save(student);
        }
        logger.error("Account is not exist to delete!");
    }

}
