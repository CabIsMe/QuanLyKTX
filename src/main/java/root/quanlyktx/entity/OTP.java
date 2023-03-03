package root.quanlyktx.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "otps")
public class OTP {
    @Id
    @Column(name="username")
    private String username;
    @Column(name = "code")
    private String otpCode;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone="Asia/Ho_Chi_Minh")
    @Column(name = "time_generate")
    private Long timeGenerate;
}
