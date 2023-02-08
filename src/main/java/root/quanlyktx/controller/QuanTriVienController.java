package root.quanlyktx.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import root.quanlyktx.entity.SinhVien;
import root.quanlyktx.service.SinhVienService;

import java.util.List;

@RestController
@RequestMapping("/api/QTV")
public class QuanTriVienController {

    @Autowired
    SinhVienService sinhVienService;
    @GetMapping("/TTSV/{MSSV}")
    SinhVien findById(@PathVariable("MSSV") String MSSV){ return sinhVienService.findById(MSSV);}

    @GetMapping("/TTSV")
    List<SinhVien> getALL(){ return sinhVienService.getAll();}
}
