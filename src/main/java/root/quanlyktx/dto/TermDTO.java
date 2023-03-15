package root.quanlyktx.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TermDTO {
    private Integer id;
    private Date ngayMoDangKy;
    private Date ngayKetThucDangKy;
    private Date ngayKetThuc;
    private Short hanDongPhi;
}
