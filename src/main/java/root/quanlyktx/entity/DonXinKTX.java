package root.quanlyktx.entity;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "don_xin_KTX")
public class DonXinKTX {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "thoi_gian_lam_don")
    private Date thoiGianLamDon;
    @Column(name = "MSSV")
    private String MSSV;
    @Column(name = "phong_KTX")
    private Integer phongKTX;
    @Column(name = "trang_thai")
    private Integer trangThai;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getThoiGianLamDon() {
        return thoiGianLamDon;
    }

    public void setThoiGianLamDon(Date thoiGianLamDon) {
        this.thoiGianLamDon = thoiGianLamDon;
    }

    public String getMSSV() {
        return MSSV;
    }

    public void setMSSV(String MSSV) {
        this.MSSV = MSSV;
    }

    public Integer getPhongKTX() {
        return phongKTX;
    }

    public void setPhongKTX(Integer phongKTX) {
        this.phongKTX = phongKTX;
    }

    public Integer getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(Integer trangThai) {
        this.trangThai = trangThai;
    }

    public DonXinKTX() {
    }
}
