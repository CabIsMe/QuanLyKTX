package root.quanlyktx.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import root.quanlyktx.dto.LoaiKTXDto;
import root.quanlyktx.entity.LoaiKTX;
import root.quanlyktx.service.LoaiKTXService;

import java.util.List;

@RestController
//@PreAuthorize("hasAuthority('student')")
@RequestMapping("/api/loaiktx")

public class LoaiKTXController {
    @Autowired
    LoaiKTXService loaiKTXService;
    @GetMapping("/")
    List <LoaiKTXDto> getAll(){
        return loaiKTXService.getAll();
    }


    @PostMapping("/add")
    String addLoaiKTX(@RequestBody LoaiKTXDto loaiKTXDto){
        return loaiKTXService.addLoaiKTX(loaiKTXDto);
    }


    @DeleteMapping("/remove/{id}")
    String deleteLoaiKTX(@PathVariable("id") Integer id){
        if(loaiKTXService.deleteLoaiKTX(id)){
            return "remove success";
        }
        return "remove fail";
    }
    @PutMapping("/update/{id}")
    private LoaiKTXDto updateLoaiKTX(@PathVariable("id") Integer id, @RequestBody LoaiKTXDto loaiKTXDto){
        return loaiKTXService.updateLoaiKTX(id, loaiKTXDto);
    }

}
