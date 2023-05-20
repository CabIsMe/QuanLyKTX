package root.quanlyktx.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import root.quanlyktx.dto.AdminDto;
import root.quanlyktx.entity.Admin;
import root.quanlyktx.entity.Role;
import root.quanlyktx.repository.RoleRepository;
import root.quanlyktx.service.AdminService;

import java.util.List;

@RestController
@RequestMapping("api/owner")
public class OwnerController {
    @Autowired
    AdminService adminService;
    @Autowired
    RoleRepository roleRepository;

    @GetMapping("/admins")
    public List<AdminDto> getAllAdmins(){
        return adminService.getAll();
    }
    @GetMapping("/roles")
    public List<Role> getAllRoles(){
        return roleRepository.findAll();
    }

    @PostMapping("/register-account")
    public ResponseEntity<?> registerUser(@RequestBody Admin admin) {
        return adminService.registerAccountAdmin(admin);

    }
    @DeleteMapping("delete-account/{id}")
    public boolean removeAdmin(@PathVariable("id") String id){
        return adminService.deleteAdminAccount(id);
    }
}
