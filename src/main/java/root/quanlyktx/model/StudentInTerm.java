package root.quanlyktx.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
public class StudentInTerm {
    private int idTerm;
    @JsonFormat(pattern = "dd/MM/yyyy", timezone = "Asia/Ho_Chi_Minh")
    private Date dateStart;
    @JsonFormat(pattern = "dd/MM/yyyy", timezone = "Asia/Ho_Chi_Minh")
    private Date dateEnd;
    private long amountStudent;

    public StudentInTerm(int idTerm, Date dateStart, Date dateEnd, long amountStudent) {
        this.idTerm = idTerm;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.amountStudent = amountStudent;
    }

    public StudentInTerm() { }
}
