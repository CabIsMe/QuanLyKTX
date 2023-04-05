package root.quanlyktx.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import root.quanlyktx.service.HopDongKTXService;
import root.quanlyktx.service.PhongKTXService;

@RestController
@RequestMapping("api/thongke")
public class ThongKeController {
    @Autowired
    HopDongKTXService hopDongKTXService;
    @Autowired
    PhongKTXService phongKTXService;

    @GetMapping("/studentInTerm")
    public ResponseEntity<?> getAmountStudentInTerm(){
        return hopDongKTXService.getAmountStudentInTerm();
    }

//    @GetMapping("/totalMoneyIn")
}
