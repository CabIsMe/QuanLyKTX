package root.quanlyktx.controller.student;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import root.quanlyktx.model.ViewBills;
import root.quanlyktx.service.HopDongKTXService;
import root.quanlyktx.service.PhieuDienKTXService;
import root.quanlyktx.service.PhieuNuocKTXService;
import root.quanlyktx.service.PhongKTXService;

import java.util.List;

@RestController
@RequestMapping("api/bills")
@CrossOrigin(origins = "*", maxAge = 3600)
public class BillsController {

    @Autowired
    PhongKTXService phongKTXService;
    @Autowired
    PhieuNuocKTXService phieuNuocKTXService;
    @Autowired
    PhieuDienKTXService phieuDienKTXService;

    @GetMapping("/water/{mssv}")
    public ResponseEntity<?> getWaterBills(@PathVariable("mssv") String mssv){
//        List<ViewBills> viewBillsList = phieuNuocKTXService.getWaterBills(mssv);
//        if(viewBillsList.isEmpty()) return ResponseEntity.badRequest().body("Empty");
//        else return ResponseEntity.ok(viewBillsList);
        return phieuNuocKTXService.getWaterBills(mssv);
    }

    @GetMapping("/electric/{mssv}")
    public ResponseEntity<?> getElectricBills(@PathVariable("mssv") String mssv){
//        List<ViewBills> viewBillsList = phieuDienKTXService.getElectricBills(mssv);
//        if(viewBillsList.isEmpty()) return ResponseEntity.badRequest().body("Empty");
//        else return ResponseEntity.ok(viewBillsList);
        return phieuDienKTXService.getElectricBills(mssv);
    }
}
