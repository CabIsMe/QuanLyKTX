package root.quanlyktx.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name ="phong_KTX")
public class PhongKTX {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

//    @ManyToOne
    @Column(name = "loai_KTX")
    private Integer loaiKTX;

    @Column(name = "hinh_anh")
    private String hinhAnh;

    @OneToMany(mappedBy = "maSoKTX")
    private List<PhieuDienKTX> phieuDienKTXList;

    @OneToMany(mappedBy = "maSoKTX")
    private List<PhieuNuocKTX> phieuNuocKTXList;

    @OneToMany(mappedBy = "phongKTX")
    private List<HopDongKTX> hopDongKTXList;

    public List<HopDongKTX> getHopDongKTXList() {
        return hopDongKTXList;
    }

    public void setHopDongKTXList(List<HopDongKTX> hopDongKTXList) {
        this.hopDongKTXList = hopDongKTXList;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public Integer getLoaiKTX() {
        return loaiKTX;
    }

    public void setLoaiKTX(Integer loaiKTX) {
        this.loaiKTX = loaiKTX;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public List<PhieuDienKTX> getPhieuDienKTXList() {
        return phieuDienKTXList;
    }

    public void setPhieuDienKTXList(List<PhieuDienKTX> phieuDienKTXList) {
        this.phieuDienKTXList = phieuDienKTXList;
    }

    public List<PhieuNuocKTX> getPhieuNuocKTXList() {
        return phieuNuocKTXList;
    }

    public void setPhieuNuocKTXList(List<PhieuNuocKTX> phieuNuocKTXList) {
        this.phieuNuocKTXList = phieuNuocKTXList;
    }

    public PhongKTX() {
    }
}
