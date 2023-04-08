package root.quanlyktx.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import root.quanlyktx.dto.LoaiKTXDto;
import root.quanlyktx.entity.LoaiKTX;
import root.quanlyktx.service.LoaiKTXService;

import java.util.List;

@RestController
@RequestMapping("/api/room-type")
@CrossOrigin(origins = "*", maxAge = 3600)
public class LoaiKTXController {
    @Autowired
    LoaiKTXService loaiKTXService;


    
    @GetMapping("/")
    List <LoaiKTXDto> getAll(){
        return loaiKTXService.getAll();
    }

    @GetMapping("/{id}")
    LoaiKTXDto getSingleLoaiKTX(@PathVariable("id") Integer id){
        return loaiKTXService.getSingleLoaiKTX(id);
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
