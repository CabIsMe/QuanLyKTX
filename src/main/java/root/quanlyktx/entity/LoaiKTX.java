package root.quanlyktx.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="loai_KTX")
public class LoaiKTX {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "so_giuong")
    private Integer soGiuong;
    @Column(name = "gia_phong")
    private Double giaPhong;
    @Column(name = "hinh_anh")
    private String Image;
    @Column(name = "ten_loai")
    private String tenLoai;
    @Column(name = "mo_ta")
    private String description;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "loaiKTX")
    private List<PhongKTX> phongKTXList;

    public String getTenLoai() {
        return tenLoai;
    }

    public void setTenLoai(String tenLoai) {
        this.tenLoai = tenLoai;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSoGiuong() {
        return soGiuong;
    }

    public void setSoGiuong(Integer soGiuong) {
        this.soGiuong = soGiuong;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
    public Integer getId() {
        return id;
    }

    public Integer getSoNguoi() {
        return soGiuong;
    }

    public void setSoNguoi(Integer soNguoi) {
        this.soGiuong = soNguoi;
    }

    public Double getGiaPhong() {
        return giaPhong;
    }

    public void setGiaPhong(Double giaPhong) {
        this.giaPhong = giaPhong;
    }

    public List<PhongKTX> getPhongKTXList() {
        return phongKTXList;
    }

    public void setPhongKTXList(List<PhongKTX> phongKTXList) {
        this.phongKTXList = phongKTXList;
    }

    public LoaiKTX(Integer id, Integer soGiuong, Double giaPhong) {
        this.id = id;
        this.soGiuong = soGiuong;
        this.giaPhong = giaPhong;
    }

    public LoaiKTX() {

    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
