package root.quanlyktx.model;


import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RoomDetails {
    private Integer id;
    private String nameTypeRoom;
    private Double cost;
    private Integer emptyBed;
    private String image;
    private String desc;

}
