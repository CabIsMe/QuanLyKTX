package root.quanlyktx.model;


import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RoomDetails {
    //bo sung them ten loai phong
    private Integer id;
    private Double giaPhong;
    private Integer soGiuongTrong;
    private String image;

}
