package root.quanlyktx.controller.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import root.quanlyktx.model.ThongTinPhong;
import root.quanlyktx.service.HopDongKTXService;
import root.quanlyktx.service.LoaiKTXService;
import root.quanlyktx.service.PhongKTXService;

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
    @GetMapping("/loaiktx")
    List <LoaiKTXDto> getAllListLoaiKTX(){
        return loaiKTXService.getAll();
    }
    @GetMapping("/loaiktx/{id}")
    private List<ThongTinPhong> thongTinPhongList(@PathVariable("id") Integer idLoaiPhong){
        return hopDongKTXService.thongTinPhongs(idLoaiPhong);
    }
    @GetMapping("/loaiktx/{idLoaiPhong}/phongktx/{idPhong}")
    private ThongTinPhong getThongTinPhong(@PathVariable("idLoaiPhong") Integer idLoaiPhong,@PathVariable("idPhong") Integer idPhong){
        return hopDongKTXService.thongTinPhong(idLoaiPhong, idPhong);
    }



}
