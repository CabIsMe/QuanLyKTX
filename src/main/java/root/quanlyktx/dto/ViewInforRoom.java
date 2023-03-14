package root.quanlyktx.dto;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import root.quanlyktx.entity.LoaiKTX;

@Data
@Getter
@Setter
public class ViewInforRoom {
//    private String img;
    private Integer idPhong;
//    private Double price;
//    private Integer numBed;
    private Integer numBedEmpty;
    private LoaiKTX loaiKTX;
//    private String description;
//    private String fullName;
//    private String mssv;
//    private String phoneNumber;
//    private String email;
}
