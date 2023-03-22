package root.quanlyktx.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import root.quanlyktx.entity.OTP;
import root.quanlyktx.entity.Role;

import javax.persistence.Column;
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
