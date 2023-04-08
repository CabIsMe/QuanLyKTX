package root.quanlyktx.controller.student;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import root.quanlyktx.dto.HopDongKTXDTO;
import root.quanlyktx.dto.TermDTO;
import root.quanlyktx.entity.HopDongKTX;
import root.quanlyktx.entity.Term;
import root.quanlyktx.model.InputContract;
import root.quanlyktx.service.HopDongKTXService;
import root.quanlyktx.service.TermService;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("api/student/contract")
public class ContractRoomController {
    @Autowired
    private HopDongKTXService hopDongKTXService;
    @Autowired
    TermService termService;

    @GetMapping("/get-term")
    public TermDTO getTermToRegister(){
        return termService.getTermForReg();
    }

    @PostMapping("/create")
    public boolean registerDormitory(@RequestBody InputContract inputContract){

        return hopDongKTXService.createContract(inputContract);
    }
    @PostMapping("/extend")
    public ResponseEntity<?> extendDorm(@RequestBody InputContract inputContract){
        return hopDongKTXService.contractExtension(inputContract);
    }

    @GetMapping("/{mssv}")
    public ResponseEntity<?> getViewContractRoom(@PathVariable("mssv") String mssv){
        return hopDongKTXService.getViewContractRoom(mssv);
    }
    @PostMapping("/cancel")
    public void cancellation(@RequestBody HopDongKTXDTO hopDongKTXDTO){

        System.out.println(hopDongKTXDTO.toString());

    }
}
