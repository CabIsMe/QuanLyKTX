package root.quanlyktx.controller.student;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import root.quanlyktx.service.PhieuDienKTXService;
import root.quanlyktx.service.PhieuNuocKTXService;
import root.quanlyktx.service.PhongKTXService;

@RestController
@RequestMapping("api/bills")
@CrossOrigin(origins = "*", maxAge = 3600)
@PreAuthorize("hasAuthority('student')")
public class BillsController {

    @Autowired
    PhongKTXService phongKTXService;
    @Autowired
    PhieuNuocKTXService phieuNuocKTXService;
    @Autowired
    PhieuDienKTXService phieuDienKTXService;

    @GetMapping("/water")
    public ResponseEntity<?> getWaterBills() {
//        List<ViewBills> viewBillsList = phieuNuocKTXService.getWaterBills(mssv);
//        if(viewBillsList.isEmpty()) return ResponseEntity.badRequest().body("Empty");
//        else return ResponseEntity.ok(viewBillsList);
        return phieuNuocKTXService.getWaterBills();
    }

    @GetMapping("/electric")
    public ResponseEntity<?> getElectricBills(){
//        List<ViewBills> viewBillsList = phieuDienKTXService.getElectricBills(mssv);
//        if(viewBillsList.isEmpty()) return ResponseEntity.badRequest().body("Empty");
//        else return ResponseEntity.ok(viewBillsList);
        return phieuDienKTXService.getElectricBills();
    }
}
