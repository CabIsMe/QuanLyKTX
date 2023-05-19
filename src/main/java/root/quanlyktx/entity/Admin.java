package root.quanlyktx.entity;

import lombok.ToString;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "admins")
@ToString
public class Admin {
    @Id
    @Column(name = "MSCB")
    private String username;
    @Column(name = "ho_ten")
    private String hoTen;
    @Column(name = "gioi_tinh")
    private boolean gioiTinh;
    @Column(name = "ngay_thang_nam_sinh")
    private Date ngaySinh;
    private String CMND;
    private String SDT;
    private String mail;
    private String password;
    @Column(name = "phan_quyen")
    private Integer role_id;
//    @Column(name = "trang_thai")
//    private boolean status;
    @ManyToOne()
    @JoinColumn(name="phan_quyen", referencedColumnName = "id", insertable = false, updatable = false)
    private Role role;

//    public boolean isStatus() {
//        return status;
//    }
//
//    public void setStatus(boolean status) {
//        this.status = status;
//    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Role getRole() {
        return role;
    }
    public Integer getRole_id() {
        return role_id;
    }

    public void setRole_id(Integer role_id) {
        this.role_id = role_id;
    }
    public Admin() {
    }

    public Admin(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Admin(String username, String hoTen, String mail, String password, Integer role_id) {
        this.username = username;
        this.hoTen=hoTen;
        this.mail = mail;
        this.password = password;
        this.role_id = role_id;
    }



    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public boolean isGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(boolean gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public Date getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(Date ngaySinh) {
        this.ngaySinh = ngaySinh;
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



    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
