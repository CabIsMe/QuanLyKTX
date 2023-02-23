package root.quanlyktx.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import root.quanlyktx.dto.UserDto;
import root.quanlyktx.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    UserService userService;
    @PutMapping("/update/{MSSV}")
    private UserDto updateSinhVien(@PathVariable("MSSV") String MSSV,@RequestBody UserDto userDto){
       return userService.updateSinhVien(MSSV, userDto);
    }

}
