package root.quanlyktx.service;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import root.quanlyktx.dto.AdminDto;
import root.quanlyktx.dto.StudentDto;
import root.quanlyktx.entity.Admin;
import root.quanlyktx.entity.Student;
import root.quanlyktx.repository.AdminRepository;
import root.quanlyktx.repository.StudentRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminService {
    @Autowired
    AdminRepository adminRepository;
    @Autowired
    private ModelMapper modelMapper;
//    @Autowired
//    BCryptPasswordEncoder bCryptPasswordEncoder;

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
}
