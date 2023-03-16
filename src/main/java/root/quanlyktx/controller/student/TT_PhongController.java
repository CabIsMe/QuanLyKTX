package root.quanlyktx.controller.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import root.quanlyktx.dto.HopDongKTXDTO;
import root.quanlyktx.dto.LoaiKTXDto;
import root.quanlyktx.dto.PhongKTXDTO;
import root.quanlyktx.model.ViewInforRoom;
import root.quanlyktx.model.ThongTinPhong;
import root.quanlyktx.service.HopDongKTXService;
import root.quanlyktx.service.LoaiKTXService;
import root.quanlyktx.service.PhongKTXService;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("api/student")
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class TT_PhongController {
    @Autowired
    PhongKTXService phongKTXService;
    @Autowired
    HopDongKTXService hopDongKTXService;
    @Autowired
    LoaiKTXService loaiKTXService;
    @GetMapping("/loaiktx/{id}")
    private List<ThongTinPhong> thongTinPhongList(@PathVariable("id") Integer idLoaiPhong){
        return hopDongKTXService.thongTinPhongs(idLoaiPhong);
    }
    @GetMapping("/loaiktx/{idLoaiPhong}/phongktx/{idPhong}")
    private ThongTinPhong getThongTinPhong(@PathVariable("idLoaiPhong") Integer idLoaiPhong,@PathVariable("idPhong") Integer idPhong){
        return hopDongKTXService.thongTinPhong(idLoaiPhong, idPhong);
    }

//    @PostMapping("/{mssv}/{idphongktx}/{numbed}")
//    private String registerRoom(@PathVariable("mssv") String mssv,@PathVariable("idphongktx") Integer idPhongKTX,@PathVariable("numbed") Integer numBed){
//        return hopDongKTXService.addBillRoom(mssv,idPhongKTX,numBed);
//    }

//    @PostMapping("/register/")
//    private String registerRoom(@RequestBody HopDongKTXDTO hopDongKTXDTO){
//        return hopDongKTXService.addBillRoom(hopDongKTXDTO);
//    }

//    @GetMapping("/inforRoom/{idphongktx}")
//    private ViewInforRoom getViewInforRoom(@PathVariable("idphongktx") Integer idPhongKTX){
//        return phongKTXService.getViewInforRoom(idPhongKTX);
//    }



}
