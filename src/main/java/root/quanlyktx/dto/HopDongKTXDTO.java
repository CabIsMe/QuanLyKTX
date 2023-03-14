package root.quanlyktx.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import root.quanlyktx.entity.PhongKTX;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HopDongKTXDTO {
    private Integer id;

    private Integer idPhongKTX;

    private String MSSV;
    @JsonFormat(pattern = "dd/MM/yyyy", timezone = "Asia/Ho_Chi_Minh")
    private Date ngayLamDon;
    @JsonFormat(pattern = "dd/MM/yyyy", timezone = "Asia/Ho_Chi_Minh")
    private Date ngayHieuLuc;
    @JsonFormat(pattern = "dd/MM/yyyy", timezone = "Asia/Ho_Chi_Minh")
    private Date ngayKetThuc;

    private boolean trangThai;
    @JsonIgnore
    private PhongKTXDTO phongKTX;

    public HopDongKTXDTO(Integer idPhongKTX, String MSSV) {
        this.idPhongKTX = idPhongKTX;
        this.MSSV = MSSV;
    }
}
