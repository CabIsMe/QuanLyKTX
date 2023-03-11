package root.quanlyktx.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import root.quanlyktx.entity.HopDongKTX;
import root.quanlyktx.entity.LoaiKTX;
import root.quanlyktx.entity.PhongKTX;
import root.quanlyktx.entity.Student;

import java.util.Date;

@Data
@Getter
@Setter
public class ViewBillRoomDTO {
    private Integer id;
    private String image;
    private Double price;
    private Integer roomType;
    private Integer bedNumber;
    private String fullName;
    private String studentID;
    private String phoneNumber;
    @JsonFormat(pattern = "dd/MM/yyyy",timezone = "Asia/Ho_Chi_Minh")
    private Date dateCreated;
    @JsonFormat(pattern = "dd/MM/yyyy",timezone = "Asia/Ho_Chi_Minh")
    private Date dateStart;
    @JsonFormat(pattern = "dd/MM/yyyy",timezone = "Asia/Ho_Chi_Minh")
    private Date dateEnd;
    private Double total;
    private Boolean status;

//    public ViewBillRoomDTO mapper(Student student, HopDongKTX hopDongKTX, PhongKTX phongKTX, LoaiKTX loaiKTX){
//        ViewBillRoomDTO viewBillRoomDTO = new ViewBillRoomDTO();
//        viewBillRoomDTO.setId(hopDongKTX.getId());
//        viewBillRoomDTO.setImage(loaiKTX.getImage());
//        viewBillRoomDTO.setPrice(loaiKTX.getGiaPhong());
//        viewBillRoomDTO.setRoomType(phongKTX.getLoaiKTX());
//        viewBillRoomDTO.setBedNumber(loaiKTX.getSoGiuong());
//        viewBillRoomDTO.setFullName(student.getHoTen());
//        viewBillRoomDTO.setStudentID(student.getUsername());
//        viewBillRoomDTO.setPhoneNumber(student.getSDT());
//        viewBillRoomDTO.setDateCreated(hopDongKTX.getNgayLamDon());
//        viewBillRoomDTO.setDateStart(hopDongKTX.getNgayKetThuc());
//        viewBillRoomDTO.setDateEnd(hopDongKTX.getNgayHieuLuc());
//        viewBillRoomDTO.setTotal(loaiKTX.getGiaPhong()*5);
//        viewBillRoomDTO.setStatus(hopDongKTX.isTrangThai());
//        return viewBillRoomDTO;
//    }
}
