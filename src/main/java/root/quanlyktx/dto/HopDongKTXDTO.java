package root.quanlyktx.dto;


import lombok.Data;

import java.sql.Date;
@Data
public class HopDongKTXDTO {


    private Integer id;

    private Integer phongKTX;

    private String MSSV;

    private Date ngayLamDon;

    private Date ngayBatDau;

    private Date ngayKetThuc;

    private boolean trangThai;

}
