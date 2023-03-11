package root.quanlyktx.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import root.quanlyktx.entity.HopDongKTX;
import root.quanlyktx.entity.Role;
import root.quanlyktx.entity.Student;
import root.quanlyktx.repository.RoleRepository;
import root.quanlyktx.repository.StudentRepository;
import root.quanlyktx.service.HopDongKTXService;
import root.quanlyktx.service.StudentService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class TestController {
    @Autowired
    StudentService studentService;

    @Autowired
    RoleRepository roleRepository;
    @Autowired
    HopDongKTXService hopDongKTXService;

    @Autowired
    StudentRepository studentRepository;
    @PutMapping("/xoastatus/{mssv}")
    public Student getXoaStt(@PathVariable String mssv){
        Student student=studentRepository.findByUsername(mssv);
        student.setStatus(false);
        studentRepository.save(student);
        return student;

    }
    @PutMapping("/xoamatkhau/{mssv}")
    public Student getXoaPass(@PathVariable String mssv){
        Student student=studentRepository.findByUsername(mssv);
        student.setPassword(null);
        studentRepository.save(student);
        return student;

    }
    @GetMapping("/all")
    public HopDongKTX gettttt(String mssv){
        List <HopDongKTX> hopDongKTXList= hopDongKTXService.getHopDongTemp("n19dccn018");
        List<Date> dates= new ArrayList<>();
        for(HopDongKTX hopDongKTX: hopDongKTXList){
            dates.add(hopDongKTX.getNgayLamDon());
        }
        System.out.println(Collections.max(dates));
        for(HopDongKTX hopDongKTX: hopDongKTXList){
            if(hopDongKTX.getNgayLamDon() ==Collections.max(dates)){
                return hopDongKTX;
            }
        }
            return null;
    }
//    public List<UserDto> getAllUser() {
//        return userService.getAll();

//    }
    public List<Role> getAllRole(){
        return roleRepository.findAll();
    }

    @GetMapping("/student")
//    @PreAuthorize("hasRole('student')")
    @PreAuthorize("hasAuthority('student')")
    public String userAccess() {
        return "User Content.";
    }
    @GetMapping("/admin")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String adminAccess() {
        return "Admin Board.";
    }


}
