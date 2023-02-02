package root.quanlyktx.entity;

import javax.persistence.*;

@Entity
@Table(name="loai_KTX")
public class LoaiKTX {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "so_nguoi")
    private Integer soNguoi;
    @Column(name = "gia_phong")
    private Double giaPhong;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSoNguoi() {
        return soNguoi;
    }

    public void setSoNguoi(Integer soNguoi) {
        this.soNguoi = soNguoi;
    }

    public Double getGiaPhong() {
        return giaPhong;
    }

    public void setGiaPhong(Double giaPhong) {
        this.giaPhong = giaPhong;
    }

    public LoaiKTX(Integer id, Integer soNguoi, Double giaPhong) {
        this.id = id;
        this.soNguoi = soNguoi;
        this.giaPhong = giaPhong;
    }

    public LoaiKTX() {

    }
}
