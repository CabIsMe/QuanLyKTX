package root.quanlyktx.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import root.quanlyktx.email.EmailDetails;
import root.quanlyktx.email.EmailServiceIml;
import root.quanlyktx.entity.OTP;
import root.quanlyktx.entity.Student;
import root.quanlyktx.model.OTPCode;
import root.quanlyktx.repository.OtpRepository;

import java.util.Date;

@Service
public class OtpService {
    @Autowired
    private OtpRepository otpRepository;
    @Autowired
    private EmailServiceIml emailServiceIml;

    public boolean saveOTP(OTP otp){
        try{
            otpRepository.save(otp);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public OTP getOtpByUsername(String username){
        try{
            return otpRepository.getOTPByUsername(username);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    @Transactional(rollbackFor = {Exception.class, Throwable.class})
    public void sendOTP(Student student) throws Exception{
        char[] OTP= OTPCode.generateOTP();
        Date date = new Date();
        OTP otp=new OTP(student.getUsername(),new String(OTP),date.getTime());
        if(!saveOTP(otp)){
            throw new Exception("OTP Failed");
        }
        if(!emailServiceIml.sendSimpleMail(new EmailDetails(student.getMail(),"OTP: " + otp.getOtpCode(),"XÁC THỰC OTP"))){
            throw new Exception("Send email error");
        }

    }

}
