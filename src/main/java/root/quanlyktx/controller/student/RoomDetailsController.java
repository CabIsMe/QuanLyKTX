package root.quanlyktx.controller.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import root.quanlyktx.dto.LoaiKTXDto;
import root.quanlyktx.model.RoomDetails;
import root.quanlyktx.repository.TermRepository;
import root.quanlyktx.service.HopDongKTXService;
import root.quanlyktx.service.LoaiKTXService;
import root.quanlyktx.service.PhongKTXService;

import java.util.List;

@RequestMapping("api/student")
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@PreAuthorize("hasAuthority('student')")
public class RoomDetailsController {
    @Autowired
    private PhongKTXService phongKTXService;
    @Autowired
    private LoaiKTXService loaiKTXService;

    @GetMapping("/room-type")
    public List <LoaiKTXDto> getAllListLoaiKTX(){
        return loaiKTXService.getAll();
    }

    @GetMapping("/room-type/{id}")
    public LoaiKTXDto getOneLoaiKTX(@PathVariable("id") Integer id){
        return loaiKTXService.getSingleLoaiKTX(id);
    }


    @GetMapping("/room-details/{id}")
    public List<RoomDetails> getRoomDetailsListById(@PathVariable("id") Integer idLoaiPhong){
        return phongKTXService.roomDetailsList(idLoaiPhong);
    }


    @GetMapping("/room-details/{idLoaiPhong}/room/{idPhong}")
    public RoomDetails getThongTinPhong(@PathVariable("idLoaiPhong") Integer idLoaiPhong, @PathVariable("idPhong") Integer idPhong){
        return phongKTXService.roomInfo(idLoaiPhong, idPhong);
    }



}
