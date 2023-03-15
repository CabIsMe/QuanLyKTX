package root.quanlyktx.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "term")
public class Term {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Integer id;
    @Column(name = "ngay_mo_dang_ky")
    private Date ngayMoDangKy;
    @Column(name = "ngay_ket_thuc_dang_ky")
    private Date ngayKetThucDangKy;
    @Column(name = "ngay_ket_thuc")
    private Date ngayKetThuc;
    @Column(name = "han_dong_phi")
    private Short hanDongPhi;

    public Term() {
    }

    public Term(Date ngayMoDangKy, Date ngayKetThucDangKy, Date ngayKetThuc, Short hanDongPhi) {
        this.ngayMoDangKy = ngayMoDangKy;
        this.ngayKetThucDangKy = ngayKetThucDangKy;
        this.ngayKetThuc = ngayKetThuc;
        this.hanDongPhi = hanDongPhi;
    }

    public Integer getId() {
        return id;
    }

    public Date getNgayMoDangKy() {
        return ngayMoDangKy;
    }

    public void setNgayMoDangKy(Date ngayMoDangKy) {
        this.ngayMoDangKy = ngayMoDangKy;
    }

    public Date getNgayKetThucDangKy() {
        return ngayKetThucDangKy;
    }

    public void setNgayKetThucDangKy(Date ngayKetThucDangKy) {
        this.ngayKetThucDangKy = ngayKetThucDangKy;
    }

    public Date getNgayKetThuc() {
        return ngayKetThuc;
    }

    public void setNgayKetThuc(Date ngayKetThuc) {
        this.ngayKetThuc = ngayKetThuc;
    }

    public Short getHanDongPhi() {
        return hanDongPhi;
    }

    public void setHanDongPhi(Short hanDongPhi) {
        this.hanDongPhi = hanDongPhi;
    }
}
