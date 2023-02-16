package root.quanlyktx.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name="phong_KTX")
public class PhongKTX {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "loai_KTX")
    private Integer loaiKTX;

    //    @JsonIgnore
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "loai_KTX")
//    private LoaiKTX loaiKTX;
    public PhongKTX(){}

    public Integer getId() {
        return id;
    }


    public Integer getLoaiKTX() {
        return loaiKTX;
    }

    public void setLoaiKTX(Integer loaiKTX) {
        this.loaiKTX = loaiKTX;
    }


    public PhongKTX(Integer id, Integer loaiKTX) {
        this.id = id;
        this.loaiKTX = loaiKTX;

    }
}
