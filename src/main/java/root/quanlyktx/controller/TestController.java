package root.quanlyktx.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import root.quanlyktx.entity.Role;
import root.quanlyktx.repository.RoleRepository;
import root.quanlyktx.service.StudentService;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class TestController {
    @Autowired
    StudentService studentService;

    @Autowired
    RoleRepository roleRepository;
    @GetMapping("/all")
//    public List<UserDto> getAllUser() {
//        return userService.getAll();
//
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
