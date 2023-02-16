package root.quanlyktx.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import root.quanlyktx.entity.LoaiKTX;
import root.quanlyktx.entity.PhongKTX;
import root.quanlyktx.repository.PhongKTXRepository;

import java.util.List;

@RestController
@RequestMapping("/api/phongktx")

public class PhongKTXController {
    @Autowired
    PhongKTXRepository phongKTXRepository;

    @GetMapping("/")
    List<PhongKTX> getAll(){
        return phongKTXRepository.findAll();
    }
}
