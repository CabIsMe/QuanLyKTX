package root.quanlyktx.entity;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "hop_dong_KTX")
public class HopDongKTX {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "phong_KTX")
    private Integer phongKTX;
    @Column(name = "MSSV")
    private String MSSV;
    @Column(name = "ngay_lam_don")
    private Date ngayLamDon;
    @Column(name = "ngay_bat_dau")
    private Date ngayBatDau;
    @Column(name = "ngay_ket_thuc")
    private Date ngayKetThuc;
    @Column(name = "trang_thai")
    private boolean trangThai;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPhongKTX() {
        return phongKTX;
    }

    public void setPhongKTX(Integer phongKTX) {
        this.phongKTX = phongKTX;
    }

    public String getMSSV() {
        return MSSV;
    }

    public void setMSSV(String MSSV) {
        this.MSSV = MSSV;
    }

    public Date getNgayLamDon() {
        return ngayLamDon;
    }

    public void setNgayLamDon(Date ngayLamDon) {
        this.ngayLamDon = ngayLamDon;
    }

    public Date getNgayBatDau() {
        return ngayBatDau;
    }

    public void setNgayBatDau(Date ngayBatDau) {
        this.ngayBatDau = ngayBatDau;
    }

    public Date getNgayKetThuc() {
        return ngayKetThuc;
    }

    public void setNgayKetThuc(Date ngayKetThuc) {
        this.ngayKetThuc = ngayKetThuc;
    }

    public boolean isTrangThai() {
        return trangThai;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }

    public HopDongKTX() {
    }
}
