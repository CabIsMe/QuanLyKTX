package root.quanlyktx.model;


import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ThongTinPhong {
    private Integer id;
    private Double giaPhong;
    private Integer soGiuongTrong;
    private String image;
}
