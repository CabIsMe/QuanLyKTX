package root.quanlyktx.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;
import root.quanlyktx.dto.PhongKTXDTO;
import root.quanlyktx.entity.PhieuNuocKTX;
import root.quanlyktx.entity.PhongKTX;
import root.quanlyktx.service.PhongKTXService;

import java.util.List;

@RestController
@RequestMapping("/api/phongktx")

public class PhongKTXController {

    @Autowired
    PhongKTXService phongKTXService;
    @PostMapping("/add")
    public String addPhongKTX(@RequestBody PhongKTXDTO phongKTXDTO){
        return phongKTXService.addPhongKTX(phongKTXDTO);
    }
    @DeleteMapping("/remove/{id}")
    public String deletePhongKTX(@PathVariable("id") Integer id){
        if(phongKTXService.deletePhongKTX(id)){
            return "remove success";
        }
        return "remove fail";
    }

    @PutMapping("/update/{id}")
    public PhongKTXDTO updatePhongKTX(@PathVariable("id") Integer id, @RequestBody PhongKTXDTO phongKTXDTO){
        return phongKTXService.updatePhongKTX(id, phongKTXDTO);
    }

    @GetMapping("/")
    public List <PhongKTXDTO> getAll() { return phongKTXService.getALL();}

    @GetMapping("/{id}")
    public PhongKTXDTO getById(@PathVariable("id") Integer id){
        return phongKTXService.getById(id);
    }

//    @GetMapping("/{id}/lastmonth")
//    PhongKTX fndByIdLastMonth(@PathVariable("id") Integer id){
//        PhongKTX phongKTX_root =phongKTXService.findById(id);
//        PhieuNuocKTX phieuNuocKTX =
//        phongKTX_root.setPhieuNuocKTXList();
//
//        return phongKTXService.findById(id);
//    }
}
