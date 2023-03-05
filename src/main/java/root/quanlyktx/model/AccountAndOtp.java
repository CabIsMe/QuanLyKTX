package root.quanlyktx.model;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class AccountAndOtp {
    private String username;
    private String password;
    private String OTP;
}
