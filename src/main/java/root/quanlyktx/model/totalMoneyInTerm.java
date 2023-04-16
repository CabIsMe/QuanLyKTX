package root.quanlyktx.model;

import lombok.Data;

@Data
public class totalMoneyInTerm {
    public Double amountEarned;
    public Double percent;

    public totalMoneyInTerm(Double amountEarned, Double percent) {
        this.amountEarned = amountEarned;
        this.percent = percent;
    }
}
