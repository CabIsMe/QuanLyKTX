package root.quanlyktx.controller.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import root.quanlyktx.dto.LoaiKTXDto;
import root.quanlyktx.model.RoomDetails;
import root.quanlyktx.service.HopDongKTXService;
import root.quanlyktx.service.LoaiKTXService;
import root.quanlyktx.service.PhongKTXService;

import java.util.List;

@RequestMapping("api/student")
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class RoomDetailsController {
    @Autowired
    private PhongKTXService phongKTXService;
    @Autowired
    private LoaiKTXService loaiKTXService;
    @GetMapping("/loaiktx")
    List <LoaiKTXDto> getAllListLoaiKTX(){
        return loaiKTXService.getAll();
    }
    @GetMapping("/loaiktx/{id}")
    private List<RoomDetails> roomInfos(@PathVariable("id") Integer idLoaiPhong){
        return phongKTXService.roomInfoList(idLoaiPhong);
    }
    @GetMapping("/loaiktx/{idLoaiPhong}/phongktx/{idPhong}")
    private RoomDetails getThongTinPhong(@PathVariable("idLoaiPhong") Integer idLoaiPhong, @PathVariable("idPhong") Integer idPhong){
        return phongKTXService.roomInfo(idLoaiPhong, idPhong);
    }



}
