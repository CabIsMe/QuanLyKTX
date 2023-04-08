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
@RequestMapping("/api/room")
@CrossOrigin(origins = "*", maxAge = 3600)

public class PhongKTXController {

    @Autowired
    private PhongKTXService phongKTXService;


    @PostMapping("/add")
    public ResponseEntity<?> addPhongKTX(@RequestBody PhongKTXDTO phongKTXDTO){
        return phongKTXService.addPhongKTX(phongKTXDTO);
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity<?> deletePhongKTX(@PathVariable("id") Integer id) {
        return phongKTXService.deletePhongKTX(id);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updatePhongKTX(@PathVariable("id") Integer id, @RequestBody PhongKTXDTO phongKTXDTO){
        return phongKTXService.updatePhongKTX(id, phongKTXDTO);
    }

    @GetMapping("/")
    public List <PhongKTXDTO> getAll() { return phongKTXService.getALL();}

    @GetMapping("/combobox")
    public List<Integer> comboboxPhongHaveStudents(@RequestParam("status") boolean status) {
        return phongKTXService.getAllPhongHaveStudents(status);
    }

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


    @GetMapping("/search")
    public PhongKTXDTO search(@RequestParam Integer id){ return  phongKTXService.getById(id);}
}
