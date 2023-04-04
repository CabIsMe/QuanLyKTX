package root.quanlyktx.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import root.quanlyktx.dto.HopDongKTXDTO;
import root.quanlyktx.service.HopDongKTXService;

import java.util.List;

@RestController
@RequestMapping("/api/hopdong")
@CrossOrigin(origins = "*", maxAge = 3600)

public class HopDongKTXController {
    @Autowired
    HopDongKTXService hopDongKTXService;

    @GetMapping("/")
    public List<HopDongKTXDTO> getAll(){ return hopDongKTXService.getAll();}

    @GetMapping("/list/{numpage}")
    public ResponseEntity<?> getViewContractRoomList(@PathVariable("numpage") Integer numPage,
          @RequestParam(name = "idphongktx") Integer idPhongKTX,
          @RequestParam(name = "idterm") Integer idTerm){
        return hopDongKTXService.getViewContractRoomList(numPage,idPhongKTX,idTerm);
    }

    @PutMapping("/")
    public ResponseEntity<?> updateStatusContract(@RequestParam Integer idHopDong){
        return hopDongKTXService.updateStatusContract(idHopDong);
    }

    @GetMapping("/{id}")
    public HopDongKTXDTO getById(@PathVariable("id") Integer id){
       return hopDongKTXService.getById(id);
    }

    @GetMapping("/phong/{idphongktx}")
    public List<HopDongKTXDTO> getByIdPhongKTX(@PathVariable("idphongktx") Integer idphongktx){
        return hopDongKTXService.getByPhongKTX(idphongktx);
    }
//    @DeleteMapping("/xoahd")
//    public List<HopDongKTXDTO> xoaHopDongChuaDong(){
//        return hopDongKTXService.xoaHopDongChuaDongPhi();
//    }

    @GetMapping("/timkiem")
    public  HopDongKTXDTO search(@RequestParam Integer id){ return  hopDongKTXService.getById(id);}
}
