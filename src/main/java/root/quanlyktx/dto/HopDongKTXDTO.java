package root.quanlyktx.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import java.util.Date;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class HopDongKTXDTO {
    private Integer id;
    private Integer idPhongKTX;
    private String MSSV;
    @JsonFormat(pattern = "dd/MM/yyyy", timezone = "Asia/Ho_Chi_Minh")
    private Date ngayLamDon;
    private Double total;
    private boolean trangThai;
    private Integer idTerm;
    @JsonIgnore
    private PhongKTXDTO phongKTX;
    @JsonIgnore
    private TermDTO term;

    public String getMSSV() {
        return MSSV;
    }

    public void setMSSV(String MSSV) {
        this.MSSV = MSSV;
    }
}
