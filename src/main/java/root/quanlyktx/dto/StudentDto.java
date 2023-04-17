package root.quanlyktx.dto;

import lombok.Data;
import java.sql.Date;

@Data

public class StudentDto {
    private String username;
    private String hoTen;
    private boolean gioiTinh;
    private Date ngaySinh;
    private String CMND;
    private String SDT;
    private String mail;
}
