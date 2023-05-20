package root.quanlyktx.controller.admin;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import root.quanlyktx.service.PhieuDienKTXService;
import root.quanlyktx.service.PhieuNuocKTXService;

@RestController
@RequestMapping("/api/manage/invoices")
public class InvoicesController {
    @Autowired
    private PhieuDienKTXService phieuDienKTXService;
    @Autowired
    private PhieuNuocKTXService phieuNuocKTXService;

    @GetMapping("/electric")
    public ResponseEntity<?> getElectricList(@RequestParam(defaultValue = "1") Short numPage,
                                             @RequestParam @Nullable Integer months,
                                             @RequestParam @Nullable Integer years,
                                             @RequestParam(defaultValue = "false") Boolean status){
        return phieuDienKTXService.electricityListManagement(numPage, months, years, status);
    }

    @PatchMapping("/electric/update/{id}")
    public ResponseEntity<?> updateStatusElectric(@PathVariable("id") Integer id){
        return phieuDienKTXService.updateStatus(id);
    }



    @GetMapping("/water")
    public ResponseEntity<?> getPhieuNuocList(@RequestParam(defaultValue = "1") Short numPage,
                                              @RequestParam @Nullable Integer months,
                                              @RequestParam @Nullable Integer years,
                                              @RequestParam(defaultValue = "false") Boolean status){
        return phieuNuocKTXService.waterListManagement(numPage, months, years, status);
    }

    @PatchMapping("/water/update/{id}")
    public ResponseEntity<?> updateStatusWater(@PathVariable("id") Integer id){
        return phieuNuocKTXService.updatePhieuNuocKTX(id);
    }
}
