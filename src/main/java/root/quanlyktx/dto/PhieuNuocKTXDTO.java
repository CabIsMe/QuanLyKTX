package root.quanlyktx.dto;

import lombok.Data;

@Data
public class PhieuNuocKTXDTO {

    private Integer id;

    private Integer maSoKTX;
//    @Column(name = "id_don_gia")
//    private Integer idDonGia;

    private Integer luongNuocTieuThu;

    private boolean trangThai;

    private GiaNuocTheoThangDTO giaNuocTheoThang;

}
