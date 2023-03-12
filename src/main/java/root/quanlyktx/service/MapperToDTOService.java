package root.quanlyktx.service;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import root.quanlyktx.dto.ViewBillRoomDTO;
import root.quanlyktx.dto.ViewInforRoom;
import root.quanlyktx.entity.HopDongKTX;
import root.quanlyktx.entity.LoaiKTX;
import root.quanlyktx.entity.PhongKTX;
import root.quanlyktx.entity.Student;

@Service
public class MapperToDTOService {

    public ViewBillRoomDTO mapperBillRoom(Student student, HopDongKTX hopDongKTX, PhongKTX phongKTX, LoaiKTX loaiKTX){
        ViewBillRoomDTO viewBillRoomDTO = new ViewBillRoomDTO();
        viewBillRoomDTO.setId(hopDongKTX.getId());
        viewBillRoomDTO.setImage(loaiKTX.getImage());
        viewBillRoomDTO.setPrice(loaiKTX.getGiaPhong());
        viewBillRoomDTO.setRoomType(phongKTX.getLoaiKTX());
        viewBillRoomDTO.setBedNumber(loaiKTX.getSoGiuong());
        viewBillRoomDTO.setFullName(student.getHoTen());
        viewBillRoomDTO.setStudentID(student.getUsername());
        viewBillRoomDTO.setPhoneNumber(student.getSDT());
        viewBillRoomDTO.setDateCreated(hopDongKTX.getNgayLamDon());
        viewBillRoomDTO.setDateStart(hopDongKTX.getNgayKetThuc());
        viewBillRoomDTO.setDateEnd(hopDongKTX.getNgayHieuLuc());
        viewBillRoomDTO.setTotal(loaiKTX.getGiaPhong()*5);
        viewBillRoomDTO.setStatus(hopDongKTX.isTrangThai());
        return viewBillRoomDTO;
    }

    public ViewInforRoom mapperInforRoom(PhongKTX phongKTX,LoaiKTX loaiKTX,Integer numBedEmpty){
        ViewInforRoom viewInforRoom =new ViewInforRoom();
//        viewInforRoom.setImg(loaiKTX.getImage());
        viewInforRoom.setIdPhong(phongKTX.getId());
//        viewInforRoom.setPrice(loaiKTX.getGiaPhong());
//        viewInforRoom.setNumBed(loaiKTX.getSoGiuong());
        viewInforRoom.setNumBedEmpty(numBedEmpty);
        viewInforRoom.setLoaiKTX(loaiKTX);
//        viewInforRoom.setDescription(loaiKTX.getDescription());
        return viewInforRoom;
    }
}
