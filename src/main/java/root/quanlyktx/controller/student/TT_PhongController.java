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
//    @GetMapping("/loaiktx/{id}")
//    private List<ThongTinPhong> thongTinPhong(@PathVariable("id") Integer idLoaiPhong){
//        LoaiKTXDto loaiKTXDto=loaiKTXService.getSingleLoaiKTX(idLoaiPhong);
//        List<PhongKTXDTO> phongKTXDTOList= phongKTXService.getAllByLoaiPhong(idLoaiPhong);
//        List<ThongTinPhong> thongTinPhongs= new ArrayList<>();
//
//        for (PhongKTXDTO phongKTXDTO: phongKTXDTOList) {
//
//            thongTinPhongs.add(new ThongTinPhong(phongKTXDTO.getId(),loaiKTXDto.getGiaPhong(),
//                    loaiKTXDto.getSoGiuong()- hopDongKTXService.countByPhongKTX(phongKTXDTO.getId())
//                    ,loaiKTXDto.getImage()));
//
//        }
//        return hopDongKTXService.thongTinPhongs(idLoaiPhong);
//    }

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
