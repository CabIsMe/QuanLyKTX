package root.quanlyktx.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import root.quanlyktx.entity.LoaiKTX;
import root.quanlyktx.service.LoaiKTXService;

import java.util.List;

@RestController
@RequestMapping("/api/loaiktx")
public class LoaiKTXController {
    @Autowired
    LoaiKTXService loaiKTXService;
    @GetMapping("/")
    List <LoaiKTX> getAll(){
        return loaiKTXService.getAll();
    }

    @PostMapping("/add")
    String addLoaiKTX(@RequestBody LoaiKTX loaiKTX){
        return loaiKTXService.addLoaiKTX(loaiKTX);
    }
    @DeleteMapping("/remove/{id}")
    String deleteLoaiKTX(@PathVariable("id") Integer id){
        if(loaiKTXService.deleteLoaiKTX(id)){
            return "remove success";
        }
        return "remove fail";
    }
    @PutMapping("/update/{id}")
    LoaiKTX updateLoaiKTX(@PathVariable("id") Integer id, @RequestBody LoaiKTX loaiKTX){
        return loaiKTXService.updateLoaiKTX(id, loaiKTX);
    }

}
