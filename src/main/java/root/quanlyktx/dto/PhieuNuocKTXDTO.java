package root.quanlyktx.dto;

import lombok.Data;

@Data
public class PhieuNuocKTXDTO {

    private Integer id;

    private Integer maSoKTX;

    private GiaNuocTheoThangDTO giaNuocTheoThang;
//    @Column(name = "id_don_gia")
//    private Integer idDonGia;

    private Integer luongNuocTieuThu;

    private boolean trangThai;


}
