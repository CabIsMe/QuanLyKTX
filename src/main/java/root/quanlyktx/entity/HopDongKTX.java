package root.quanlyktx.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.ToString;

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
    @Column(name = "total")
    private Double total;
    @Column(name = "trang_thai")
    private boolean trangThai;
    @Column(name = "id_term")
    private Integer idTerm;

    @ManyToOne
    @JoinColumn(name = "phong_KTX",referencedColumnName = "id",updatable = false,insertable = false)
    private PhongKTX phongKTX;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "MSSV",referencedColumnName = "MSSV", insertable = false, updatable = false)
    private Student student;

    @ManyToOne
    @JoinColumn(name = "id_term", referencedColumnName = "id", insertable = false, updatable = false)
    private Term term;

    public Term getTerm() {
        return term;
    }

    public Integer getIdTerm() {
        return idTerm;
    }

    public void setIdTerm(Integer idTerm) {
        this.idTerm = idTerm;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdPhongKTX() {
        return idPhongKTX;
    }

    public void setIdPhongKTX(Integer idPhongKTX) {
        this.idPhongKTX = idPhongKTX;
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

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public boolean isTrangThai() {
        return trangThai;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }

    public PhongKTX getPhongKTX() {
        return phongKTX;
    }

    public void setPhongKTX(PhongKTX phongKTX) {
        this.phongKTX = phongKTX;
    }

    public Student getStudent() {
        return student;
    }

    public HopDongKTX(){}

    public HopDongKTX(Integer idPhongKTX, String MSSV, Date ngayLamDon,Double total, Integer idTerm) {
        this.idPhongKTX = idPhongKTX;
        this.MSSV = MSSV;
        this.ngayLamDon = ngayLamDon;
        this.total = total;
        this.idTerm=idTerm;
    }

    public HopDongKTX(Integer idPhongKTX, String MSSV, Date ngayLamDon, Integer idTerm) {
        this.idPhongKTX = idPhongKTX;
        this.MSSV = MSSV;
        this.ngayLamDon = ngayLamDon;
        this.idTerm = idTerm;
    }

    public HopDongKTX(String MSSV, Integer idTerm) {
        this.MSSV = MSSV;
        this.idTerm = idTerm;
    }
}
