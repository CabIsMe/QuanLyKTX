package root.quanlyktx.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import root.quanlyktx.entity.PhongKTX;

import java.util.List;

@Data
@ToString
@Getter
@Setter
public class LoaiKTXDto {
    private Integer id;
    private Integer soGiuong;
    private Double giaPhong;
    private String Image;
    private String tenLoai;
    private String description;

}
