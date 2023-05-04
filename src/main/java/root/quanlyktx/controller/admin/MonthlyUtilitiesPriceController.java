package root.quanlyktx.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import root.quanlyktx.entity.GiaDienTheoThang;
import root.quanlyktx.entity.GiaNuocTheoThang;
import root.quanlyktx.service.GiaDienTheoThangService;
import root.quanlyktx.service.GiaNuocTheoThangSerive;

import java.util.List;

@RestController
@RequestMapping("/api/manage/price")
public class MonthlyUtilitiesPriceController {
    @Autowired
    private GiaNuocTheoThangSerive giaNuocTheoThangSerive;
    @Autowired
    private GiaDienTheoThangService giaDienTheoThangService;

    @GetMapping("/water")
    public List<GiaNuocTheoThang> getAllWaterPrice(){ return giaNuocTheoThangSerive.getAllWaterPrice();}
//    @GetMapping("/water")
//    public List<GiaDie>
    @GetMapping("/electric")
    public List<GiaDienTheoThang> getAllElectricPrice(){
        return giaDienTheoThangService.getAllElectricPrice();
    }
}
