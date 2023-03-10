package root.quanlyktx.controller.student;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import root.quanlyktx.dto.ViewBillRoomDTO;
import root.quanlyktx.service.HopDongKTXService;

@RestController
@RequestMapping("api/bill")
@CrossOrigin(origins = "*", maxAge = 3600)
public class BillsController {
    @Autowired
    HopDongKTXService hopDongKTXService;
    @GetMapping("/room")
    public ViewBillRoomDTO getBillRoom(String mssv) {
        ViewBillRoomDTO hopDongKTX = hopDongKTXService.getBillRoom(mssv);
        return hopDongKTX;
    }


}
