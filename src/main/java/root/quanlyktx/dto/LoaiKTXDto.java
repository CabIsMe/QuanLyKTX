package root.quanlyktx.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Data
@ToString
@Getter
@Setter
public class LoaiKTXDto {
    private Integer soGiuong;
    private Double giaPhong;
    private String Image;
    private String tenLoai;
}
