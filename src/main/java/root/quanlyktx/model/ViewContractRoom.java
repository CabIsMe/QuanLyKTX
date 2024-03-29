package root.quanlyktx.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import root.quanlyktx.dto.HopDongKTXDTO;
import root.quanlyktx.dto.LoaiKTXDto;
import root.quanlyktx.dto.StudentDto;
import root.quanlyktx.entity.HopDongKTX;
import root.quanlyktx.entity.LoaiKTX;
import root.quanlyktx.entity.PhongKTX;
import root.quanlyktx.entity.Student;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ViewContractRoom {
    private HopDongKTXDTO hopDongKTX;
    private String nameRoomtype;
    private Double price;
    private String fullName;
    private String mail;
    private String phoneNumber;
    @JsonFormat(pattern = "dd/MM/yyyy", timezone = "Asia/Ho_Chi_Minh")
    private Date datePayment;
    @JsonFormat(pattern = "dd/MM/yyyy", timezone = "Asia/Ho_Chi_Minh")
    private Date dateFrom;
    @JsonFormat(pattern = "dd/MM/yyyy", timezone = "Asia/Ho_Chi_Minh")
    private Date dateEnd;
}
