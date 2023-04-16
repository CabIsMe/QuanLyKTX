package root.quanlyktx.entity;

import javax.persistence.*;

@Entity
@Table(name = "phieu_nuoc_KTX")
public class PhieuNuocKTX {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "ma_so_KTX")
    private Integer maSoKTX;
    @ManyToOne
    @JoinColumn(name = "id_don_gia")
    private GiaNuocTheoThang giaNuocTheoThang;
    @Column(name = "luong_nuoc_tieu_thu")
    private Integer luongNuocTieuThu;
    @Column(name ="trang_thai")
    private boolean trangThai;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMaSoKTX() {
        return maSoKTX;
    }

    public void setMaSoKTX(Integer maSoKTX) {
        this.maSoKTX = maSoKTX;
    }

    public GiaNuocTheoThang getGiaNuocTheoThang() {
        return giaNuocTheoThang;
    }

    public void setGiaNuocTheoThang(GiaNuocTheoThang giaNuocTheoThang) {
        this.giaNuocTheoThang = giaNuocTheoThang;
    }


//    public Integer getIdDonGia() {
//        return idDonGia;
//    }
//
//    public void setIdDonGia(Integer idDonGia) {
//        this.idDonGia = idDonGia;
//    }

    public Integer getLuongNuocTieuThu() {
        return luongNuocTieuThu;
    }

    public void setLuongNuocTieuThu(Integer luongNuocTieuThu) {
        this.luongNuocTieuThu = luongNuocTieuThu;
    }

    public boolean isTrangThai() {
        return trangThai;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }

    public PhieuNuocKTX(Integer maSoKTX, GiaNuocTheoThang giaNuocTheoThang, Integer luongNuocTieuThu, boolean trangThai) {
        this.maSoKTX = maSoKTX;
        this.giaNuocTheoThang = giaNuocTheoThang;
        this.luongNuocTieuThu = luongNuocTieuThu;
        this.trangThai = trangThai;
    }

    public PhieuNuocKTX() {
    }
}
