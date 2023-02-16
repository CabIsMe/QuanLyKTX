package root.quanlyktx.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import java.sql.Date;

@Data
@Getter
@Setter
public class UserDto {
    private String username;
    private String hoTen;
    private boolean gioiTinh;
    private Date ngaySinh;
    private String CMND;
    private String SDT;
    private String mail;
}
