package root.quanlyktx.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Data
@NoArgsConstructor
public class ViewBills {
    private Optional<?> optional;
    private Double total;

    public ViewBills(Optional<?> optional, Double total) {
        this.optional = optional;
        this.total = total;
    }
}
