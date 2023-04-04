package root.quanlyktx.model;


import lombok.Data;
import lombok.ToString;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Data
@ToString
public class OperationHistory {
    private String fileName;

    public OperationHistory(String fileName) {
        this.fileName = fileName;
    }

    public List<String> LoadDataToList(){
        List<String> records= new ArrayList<>();
        try {
            Scanner scanner = new Scanner(new File(this.fileName));

            while (scanner.hasNextLine()) {
                records.add(scanner.nextLine());
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return records;
    }
}
