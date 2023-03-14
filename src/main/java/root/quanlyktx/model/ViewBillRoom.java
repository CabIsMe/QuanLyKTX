package root.quanlyktx.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import root.quanlyktx.dto.HopDongKTXDTO;
import root.quanlyktx.dto.LoaiKTXDto;
import root.quanlyktx.entity.HopDongKTX;
import root.quanlyktx.entity.LoaiKTX;
import root.quanlyktx.entity.PhongKTX;
import root.quanlyktx.entity.Student;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ViewBillRoom {
    private HopDongKTXDTO hopDongKTX;
    private LoaiKTXDto loaiKTX;
    private String fullName;
    private String phoneNumber;
    private Double total;
}
