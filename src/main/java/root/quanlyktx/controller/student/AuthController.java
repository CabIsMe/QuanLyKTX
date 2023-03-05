package root.quanlyktx.controller.student;

import org.aspectj.lang.annotation.Around;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import root.quanlyktx.email.EmailDetails;
import root.quanlyktx.email.EmailService;
import root.quanlyktx.entity.Admin;
import root.quanlyktx.entity.OTP;
import root.quanlyktx.entity.Student;
import root.quanlyktx.model.AccountAndOtp;
import root.quanlyktx.model.JwtResponseAndOtp;
import root.quanlyktx.model.OTPCode;
import root.quanlyktx.service.OtpService;
import root.quanlyktx.userdetail.HandleStudentDetail;
import root.quanlyktx.jwt.JwtResponse;
import root.quanlyktx.jwt.JwtUtils;
import root.quanlyktx.repository.StudentRepository;
import root.quanlyktx.service.StudentService;


import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private StudentService studentService;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private EmailService emailService;

    @Autowired
    private OtpService otpService;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody Student student) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(student.getUsername(), student.getPassword()));
        Student student1=studentService.getStudentByUsername(student.getUsername());
        if(!student1.isStatus()){
            char[] OTP= OTPCode.generateOTP();
            Date date = new Date();
            root.quanlyktx.entity.OTP otp=new OTP(student.getUsername(),new String(OTP),date.getTime());
            if(!otpService.saveOTP(otp)){
                return ResponseEntity.badRequest().body("Save OTP failed");
            }
            System.out.println(emailService.sendSimpleMail(new EmailDetails(student1.getMail(),"OTP: " + otp.getOtpCode(),"XÁC THỰC OTP")));
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Your account is not yet verified");
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        HandleStudentDetail userDetails = (HandleStudentDetail) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());
        return ResponseEntity.ok(new JwtResponse(jwt,userDetails.getUsername(),roles));
    }


    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody Student student) {
        return studentService.registerStudent(student);

    }
//    @GetMapping()
//    public boolean resetOTP(@RequestBody AccountAndOtp accountAndOtp){
//        char[] OTP= OTPCode.generateOTP();
//        Date date = new Date();
//        root.quanlyktx.entity.OTP otp=new OTP(accountAndOtp.getUsername(),new String(OTP),date.getTime());
//        if(!otpService.saveOTP(otp)){
//            return false;
//        }
//        Student
//        System.out.println(emailService.sendSimpleMail(new EmailDetails(student1.getMail(),"OTP: " + otp.getOtpCode(),"XÁC THỰC OTP")));
//    }
    @PostMapping("/two-factor-auth")
    public ResponseEntity<?> verifyStudent(@RequestBody AccountAndOtp accountAndOtp){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(accountAndOtp.getUsername(), accountAndOtp.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        HandleStudentDetail userDetails = (HandleStudentDetail) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());
        OTP otp= otpService.getOtpByUsername(accountAndOtp.getUsername());

        if(otp==null)
            return ResponseEntity.badRequest().body("OTP not found!");
        if(!otp.getOtpCode().equals(accountAndOtp.getOTP())){
            return ResponseEntity.badRequest().body("OTP incorrect!");
        }
        studentService.updateStatus(accountAndOtp.getUsername(),true);
        return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getUsername(),roles));
    }
}
