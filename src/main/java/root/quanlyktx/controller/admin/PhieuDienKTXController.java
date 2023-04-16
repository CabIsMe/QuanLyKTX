package root.quanlyktx.controller.admin;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import root.quanlyktx.dto.PhieuDienKTXDTO;
import root.quanlyktx.service.PhieuDienKTXService;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("api/phieudienktx")
public class PhieuDienKTXController {
    @Autowired
    PhieuDienKTXService phieuDienKTXService;

    @GetMapping("/")
    public ResponseEntity<?> getElectricList(@RequestParam(name = "numpage") Integer numPage,
                                             @RequestParam(name = "idTerm") Integer idTerm,
                                             @RequestParam(name = "status") Boolean status){
        return phieuDienKTXService.getElectricList(numPage,idTerm,status);
    }

    @PostMapping("/")
    public ResponseEntity<?> updateStatus(@RequestBody PhieuDienKTXDTO phieuDienKTXDTO){
        return phieuDienKTXService.updateStatus(phieuDienKTXDTO);
    }

//    @PostMapping("/add")
//    public ResponseEntity<?> addPhieuDien() throws ExecutionException, InterruptedException {
//        return phieuDienKTXService.addPhieuDien();
//    }
}
