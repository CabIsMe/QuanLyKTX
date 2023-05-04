package root.quanlyktx.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="loai_KTX")
@ToString
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
    private String image;
    @Column(name = "ten_loai")
    private String tenLoai;
    @Column(name = "gioi_tinh")
    private boolean gioiTinh;
    @Column(name="mo_ta")
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
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
    public Integer getId() {
        return id;
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

    public LoaiKTX(Integer soGiuong, Double giaPhong, String image, String tenLoai, boolean gioiTinh, String description) {
        this.soGiuong = soGiuong;
        this.giaPhong = giaPhong;
        this.image = image;
        this.tenLoai = tenLoai;
        this.gioiTinh = gioiTinh;
        this.description = description;
    }

    public LoaiKTX() {

    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(boolean gioiTinh) {
        this.gioiTinh = gioiTinh;
    }
}
