package root.quanlyktx.entity;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "don_xin_ra_KTX")
public class DonXinRaKTX {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "thoi_gian_lam_don")
    private Date thoiGianLamDon;
    @Column(name = "MSSV")
    private String MSSV;
    @Column(name = "trang_thai")
    private boolean trangThai;
    @Column(name = "ly_do")
    private String lyDo;
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

    public boolean isTrangThai() {
        return trangThai;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }

    public String getLyDo() {
        return lyDo;
    }

    public void setLyDo(String lyDo) {
        this.lyDo = lyDo;
    }

    public DonXinRaKTX() {
    }
}
