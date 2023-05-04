package root.quanlyktx.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import root.quanlyktx.dto.PhongKTXDTO;

@Data
@AllArgsConstructor
public class CurrentInfoRoom {
    private PhongKTXDTO phongKTX;
    private Integer currentContract;
}
