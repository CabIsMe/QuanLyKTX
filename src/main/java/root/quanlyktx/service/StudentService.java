package root.quanlyktx.service;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import root.quanlyktx.dto.StudentDto;

import root.quanlyktx.entity.Student;
import root.quanlyktx.repository.StudentRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService {
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    private ModelMapper modelMapper;
//    @Autowired
//    BCryptPasswordEncoder bCryptPasswordEncoder;

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
                 Student student_root = studentRepository.findById(MSSV).get();
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
}
