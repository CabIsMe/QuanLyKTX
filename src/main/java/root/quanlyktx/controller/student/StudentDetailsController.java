package root.quanlyktx.controller.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import root.quanlyktx.dto.StudentDto;
import root.quanlyktx.service.HopDongKTXService;
import root.quanlyktx.service.StudentService;


import java.sql.Date;

@RestController
@RequestMapping("api/student")
@PreAuthorize("hasAuthority('student')")

public class StudentDetailsController {
    @Autowired
    StudentService studentService;

    @GetMapping("/info")
    public StudentDto thongTinSV(){
        return studentService.getInfo();
    }

}
