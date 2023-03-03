package root.quanlyktx.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import root.quanlyktx.entity.OTP;
import root.quanlyktx.entity.Role;

import java.sql.Date;

@Data
@Getter
@Setter
public class AdminDto {
    private String username;
    private String hoTen;
    private boolean gioiTinh;
    private Date ngaySinh;
    private String CMND;
    private String SDT;
    private String mail;
    private OTP otp;
    private Role role;
}
