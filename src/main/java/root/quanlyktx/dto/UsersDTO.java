package root.quanlyktx.dto;


import lombok.Data;

import java.sql.Date;
@Data
public class UsersDTO {


    private String MSSV;

    private String ho_ten;

    private boolean gioi_tinh;

    private Date ngay_thang_nam_sinh;

    private String CMND;

    private String SDT;

    private String mail;

    private String password;

    private Integer phanQuyen;


}
