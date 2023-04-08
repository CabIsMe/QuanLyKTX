package root.quanlyktx.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import root.quanlyktx.dto.StudentDto;

import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentDetails {
    private StudentDto personalInfo;

    @JsonFormat(pattern = "dd/MM/yyyy", timezone = "Asia/Ho_Chi_Minh")
    private Date ngayLamDon;
    @JsonFormat(pattern = "dd/MM/yyyy", timezone = "Asia/Ho_Chi_Minh")
    private Date ngayBatDau;
    @JsonFormat(pattern = "dd/MM/yyyy", timezone = "Asia/Ho_Chi_Minh")
    private Date ngayKetThuc;
    private Integer idPhong;
    private Boolean trangThaiThanhToan;

}
