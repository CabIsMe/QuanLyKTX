package root.quanlyktx.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "gia_dien_theo_thang")

public class GiaDienTheoThang {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "thang")
    private Integer thang;
    @Column(name ="nam")
    private Integer nam;
    @Column(name = "gia_dien")
    private double giaDien;

//    @OneToMany(mappedBy = "idDonGia")
//    private List<PhieuDienKTX> phieuDienKTXList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getThang() {
        return thang;
    }

    public void setThang(Integer thang) {
        this.thang = thang;
    }

    public Integer getNam() {
        return nam;
    }

    public void setNam(Integer nam) {
        this.nam = nam;
    }

    public double getGiaDien() {
        return giaDien;
    }

    public void setGiaDien(double giaDien) {
        this.giaDien = giaDien;
    }

    public GiaDienTheoThang(Integer thang, Integer nam, double giaDien) {
        this.thang = thang;
        this.nam = nam;
        this.giaDien = giaDien;
    }

    public GiaDienTheoThang() {
    }
}
