package root.quanlyktx.dto;


import lombok.Data;

@Data
public class PhieuDienKTXDTO {

    private Integer id;

    private Integer maSoKTX;

    private Integer soDienTieuThu;

    private boolean trangThai;

    private GiaDienTheoThangDTO giaDienTheoThang;

}
