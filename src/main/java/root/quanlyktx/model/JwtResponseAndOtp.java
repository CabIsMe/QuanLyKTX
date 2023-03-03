package root.quanlyktx.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import root.quanlyktx.jwt.JwtResponse;

import java.util.List;

public class JwtResponseAndOtp extends JwtResponse {
    private char [] OTP;
    public JwtResponseAndOtp(String token, String username, List<String> roles) {
        super(token, username, roles);
    }

    public JwtResponseAndOtp(String token, String username, List<String> roles, char[] OTP) {
        super(token, username, roles);
        this.OTP = OTP;
    }

    public char[] getOTP() {
        return OTP;
    }

    public void setOTP(char[] OTP) {
        this.OTP = OTP;
    }
}
