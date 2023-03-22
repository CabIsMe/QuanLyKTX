package root.quanlyktx.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import root.quanlyktx.dto.HopDongKTXDTO;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ViewContractRoomList {
    private HopDongKTXDTO hopDongKTX;
    private String fullName;
    private Double total;
}
