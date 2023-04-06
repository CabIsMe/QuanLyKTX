package root.quanlyktx.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "thong_bao_KTX")
public class ThongBaoKTX {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "tieu_de")
    private String tieuDe;
    @Column(name = "noi_dung")
    private String noiDung;
    @Column(name = "nguoi_tao")
    private String nguoiTao;
    @Column(name = "hinh_anh")
    private String hinhAnh;
    @Column(name = "thoi_gian")
    @JsonFormat(pattern = "dd/MM/yyyy", timezone = "Asia/Ho_Chi_Minh")
    private Date thoiGian;
    @JsonIgnore
    @ManyToOne()
    @JoinColumn(name = "nguoi_tao", referencedColumnName = "MSCB", insertable = false, updatable = false)
    private Admin admin;

    public ThongBaoKTX() {
    }

    public ThongBaoKTX(Integer id, String tieuDe, String noiDung, String nguoiTao, String hinhAnh, Date thoiGian) {
        this.id = id;
        this.tieuDe = tieuDe;
        this.noiDung = noiDung;
        this.nguoiTao = nguoiTao;
        this.hinhAnh = hinhAnh;
        this.thoiGian = thoiGian;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTieuDe() {
        return tieuDe;
    }

    public void setTieuDe(String tieuDe) {
        this.tieuDe = tieuDe;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public String getNguoiTao() {
        return nguoiTao;
    }

    public void setNguoiTao(String nguoiTao) {
        this.nguoiTao = nguoiTao;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public Date getThoiGian() {
        return thoiGian;
    }

    public void setThoiGian(Date thoiGian) {
        this.thoiGian = thoiGian;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }
}
