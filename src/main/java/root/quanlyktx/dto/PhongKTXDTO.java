package root.quanlyktx.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import root.quanlyktx.entity.HopDongKTX;
import root.quanlyktx.entity.LoaiKTX;

import java.util.List;

@Data
@ToString
@Getter
@Setter
public class PhongKTXDTO {

    private Integer id;
    private Integer idLoaiKTX;
    private LoaiKTXDto loaiKTX;
//    private List<HopDongKTX> hopDongKTXList;
//    private String hinhAnh;
}
