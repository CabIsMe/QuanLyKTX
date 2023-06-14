package root.quanlyktx.service;



import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import root.quanlyktx.dto.StudentDto;

import root.quanlyktx.email.EmailDetails;
import root.quanlyktx.email.EmailServiceIml;
import root.quanlyktx.entity.HopDongKTX;
import root.quanlyktx.entity.Student;
import root.quanlyktx.firebase.FBStudentService;
import root.quanlyktx.model.PasswordEditing;
import root.quanlyktx.repository.HopDongKTXRepository;
import root.quanlyktx.repository.StudentRepository;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private OtpService otpService;
    @Autowired
    private FBStudentService fbStudentService;
    @Autowired
    private EmailServiceIml emailServiceIml;
    private static final Logger logger = LoggerFactory.getLogger(StudentService.class);
    private static final int PASSWORD_LENGTH = 10;
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstu0123456789";

    @PreAuthorize("hasAuthority('student')")
    public StudentDto getInfo(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication.isAuthenticated()){
            Student student = studentRepository.findByUsername(authentication.getName());
            if(student!=null){

                return modelMapper.map(student, StudentDto.class);
            }
        }


        return null;

    }
     public List<StudentDto> getAll(){
         List <Student> studentList = studentRepository.findAll();
         return studentList.stream()
                 .map(user -> modelMapper
                 .map(user, StudentDto.class))
                 .collect(Collectors.toList());
     }

    public List<StudentDto> getAllStudentEnabled(Integer pageNo, Integer pageSize, String sortBy, boolean typeSort, Boolean gender)
    {
        Pageable paging;
         if(typeSort){
             paging  = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).ascending());
         }
         else{
            paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending());
         }
        Page<Student> pagedResult;
         if(gender==null){
             pagedResult = studentRepository.findAllByStatusTrue(paging);
         }
         else{
             pagedResult = studentRepository.findAllByStatusTrueAndGioiTinh(paging, gender);
         }
         if(pagedResult.hasContent()) {
            return pagedResult.getContent().stream()
                    .map(user -> modelMapper
                            .map(user, StudentDto.class))
                    .collect(Collectors.toList());
        } else {
            return new ArrayList<StudentDto>();
        }
    }

//     public List<StudentDto> getAllStudentInDorm(boolean typeSort){
//        if(typeSort)
//            return studentRepository.findAllByStatusTrueOrderByUsernameAsc()
//                    .stream()
//                        .map(user -> modelMapper
//                        .map(user, StudentDto.class))
//                        .collect(Collectors.toList());
//        else{
//            return studentRepository.findAllByStatusTrueOrderByUsernameDesc().stream()
//                    .map(user -> modelMapper
//                            .map(user, StudentDto.class))
//                    .collect(Collectors.toList());
//        }
//    }

    @Transactional(rollbackFor = {Exception.class, Throwable.class})
    public ResponseEntity<?> registerStudent(Student std) {
        if(studentRepository.existsById(std.getUsername())){
            Student student=studentRepository.findByUsername(std.getUsername());
            if(student.getPassword()==null && !student.isStatus()){
              student.setPassword(encoder.encode(std.getPassword()));
              studentRepository.save(student);
                try {
                    otpService.sendOTP(student);
                } catch (Exception e) {
                    e.printStackTrace();
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Send mail error");
                }
                return ResponseEntity.ok().body("Register Success");
            }
            logger.error("Account is already taken!");
            return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body("Account is already taken!");
//            throw new Exception("Account is already taken!");
        }
        logger.error("Username does not exist or is not allowed!");
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body("Username does not exist!");
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
    public boolean changePassword(String username, String password){
        Student student1=studentRepository.findByUsername(username);
        if(password==null)
            return false;
        student1.setPassword(encoder.encode(password));
        studentRepository.save(student1);
        return true;
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

    public List<StudentDto> getListByTitle(String title) {
        List <Student> students= studentRepository.search(title);

//        if(students.isEmpty() ){
//            String tmp=title.charAt(0)+ title.substring(1, 2).toUpperCase() + title.substring(2);
//            logger.warn(tmp);
//            students=studentRepository.search(tmp);
//            if(students.isEmpty() ){
//                students=studentRepository.search(title);
//            }
//        }
        return students.stream().map(content -> modelMapper.map(content,StudentDto.class)).collect(Collectors.toList());
    }

    public List<StudentDto> getAllBySearchKey(String username, String name){
        List<Student> students = studentRepository.findAllByUsernameLikeOrHoTenLike("%"+username+"%", "%"+name+"%");

        return students.stream()
                .map(student -> modelMapper
                .map(student, StudentDto.class))
                .collect(Collectors.toList());
    }

    public boolean addNewStudent() throws ExecutionException, InterruptedException {
        List<StudentDto> studentDtoList=fbStudentService.loadAllStudentFromFB();
        if(studentDtoList.isEmpty())
            return false;

        List<Student> studentList= studentDtoList.stream()
                .map(studentDto -> modelMapper.map(studentDto, Student.class))
                .collect(Collectors.toList());
        studentList.forEach(student ->
        {
//-------------Tạo data giả
            student.setCMND("123456789");
            student.setSDT("0123456789");
            Calendar calendar = Calendar.getInstance();
            calendar.set(2001, Calendar.FEBRUARY, 2);
            Date date = calendar.getTime();
            student.setNgaySinh(date);
            student.setMail("n19dccn018@student.ptithcm.edu.vn");
            student.setStatus(true);
            student.setPassword(encoder.encode("123"));
//-----------------------
            studentRepository.save(student);
        });
        return true;
    }
    @Transactional(rollbackFor = {Exception.class, Throwable.class})
    public ResponseEntity<?> forgotPass(String mssv) throws Exception{
        Student student= studentRepository.findByUsername(mssv);
        if(student == null){
            return ResponseEntity.badRequest().body("Invalid student ID");

        }
        if(student.getPassword()==null){
            return ResponseEntity.badRequest().body("Account not registered");
        }
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(PASSWORD_LENGTH);
        for (int i = 0; i < PASSWORD_LENGTH; i++) {
            int index = random.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(index));
        }
        String newPassword= encoder.encode(sb.toString());
        student.setPassword(newPassword);
        studentRepository.save(student);
        if(!emailServiceIml.sendSimpleMail(new EmailDetails(student.getMail(),"New password: " + sb.toString(),"FORGOT PASSWORD DORMITORY ACCOUNT"))){
            throw new Exception("Send email error");
        }
        return ResponseEntity.ok(newPassword);
    }
}
