package root.quanlyktx.model;

import lombok.Data;

@Data
public class StudentsOfRoomtypes {
    private String nameRoomtype;
    private Integer amoutStudents;
    private Double price;

    public StudentsOfRoomtypes(String nameRoomtype, Integer amoutStudents, Double price) {
        this.nameRoomtype = nameRoomtype;
        this.amoutStudents = amoutStudents;
        this.price = price;
    }
}
