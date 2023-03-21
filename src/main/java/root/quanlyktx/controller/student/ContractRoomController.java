package root.quanlyktx.controller.student;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import root.quanlyktx.dto.HopDongKTXDTO;
import root.quanlyktx.model.ViewContractRoom;
import root.quanlyktx.service.HopDongKTXService;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("api/student/contract")
public class ContractRoomController {
    @Autowired
    private HopDongKTXService hopDongKTXService;

    @PostMapping("/")
    public boolean registerDormitory(@RequestBody HopDongKTXDTO hopDongKTXDTO){
        return hopDongKTXService.createHopDong(hopDongKTXDTO);
    }

    @GetMapping("/{mssv}")
    public ResponseEntity<ViewContractRoom> getViewContractRoom(@PathVariable("mssv") String mssv){
        ViewContractRoom viewContractRoom = hopDongKTXService.getViewContractRoom(mssv);
        if(viewContractRoom == null){
            ResponseEntity.badRequest().body("Empty");
        }
        return ResponseEntity.ok(viewContractRoom);
    }
}
