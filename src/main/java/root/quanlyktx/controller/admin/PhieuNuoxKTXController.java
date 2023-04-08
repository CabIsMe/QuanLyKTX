package root.quanlyktx.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import root.quanlyktx.dto.PhieuNuocKTXDTO;
import root.quanlyktx.entity.LoaiKTX;
import root.quanlyktx.entity.PhieuNuocKTX;
import root.quanlyktx.service.GiaNuocTheoThangSerive;
import root.quanlyktx.service.PhieuNuocKTXService;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/phieunuocktx")
public class PhieuNuoxKTXController {

    @Autowired
    private PhieuNuocKTXService phieuNuocKTXService;
    @Autowired
    private GiaNuocTheoThangSerive giaNuocTheoThangSerive;

    @GetMapping("/")
    public ResponseEntity<?> getPhieuNuocList(@RequestParam(name = "idphongktx") Integer idPhongKTX,@RequestParam(name = "year") Integer year){
        return phieuNuocKTXService.getPhieuNuocList(idPhongKTX,year);
    }

    @PostMapping("/")
        public ResponseEntity<?> updateStatus(@RequestBody PhieuNuocKTXDTO phieuNuocKTXDTO){
        return phieuNuocKTXService.updatePhieuNuocKTX(phieuNuocKTXDTO);
    }


}
