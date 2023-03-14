package root.quanlyktx.model;


import lombok.*;
import root.quanlyktx.dto.StudentDto;
import root.quanlyktx.entity.LoaiKTX;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ViewInforRoom {
//    private String img;
    private Integer idPhong;
    private LoaiKTX loaiKTX;
//    private Double price;
//    private Integer numBed;
    private Integer numBedEmpty;

//    private String description;
//    private String fullName;
//    private String mssv;
//    private String phoneNumber;
//    private String email;
}
