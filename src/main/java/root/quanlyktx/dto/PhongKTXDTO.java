package root.quanlyktx.dto;


import lombok.Data;

import lombok.ToString;



import java.util.List;

@Data
@ToString

public class PhongKTXDTO {
    private Integer id;
    private Integer idLoaiKTX;
    private LoaiKTXDto loaiKTX;
//    private List<HopDongKTX> hopDongKTXList;
//    private String hinhAnh;
}
