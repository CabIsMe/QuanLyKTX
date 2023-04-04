package root.quanlyktx.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "gia_nuoc_theo_thang")
public class GiaNuocTheoThang {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "thang")
    private Integer thang;
    @Column(name ="nam")
    private Integer nam;
    @Column(name = "gia_nuoc")
    private double giaNuoc;

//    @OneToMany(mappedBy = "idDonGia", fetch = FetchType.EAGER)
//    private List<PhieuNuocKTX> phieuNuocKTXList= new ArrayList<>();

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

    public double getGiaNuoc() {
        return giaNuoc;
    }

    public void setGiaNuoc(double giaNuoc) {
        this.giaNuoc = giaNuoc;
    }

//    public List<PhieuNuocKTX> getPhieuNuocKTXList() {
//        return phieuNuocKTXList;
//    }
//
//    public void setPhieuNuocKTXList(List<PhieuNuocKTX> phieuNuocKTXList) {
//        this.phieuNuocKTXList = phieuNuocKTXList;
//    }


    public GiaNuocTheoThang(Integer thang, Integer nam, double giaNuoc) {
        this.thang = thang;
        this.nam = nam;
        this.giaNuoc = giaNuoc;
    }

    public GiaNuocTheoThang() {
    }
}
