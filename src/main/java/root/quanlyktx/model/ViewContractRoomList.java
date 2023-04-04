package root.quanlyktx.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import root.quanlyktx.dto.HopDongKTXDTO;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ViewContractRoomList {
    private HopDongKTXDTO hopDongKTX;
    private String fullName;
    @JsonFormat(pattern = "dd/MM/yyyy", timezone = "Asia/Ho_Chi_Minh")
    private Date datePayment;
    private Double total;
}
