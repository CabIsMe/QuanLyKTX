package root.quanlyktx.dto;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Data
@Getter
@Setter
public class HopDongKTXDTO {
    private Integer id;

    private Integer phongKTX;

    private String MSSV;

    private Date ngayLamDon;

    private Date ngayHieuLuc;

    private Date ngayKetThuc;

    private boolean trangThai;

}
