package root.quanlyktx.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import root.quanlyktx.entity.PhongKTX;

import java.util.List;

@Data
@ToString
public class LoaiKTXDto {
    private Integer id;
    private Integer soGiuong;
    private Double giaPhong;
    private String image;
    private String tenLoai;
    private boolean gioiTinh;
    private String description;
    @JsonIgnore
    private List<PhongKTXDTO> phongKTXList;
}
