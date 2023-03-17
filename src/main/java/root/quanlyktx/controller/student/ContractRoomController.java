package root.quanlyktx.controller.student;

import org.springframework.beans.factory.annotation.Autowired;

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
    public ViewContractRoom getViewContractRoom(@PathVariable("mssv") String mssv){
        return hopDongKTXService.getViewContractRoom(mssv);
    }
}
