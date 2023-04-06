package root.quanlyktx.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import root.quanlyktx.entity.LoaiKTX;
import root.quanlyktx.entity.PhieuNuocKTX;
import root.quanlyktx.service.GiaNuocTheoThangSerive;
import root.quanlyktx.service.PhieuNuocKTXService;

import java.util.List;

@RestController
@RequestMapping("/api/phieunuocktx")
public class PhieuNuoxKTXController {

    @Autowired
    PhieuNuocKTXService phieuNuocKTXService;
    GiaNuocTheoThangSerive giaNuocTheoThangSerive;

    @GetMapping("/")
    public List<PhieuNuocKTX> getAll(){ return phieuNuocKTXService.getAll();}
    @PostMapping("/add")
    public String addPhieuNuocKTX(@RequestBody PhieuNuocKTX phieuNuocKTX){
        return phieuNuocKTXService.addPhieuNuocKTX(phieuNuocKTX);
    }


}
