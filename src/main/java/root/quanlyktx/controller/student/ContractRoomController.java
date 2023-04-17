package root.quanlyktx.controller.student;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import root.quanlyktx.dto.HopDongKTXDTO;
import root.quanlyktx.dto.TermDTO;
import root.quanlyktx.entity.HopDongKTX;
import root.quanlyktx.entity.Term;
import root.quanlyktx.model.ViewContractRoom;
import root.quanlyktx.service.HopDongKTXService;
import root.quanlyktx.service.TermService;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("api/student/contract")
@PreAuthorize("hasAuthority('student')")
public class ContractRoomController {
    @Autowired
    private HopDongKTXService hopDongKTXService;
    @Autowired
    TermService termService;

    @GetMapping("/check-registration")
    public boolean checkTimeAllow(){
        return hopDongKTXService.checkConditionToRegistration();
    }

    @GetMapping("/view-create/{idPhong}")
    public ViewContractRoom getViewCreateContract(@PathVariable("idPhong") Integer idPhong){
        return hopDongKTXService.getViewBeforeCreateContract(idPhong);
    }

    @GetMapping("/create/{idPhong}")
    public boolean registerDormitory(@PathVariable("idPhong") Integer idPhong){

        return hopDongKTXService.createContract(idPhong);
    }
    @GetMapping("/extend")
    public ResponseEntity<?> extendDorm(){
        return hopDongKTXService.contractExtension();
    }

    @GetMapping("/")
    public ResponseEntity<?> getViewContractRoomCreated(){
        return hopDongKTXService.getViewContractRoom();
    }
    @GetMapping("/cancel")
    public ResponseEntity<?> cancellation(){
        return hopDongKTXService.cancelContract();
    }
}
