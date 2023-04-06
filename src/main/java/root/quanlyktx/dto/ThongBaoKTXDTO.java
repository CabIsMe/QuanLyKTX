package root.quanlyktx.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import java.sql.Date;

@Data
public class ThongBaoKTXDTO {
    private Integer id;
    private String tieuDe;
    private String noiDung;
    private String nguoiTao;
    private String hinhAnh;
    private Date thoiGian;
    @JsonIgnore
    private AdminDto adminDto;
}
