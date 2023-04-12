package root.quanlyktx.controller.admin;

import org.apache.http.annotation.Contract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import root.quanlyktx.dto.HopDongKTXDTO;
import root.quanlyktx.entity.HopDongKTX;
import root.quanlyktx.service.HopDongKTXService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/contract")
@CrossOrigin(origins = "*", maxAge = 3600)

public class HopDongKTXController {
    @Autowired
    private HopDongKTXService hopDongKTXService;



    @GetMapping("/")
    public List<HopDongKTXDTO> getAll(){ return hopDongKTXService.getAll();}

    @GetMapping("/list/{numpage}")
    public ResponseEntity<?> getViewContractRoomList(@PathVariable("numpage") Integer numPage,
          @RequestParam(name = "idphongktx") Integer idPhongKTX,
          @RequestParam(name = "idterm") Integer idTerm,
          @RequestParam(name = "status") boolean status){
        return hopDongKTXService.getViewContractRoomList(numPage,idPhongKTX,idTerm,status);
    }

    @PutMapping("/")
    public ResponseEntity<?> updateStatusContract(@RequestParam(name = "idHopDong") Integer idHopDong,@RequestParam(name = "idTerm") Integer idTerm){
        return hopDongKTXService.updateStatusContract(idHopDong,idTerm);
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

    @GetMapping("/searching")
    public List<HopDongKTXDTO> getContracts(
            @RequestParam(required = false) Integer id,
            @RequestParam(required = false) Integer idPhong,
            @RequestParam(required = false) String MSSV
    ) {
        List <HopDongKTXDTO> contracts= hopDongKTXService.getAll();
        return contracts.stream()
                .filter(c -> id == null || c.getId().equals(id))
                .filter(c -> idPhong == null || c.getIdPhongKTX().equals(idPhong))
                .filter(c -> MSSV == null || c.getMSSV().equals(MSSV))
                .collect(Collectors.toList());
    }
}
