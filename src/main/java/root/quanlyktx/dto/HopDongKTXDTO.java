package root.quanlyktx.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import root.quanlyktx.entity.PhongKTX;
import root.quanlyktx.entity.Term;

import java.util.Date;

@Data
//@AllArgsConstructor
@ToString
public class HopDongKTXDTO {
    private Integer id;
    private Integer idPhongKTX;
    private String MSSV;
    @JsonFormat(pattern = "dd/MM/yyyy", timezone = "Asia/Ho_Chi_Minh")
    private Date ngayLamDon;
    private boolean trangThai;
    private Integer idTerm;
    @JsonIgnore
    private PhongKTXDTO phongKTX;
    private TermDTO termDTO;

    public HopDongKTXDTO(Integer idPhongKTX, String MSSV) {
        this.idPhongKTX = idPhongKTX;
        this.MSSV = MSSV;
    }

}
