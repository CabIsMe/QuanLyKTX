package root.quanlyktx.model;

import lombok.Data;
import lombok.ToString;

import java.util.Random;

@Data
public class InputBillPerMonth {
    private Integer amountOfWater;
    private Integer amountOfElectric;
    public static Integer generateRandomNumberWater() {
        Random rand = new Random();
        return rand.nextInt(5) + 1;
    }
    public static Integer generateRandomNumberElectric() {
        Random rand = new Random();
        return rand.nextInt(41) + 10;
    }

    public InputBillPerMonth() {
        this.amountOfWater=generateRandomNumberWater();
        this.amountOfElectric=generateRandomNumberElectric();
    }
}
