package root.quanlyktx.dto;


import lombok.Data;

@Data
public class PhieuDienKTXDTO {

    private Integer id;

    private Integer maSoKTX;

    private Integer soDienTieuThu;

    private boolean trangThai;

    private GiaDienTheoThangDTO giaDienTheoThang;

    private Double total;

    public PhieuDienKTXDTO(Integer id, Integer maSoKTX, Integer soDienTieuThu, boolean trangThai, GiaDienTheoThangDTO giaDienTheoThang, Double total) {
        this.id = id;
        this.maSoKTX = maSoKTX;
        this.soDienTieuThu = soDienTieuThu;
        this.trangThai = trangThai;
        this.giaDienTheoThang = giaDienTheoThang;
        this.total = total;
    }
}
