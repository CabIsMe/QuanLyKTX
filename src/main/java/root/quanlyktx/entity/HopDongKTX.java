package root.quanlyktx.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "hop_dong_KTX")
@ToString
public class HopDongKTX {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "phong_KTX")
    private Integer phongKTX;
    @Column(name = "MSSV")
    private String MSSV;
    @JsonFormat(pattern = "dd/MM/yyyy", locale = "vi_VN")
    @Column(name = "ngay_lam_don")
    private Date ngayLamDon;
    @JsonFormat(pattern = "dd/MM/yyyy", locale = "vi_VN")
    @Column(name = "ngay_hieu_luc")
    private Date ngayHieuLuc;
    @JsonFormat(pattern = "dd/MM/yyyy", locale = "vi_VN")
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

    public Date getNgayHieuLuc() {
        return ngayHieuLuc;
    }

    public void setNgayHieuLuc(Date ngayHieuLuc) {
        this.ngayHieuLuc = ngayHieuLuc;
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
