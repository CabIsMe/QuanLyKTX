package root.quanlyktx.model;

import lombok.*;
import root.quanlyktx.dto.StudentDto;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ThongTinSV {
    private StudentDto studentDto;
    private Date ngayBatDau;
    private Date ngayKetThuc;
    private Boolean trangThaiThanhToan;

}
