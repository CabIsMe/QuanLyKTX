package root.quanlyktx.dto;

import lombok.Data;

@Data
public class PhieuNuocKTXDTO {

    private Integer id;

    private Integer maSoKTX;

    private Integer luongNuocTieuThu;

    private boolean trangThai;

    private GiaNuocTheoThangDTO giaNuocTheoThang;

    private Double total;

    public PhieuNuocKTXDTO(Integer id, Integer maSoKTX, Integer luongNuocTieuThu, boolean trangThai, GiaNuocTheoThangDTO giaNuocTheoThang, Double total) {
        this.id = id;
        this.maSoKTX = maSoKTX;
        this.luongNuocTieuThu = luongNuocTieuThu;
        this.trangThai = trangThai;
        this.giaNuocTheoThang = giaNuocTheoThang;
        this.total = total;
    }

}
