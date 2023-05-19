package root.quanlyktx.service;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import root.quanlyktx.dto.AdminDto;
import root.quanlyktx.dto.StudentDto;
import root.quanlyktx.entity.Admin;
import root.quanlyktx.entity.Role;
import root.quanlyktx.entity.Student;
import root.quanlyktx.repository.AdminRepository;
import root.quanlyktx.repository.RoleRepository;
import root.quanlyktx.repository.StudentRepository;

import java.sql.Date;
import java.util.ArrayList;
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
    private RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    public AdminDto getInfo(String username){

//        HandleUserDetail userDetails =
//                (HandleUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Admin admin = adminRepository.findByUsername(username);
            return modelMapper.map(admin, AdminDto.class);

    }
     public List<AdminDto> getAll(){
         List <Admin> admins = adminRepository.findAll();
         return admins.stream().map(admin -> modelMapper.map(admin, AdminDto.class))
                 .collect(Collectors.toList());
     }



     public ResponseEntity<?> registerAccountAdmin(Admin admin){
         if (adminRepository.existsById(admin.getUsername())) {
             return ResponseEntity.badRequest().body("Error: Username is already taken!");
         }
         // Create new user's account
         try{
             Admin admin1 = new Admin(admin.getUsername(), admin.getHoTen(), admin.getMail(),encoder.encode(admin.getPassword()), admin.getRole_id());

             admin1.setNgaySinh(Date.valueOf(("2001-03-31")));
             admin1.setCMND("12345788");
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
        Admin admin= adminRepository.findById(id).orElseThrow(()-> new RuntimeException("ID not found!"));
        adminRepository.delete(admin);
        return true;
     }
}
