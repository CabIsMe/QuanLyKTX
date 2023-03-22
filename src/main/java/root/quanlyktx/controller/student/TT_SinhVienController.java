package root.quanlyktx.controller.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import root.quanlyktx.model.ThongTinSV;
import root.quanlyktx.service.HopDongKTXService;
import root.quanlyktx.service.StudentService;


import java.sql.Date;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("api/student")
public class TT_SinhVienController {
    @Autowired
    StudentService studentService;
    @Autowired
    HopDongKTXService hopDongKTXService;
    @GetMapping("/info/{MSSV}")
    private ThongTinSV thongTinSV(@PathVariable("MSSV") String MSSV){
        Date date = Date.valueOf("2022-01-01");
        return new ThongTinSV(studentService.getInfo(MSSV),date, Date.valueOf(""),false );
    }

    @GetMapping("/{mssv}")
    private ResponseEntity<?> getInfoStudent(@PathVariable("mssv") String mssv){
        return ResponseEntity.ok(studentService.getStudentByUsername(mssv));
    }

}
