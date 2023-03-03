package root.quanlyktx.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import root.quanlyktx.entity.GiaNuocTheoThang;
import root.quanlyktx.service.GiaNuocTheoThangSerive;

import java.util.List;

@RestController
@RequestMapping("/api/gianuoctheothang")
public class GiaNuocTheoThangController {

    @Autowired
    GiaNuocTheoThangSerive giaNuocTheoThangSerive;

    @GetMapping("/")
    public List<GiaNuocTheoThang> getAll(){ return giaNuocTheoThangSerive.getAll();}
    @GetMapping("/lastmonth")
    public GiaNuocTheoThang findByLastMonth() { return giaNuocTheoThangSerive.findById();}
}
