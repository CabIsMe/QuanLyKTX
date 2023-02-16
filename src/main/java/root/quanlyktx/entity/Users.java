package root.quanlyktx.entity;


import java.sql.Date;
import java.util.List;
import javax.persistence.*;

@Entity
@Table(name ="sinh_vien")
public class Users {

    @Id
    @Column(name = "MSSV")
    private String MSSV;
    @Column(name = "ho_ten")
    private String ho_ten;
    @Column(name = "gioi_tinh")
    private boolean gioi_tinh;
    @Column(name = "ngay_thang_nam_sinh")
    private Date ngay_thang_nam_sinh;
    @Column(name = "CMND")
    private String CMND;
    @Column(name = "SDT")
    private String SDT;
    @Column(name = "mail")
    private String mail;
    @Column(name = "password")
    private String password;
    @Column(name = "phan_quyen")
    private Integer phanQuyen;

    @OneToMany(mappedBy = "MSSV")
    private List<HopDongKTX> hopDongKTXList;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getPhanQuyen() {
        return phanQuyen;
    }

    public void setPhanQuyen(Integer phanQuyen) {
        this.phanQuyen = phanQuyen;
    }

    public String getMSSV() {
        return MSSV;
    }

    public void setMSSV(String MSSV) {
        this.MSSV = MSSV;
    }

    public String getHo_ten() {
        return ho_ten;
    }

    public void setHo_ten(String ho_ten) {
        this.ho_ten = ho_ten;
    }

    public boolean isGioi_tinh() {
        return gioi_tinh;
    }

    public void setGioi_tinh(boolean gioi_tinh) {
        this.gioi_tinh = gioi_tinh;
    }

    public Date getNgay_thang_nam_sinh() {
        return ngay_thang_nam_sinh;
    }

    public void setNgay_thang_nam_sinh(Date ngay_thang_nam_sinh) {
        this.ngay_thang_nam_sinh = ngay_thang_nam_sinh;
    }

    public String getCMND() {
        return CMND;
    }

    public void setCMND(String CMND) {
        this.CMND = CMND;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public List<HopDongKTX> getHopDongKTXList() {
        return hopDongKTXList;
    }

    public void setHopDongKTXList(List<HopDongKTX> hopDongKTXList) {
        this.hopDongKTXList = hopDongKTXList;
    }


    public Users() {
    }
}
