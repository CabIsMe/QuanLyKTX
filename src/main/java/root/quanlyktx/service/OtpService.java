package root.quanlyktx.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import root.quanlyktx.entity.OTP;
import root.quanlyktx.repository.OtpRepository;

@Service
public class OtpService {
    @Autowired
    OtpRepository otpRepository;

    public boolean saveOTP(OTP otp){
        try{
            otpRepository.save(otp);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

}
