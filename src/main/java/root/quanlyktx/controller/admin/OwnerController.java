package root.quanlyktx.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import root.quanlyktx.entity.Admin;
import root.quanlyktx.service.AdminService;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("api/owner")
//@PreAuthorize("hasAuthority('owner')")
public class OwnerController {
    @Autowired
    AdminService adminService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody Admin admin) {
        return adminService.registerAccountAdmin(admin);

    }
    @DeleteMapping("delete/{id}")
    public boolean removeAdmin(@PathVariable("id") String id){
        return adminService.deleteAdminAccount(id);
    }
}
