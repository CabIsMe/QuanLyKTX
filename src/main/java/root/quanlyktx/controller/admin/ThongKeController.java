package root.quanlyktx.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import root.quanlyktx.service.HopDongKTXService;
import root.quanlyktx.service.PhongKTXService;
import root.quanlyktx.service.ThongKeService;

@RestController
@RequestMapping("api/thongke")
public class ThongKeController {
    @Autowired
    ThongKeService thongKeService;
    @Autowired
    private PhongKTXService phongKTXService;

    @GetMapping("/studentInTerm")
    public ResponseEntity<?> getAmountStudentInTerm(@RequestParam(name = "idTerm") Integer idTerm){
        return thongKeService.getAmountStudentInTerm(idTerm);
    }

//    @GetMapping("/totalMoneyIn")
}
