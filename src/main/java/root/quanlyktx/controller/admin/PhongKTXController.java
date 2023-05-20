package root.quanlyktx.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import root.quanlyktx.dto.PhongKTXDTO;
import root.quanlyktx.entity.PhieuNuocKTX;
import root.quanlyktx.entity.PhongKTX;
import root.quanlyktx.model.CurrentInfoRoom;
import root.quanlyktx.service.PhongKTXService;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/manage/room")
public class PhongKTXController {

    @Autowired
    private PhongKTXService phongKTXService;


    @PostMapping("/add")
    public ResponseEntity<?> addPhongKTX(@RequestBody PhongKTXDTO phongKTXDTO){
        return phongKTXService.addPhongKTX(phongKTXDTO);
    }

    @GetMapping("/remove/{id}")
    public ResponseEntity<?> deletePhongKTX(@PathVariable("id") Integer id) {
        return phongKTXService.deletePhongKTX(id);
    }

    @PatchMapping("/update/{id}/{idTypeRoom}")
    public ResponseEntity<?> updatePhongKTX(@PathVariable("id") Integer id, @PathVariable Integer idTypeRoom){
        return phongKTXService.updatePhongKTX(id, idTypeRoom);
    }

    @GetMapping("/")
    public List <CurrentInfoRoom> getAll(@RequestParam(defaultValue = "true") Boolean status,
                                         @RequestParam(required = false) Boolean gender,
                                         @RequestParam(required = false) Integer idTypeRoom,
                                         @RequestParam(required = false) Integer id,
                                         @RequestParam(defaultValue = "false") Boolean sortByType) {

        List <CurrentInfoRoom> infoRooms=phongKTXService.getALL(status, sortByType);
        return infoRooms.stream()
                .filter(currentInfoRoom -> gender ==null || currentInfoRoom.getPhongKTX().getLoaiKTX().isGioiTinh()==gender)
                .filter(currentInfoRoom -> idTypeRoom ==null || currentInfoRoom.getPhongKTX().getLoaiKTX().getId().equals(idTypeRoom))
                .filter(currentInfoRoom -> id ==null || currentInfoRoom.getPhongKTX().getId().equals(id))
                .collect(Collectors.toList());
    }



    @GetMapping("/combobox")
    public List<PhongKTXDTO> comboboxPhongHaveStudents(@RequestParam( defaultValue = "true") Boolean statusContract) {
        return phongKTXService.getAllPhongHaveStudents(statusContract);
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
