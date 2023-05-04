package root.quanlyktx.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import root.quanlyktx.dto.ThongBaoKTXDTO;
import root.quanlyktx.service.ThongBaoKTXService;

import java.util.List;

@RestController
//@PreAuthorize("hasAuthority('student')")
@RequestMapping("/api/thongbaoktx")
public class ThongBaoKTXController {
    @Autowired
    ThongBaoKTXService thongBaoKTXService;

    @GetMapping("/")
    List<ThongBaoKTXDTO> getAll(){
        return thongBaoKTXService.getAll();
    }

    @GetMapping("/{id}")
    ThongBaoKTXDTO getSingleLoaiKTX(@PathVariable("id") Integer id){
        return thongBaoKTXService.getSingleThongBaoKTX(id);
    }

    @PostMapping("/add")
    ResponseEntity<?> addThongBaoKTX(@RequestParam("file") MultipartFile file, @ModelAttribute ThongBaoKTXDTO thongBaoKTXDTO){
        return thongBaoKTXService.addThongBaoKTX(file,thongBaoKTXDTO);
    }


    @DeleteMapping("/remove/{id}")
    ResponseEntity<?> deleteThongBaoKTX(@PathVariable("id") Integer id){

        return thongBaoKTXService.deleteThongBaoKTX(id);
    }
    @PutMapping("/update/{id}")
    ResponseEntity<?> updateThongBaoKTX(@PathVariable("id") Integer id,@RequestParam("file") MultipartFile file ,@ModelAttribute ThongBaoKTXDTO thongBaoKTXDTO){
        return thongBaoKTXService.updateThongBaoKTX(id,file ,thongBaoKTXDTO);
    }

}
