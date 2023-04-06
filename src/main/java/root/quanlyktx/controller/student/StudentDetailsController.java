package root.quanlyktx.controller.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import root.quanlyktx.model.StudentDetails;
import root.quanlyktx.service.HopDongKTXService;
import root.quanlyktx.service.StudentService;


import java.sql.Date;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("api/student")
public class StudentDetailsController {
    @Autowired
    StudentService studentService;

    @GetMapping("/info/{MSSV}")
    public StudentDetails thongTinSV(@PathVariable("MSSV") String MSSV){

        return studentService.getInfo(MSSV);
    }

}
