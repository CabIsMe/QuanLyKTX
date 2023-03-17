package root.quanlyktx.controller.student;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import root.quanlyktx.service.HopDongKTXService;

@RestController
@RequestMapping("api/bill")
@CrossOrigin(origins = "*", maxAge = 3600)
public class BillsController {
    @Autowired
    HopDongKTXService hopDongKTXService;
//    @GetMapping("/room/{mssv}")
//    public ViewBillRoom getBillRoom(@PathVariable("mssv") String mssv) {
//        ViewBillRoom hopDongKTX = hopDongKTXService.getBillRoom(mssv);
//        return hopDongKTX;
//    }

//    @GetMapping("/bills/electric/{mssv}")
//    public


}
