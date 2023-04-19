package root.quanlyktx.service;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import root.quanlyktx.dto.AdminDto;
import root.quanlyktx.dto.StudentDto;
import root.quanlyktx.entity.Admin;
import root.quanlyktx.entity.Student;
import root.quanlyktx.repository.AdminRepository;
import root.quanlyktx.repository.StudentRepository;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class AdminService {
    @Autowired
    AdminRepository adminRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    PasswordEncoder encoder;

    public AdminDto getInfo(String username){

//        HandleUserDetail userDetails =
//                (HandleUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Admin admin = adminRepository.findByUsername(username);
            return modelMapper.map(admin, AdminDto.class);

    }
     public List<StudentDto> getAll(){
         List <Admin> studentList = adminRepository.findAll();

         return studentList.stream()
                 .map(user -> modelMapper
                 .map(user, StudentDto.class))
                 .collect(Collectors.toList());
     }

     public AdminDto updateSinhVien(String MSCB, AdminDto adminDto){

         if (adminRepository.existsById(MSCB)){
             try{
                 Admin admin = adminRepository.findById(MSCB).get();
                 admin.setHoTen(adminDto.getHoTen());
                 admin.setGioiTinh(adminDto.isGioiTinh());
                 admin.setNgaySinh(adminDto.getNgaySinh());
                 admin.setCMND(adminDto.getCMND());
                 admin.setSDT(adminDto.getSDT());
                 admin.setMail(adminDto.getMail());
                 adminRepository.save(admin);
                 return modelMapper.map(admin, AdminDto.class);
             }
             catch (Exception e){
                 e.printStackTrace();
             }
         }
         return null;
     }

     public ResponseEntity<?> registerAccountAdmin(Admin admin){
         if (adminRepository.existsById(admin.getUsername())) {
             return ResponseEntity.badRequest().body("Error: Username is already taken!");
         }
         // Create new user's account
         try{
             Admin admin1 = new Admin(admin.getUsername(), encoder.encode(admin.getPassword()), admin.getHoTen());
             // student la 2
             admin1.setRole_id(1);
             admin1.setCMND("12345788");
             admin1.setMail("n19dccn018@student.ptithcm.edu.vn");
             admin1.setSDT("0123456789");
             admin1.setGioiTinh(new Random().nextBoolean());
             adminRepository.save(admin1);
             return ResponseEntity.ok("User registered successfully!");
         }
         catch (Exception e){
             e.printStackTrace();
             return ResponseEntity
                     .badRequest()
                     .body("Error: Can't save User");
         }
     }

     public boolean deleteAdminAccount(String id){
        if(!adminRepository.existsById(id)){
            return false;
        }
        adminRepository.delete(adminRepository.findByUsername(id));
        return true;
     }
}
