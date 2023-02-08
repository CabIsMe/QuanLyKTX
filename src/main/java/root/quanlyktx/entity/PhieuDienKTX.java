package root.quanlyktx.entity;

import javax.persistence.*;

@Entity
@Table(name = "phieu_dien_KTX")
public class PhieuDienKTX {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "ma_so_KTX")
    private Integer maSoKTX;
    @Column(name = "id_don_gia")
    private Integer idDonGia;
    @Column(name = "so_dien_tieu_thu")
    private Integer soDienTieuThu;
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

    public Integer getIdDonGia() {
        return idDonGia;
    }

    public void setIdDonGia(Integer idDonGia) {
        this.idDonGia = idDonGia;
    }

    public Integer getSoDienTieuThu() {
        return soDienTieuThu;
    }

    public void setSoDienTieuThu(Integer soDienTieuThu) {
        this.soDienTieuThu = soDienTieuThu;
    }

    public boolean isTrangThai() {
        return trangThai;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }

    public PhieuDienKTX() {
    }
}
