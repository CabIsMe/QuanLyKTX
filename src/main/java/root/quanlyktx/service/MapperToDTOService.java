package root.quanlyktx.service;

import org.springframework.stereotype.Service;
import root.quanlyktx.model.ViewBillRoom;
import root.quanlyktx.model.ViewInforRoom;
import root.quanlyktx.entity.HopDongKTX;
import root.quanlyktx.entity.LoaiKTX;
import root.quanlyktx.entity.PhongKTX;
import root.quanlyktx.entity.Student;

@Service
public class MapperToDTOService {

//    public ViewBillRoom mapperBillRoom(Student student, HopDongKTX hopDongKTX, PhongKTX phongKTX, LoaiKTX loaiKTX){
//        ViewBillRoom viewBillRoom = new ViewBillRoom();
//        viewBillRoom.setId(hopDongKTX.getId());
//        viewBillRoom.setImage(loaiKTX.getImage());
//        viewBillRoom.setPrice(loaiKTX.getGiaPhong());
//        viewBillRoom.setRoomType(phongKTX.getLoaiKTX());
//        viewBillRoom.setBedNumber(loaiKTX.getSoGiuong());
//        viewBillRoom.setFullName(student.getHoTen());
//        viewBillRoom.setStudentID(student.getUsername());
//        viewBillRoom.setPhoneNumber(student.getSDT());
//        viewBillRoom.setDateCreated(hopDongKTX.getNgayLamDon());
//        viewBillRoom.setDateStart(hopDongKTX.getNgayKetThuc());
//        viewBillRoom.setDateEnd(hopDongKTX.getNgayHieuLuc());
//        viewBillRoom.setTotal(loaiKTX.getGiaPhong()*5);
//        viewBillRoom.setStatus(hopDongKTX.isTrangThai());
//        return viewBillRoom;
//    }

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
