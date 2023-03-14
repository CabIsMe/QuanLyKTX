package root.quanlyktx.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;

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
    private Integer idLoaiKTX;

//    @Column(name = "hinh_anh")
//    private String hinhAnh;

    @OneToMany(mappedBy = "maSoKTX", fetch = FetchType.LAZY)
    private List<PhieuDienKTX> phieuDienKTXList;

    @OneToMany(mappedBy = "maSoKTX")
    private List<PhieuNuocKTX> phieuNuocKTXList;

    @JsonIgnore
    @OneToMany(mappedBy = "phongKTX")
    private List<HopDongKTX> hopDongKTXList;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "loai_KTX",referencedColumnName = "id",insertable = false,updatable = false)
    private LoaiKTX loaiKTX;

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

    public Integer getIdLoaiKTX() {
        return idLoaiKTX;
    }

    public void setIdLoaiKTX(Integer idLoaiKTX) {
        this.idLoaiKTX = idLoaiKTX;
    }

    public LoaiKTX getLoaiKTX() {
        return loaiKTX;
    }

    public void setLoaiKTX(LoaiKTX loaiKTX) {
        this.loaiKTX = loaiKTX;
    }
}
