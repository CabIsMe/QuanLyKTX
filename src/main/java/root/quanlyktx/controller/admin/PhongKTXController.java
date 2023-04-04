package root.quanlyktx.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import root.quanlyktx.dto.PhongKTXDTO;
import root.quanlyktx.entity.PhieuNuocKTX;
import root.quanlyktx.entity.PhongKTX;
import root.quanlyktx.service.PhongKTXService;

import java.util.List;

@RestController
@RequestMapping("/api/phongktx")
@CrossOrigin(origins = "*", maxAge = 3600)

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

    @GetMapping("/loaiktx/{id}")
    public ResponseEntity<?> getAllByLoaiPhong(@PathVariable("id") Integer id){
        if(phongKTXService.getAllByLoaiPhong(id).isEmpty()){
            return ResponseEntity.badRequest().body("Empty");
        }
        return ResponseEntity.ok().body(phongKTXService.getAllByLoaiPhong(id));
    }


    @GetMapping("/timkiem")
    public PhongKTXDTO search(@RequestParam Integer id){ return  phongKTXService.getById(id);}
}
