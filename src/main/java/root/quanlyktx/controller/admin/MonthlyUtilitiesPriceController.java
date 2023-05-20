package root.quanlyktx.controller.admin;

import com.google.firebase.database.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import root.quanlyktx.entity.GiaDienTheoThang;
import root.quanlyktx.entity.GiaNuocTheoThang;
import root.quanlyktx.service.GiaDienTheoThangService;
import root.quanlyktx.service.GiaNuocTheoThangSerive;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/manage/price")

public class MonthlyUtilitiesPriceController {
    @Autowired
    private GiaNuocTheoThangSerive giaNuocTheoThangSerive;
    @Autowired
    private GiaDienTheoThangService giaDienTheoThangService;

    @GetMapping("/water")
    public List<GiaNuocTheoThang> getAllWaterPrice(@RequestParam(required = false) Integer year){
        List<GiaNuocTheoThang> giaNuocTheoThangs= giaNuocTheoThangSerive.getAllWaterPrice();
        return giaNuocTheoThangs.stream()
                .filter(c-> year==null || c.getNam().equals(year))
                .collect(Collectors.toList());
    }
//    @GetMapping("/water")
//    public List<GiaDie>
    @GetMapping("/electric")
    public List<GiaDienTheoThang> getAllElectricPrice(@RequestParam(required = false) Integer year){
        List<GiaDienTheoThang> giaDienTheoThangs=giaDienTheoThangService.getAllElectricPrice();
        return giaDienTheoThangs.stream()
                .filter(c-> year==null || c.getNam().equals(year))
                .collect(Collectors.toList());
    }

    @PatchMapping("/electric/update/{id}")
    public ResponseEntity<?> updateCostOfElectricity(@PathVariable Integer id,@RequestParam(required = false) Double cost){
        if(giaDienTheoThangService.editElectricPrice(id, cost)){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.badRequest().body("Cannot edit this data");
    }
    @PatchMapping("/water/update/{id}")
    public ResponseEntity<?> updateCostOfWater(@PathVariable Integer id, @RequestParam(required = false) Double cost){
        if(giaNuocTheoThangSerive.editWaterPrice(id, cost)){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.badRequest().body("Cannot edit this data");
    }
}
