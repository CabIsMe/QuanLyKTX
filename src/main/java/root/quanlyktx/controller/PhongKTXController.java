package root.quanlyktx.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import root.quanlyktx.entity.LoaiKTX;
import root.quanlyktx.entity.PhongKTX;
import root.quanlyktx.service.PhongKTXService;

@RestController
@RequestMapping("/api/phongktx")
public class PhongKTXController {

    @Autowired
    PhongKTXService phongKTXService;
    @PostMapping("/add")
    public String addPhongKTX(@RequestBody PhongKTX phongKTX){
        return phongKTXService.addPhongKTX(phongKTX);
    }
    @DeleteMapping("/remove/{id}")
    public String deletePhongKTX(@PathVariable("id") Integer id){
        if(phongKTXService.deletePhongKTX(id)){
            return "remove success";
        }
        return "remove fail";
    }

    @PutMapping("/update/{id}")
    public PhongKTX updatePhongKTX(@PathVariable("id") Integer id, @RequestBody PhongKTX phongKTX){
        return phongKTXService.updatePhongKTX(id, phongKTX);
    }

}
