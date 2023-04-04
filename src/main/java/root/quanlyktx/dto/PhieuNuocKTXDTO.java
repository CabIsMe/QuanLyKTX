package root.quanlyktx.dto;

import lombok.Data;

@Data
public class PhieuNuocKTXDTO {

    private Integer id;

    private Integer maSoKTX;

    private Integer luongNuocTieuThu;

    private boolean trangThai;

    private GiaNuocTheoThangDTO giaNuocTheoThang;

}
