package root.quanlyktx.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import root.quanlyktx.dto.LoaiKTXDto;
import root.quanlyktx.entity.LoaiKTX;
import root.quanlyktx.service.LoaiKTXService;

import java.util.List;

@RestController
@RequestMapping("/api/manage/room-type")
//@PreAuthorize("hasAuthority('student')")
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
    ResponseEntity<?> addLoaiKTX(@RequestParam("file") MultipartFile file, @ModelAttribute LoaiKTXDto loaiKTXDto){
        return loaiKTXService.addLoaiKTX(file,loaiKTXDto);
    }


    @DeleteMapping("/remove/{id}")
    ResponseEntity<?> deleteLoaiKTX(@PathVariable("id") Integer id){

        return loaiKTXService.deleteLoaiKTX(id);
    }
    @PutMapping("/update/{id}")
    ResponseEntity<?> updateLoaiKTX(@PathVariable("id") Integer id, @RequestParam("file") MultipartFile file
            , @ModelAttribute LoaiKTXDto loaiKTXDto){
        return loaiKTXService.updateLoaiKTX(id, file, loaiKTXDto);
    }

}
