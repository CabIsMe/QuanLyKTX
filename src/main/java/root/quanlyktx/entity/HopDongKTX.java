package root.quanlyktx.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.parameters.P;

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
    private Integer idPhongKTX;
    @Column(name = "MSSV")
    private String MSSV;
    @Column(name = "ngay_lam_don")
    @JsonFormat(pattern = "dd/MM/yyyy", timezone = "Asia/Ho_Chi_Minh")
    private Date ngayLamDon;
    @Column(name = "ngay_hieu_luc")
    @JsonFormat(pattern = "dd/MM/yyyy", timezone = "Asia/Ho_Chi_Minh")
    private Date ngayHieuLuc;
    @JsonFormat(pattern = "dd/MM/yyyy", timezone = "Asia/Ho_Chi_Minh")
    @Column(name = "ngay_ket_thuc")
    private Date ngayKetThuc;
    @Column(name = "trang_thai")
    private boolean trangThai;

    @ManyToOne
    @JoinColumn(name = "phong_KTX",referencedColumnName = "id",updatable = false,insertable = false)
    private PhongKTX phongKTX;

    public Integer getIdPhongKTX() {
        return idPhongKTX;
    }

    public void setIdPhongKTX(Integer idPhongKTX) {
        this.idPhongKTX = idPhongKTX;
    }

    public PhongKTX getPhongKTX() {
        return phongKTX;
    }

    public void setPhongKTX(PhongKTX phongKTX) {
        this.phongKTX = phongKTX;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public HopDongKTX(Integer idPhongKTX, String MSSV, Date ngayLamDon, Date ngayHieuLuc, Date ngayKetThuc, boolean trangThai) {
        this.idPhongKTX = idPhongKTX;
        this.MSSV = MSSV;
        this.ngayLamDon = ngayLamDon;
        this.ngayHieuLuc = ngayHieuLuc;
        this.ngayKetThuc = ngayKetThuc;
        this.trangThai = trangThai;
    }
}
