package root.quanlyktx.controller.student;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import root.quanlyktx.entity.OTP;
import root.quanlyktx.entity.Student;
import root.quanlyktx.model.AccountAndOtp;
import root.quanlyktx.service.OtpService;
import root.quanlyktx.userdetail.HandleStudentDetail;
import root.quanlyktx.jwt.JwtResponse;
import root.quanlyktx.jwt.JwtUtils;

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
    private OtpService otpService;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody Student student) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(student.getUsername(), student.getPassword()));

        Student student1=studentService.getStudentByUsername(student.getUsername());
        if(!student1.isStatus()){
            try {
                otpService.sendOTP(student1);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Your account is not yet verified, Pleas again");
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        HandleStudentDetail userDetails = (HandleStudentDetail) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());
        return ResponseEntity.ok(new JwtResponse(jwt,userDetails.getUsername(),roles));
    }

    @Transactional(rollbackFor = {Exception.class, Throwable.class})
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody Student student) throws Exception{
        if(!studentService.registerStudent(student)){
            throw new Exception("Register failed");
        }
        Student student1=studentService.getStudentByUsername(student.getUsername());
        otpService.sendOTP(student1);
        return ResponseEntity.ok().body("Register success!");

    }
    @PutMapping("/verify-again")
    public ResponseEntity<?> resetOTP(@RequestBody AccountAndOtp accountAndOtp){
        OTP otp= otpService.getOtpByUsername(accountAndOtp.getUsername());
        Student student1=studentService.getStudentByUsername(accountAndOtp.getUsername());
        if( !student1.isStatus()){
            if(accountAndOtp.getOTP().equals(otp.getOtpCode())&& new Date().getTime() - (otp.getTimeGenerate()) < (3*60*1000)){
                studentService.updateStatus(accountAndOtp.getUsername(),true);
                return ResponseEntity.ok(true);
            }
            return ResponseEntity.badRequest().body("OTP expired or incorrect!");
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Status is True");
    }
    @PostMapping("/two-factor-auth")
    public ResponseEntity<?> verifyStudent(@RequestBody AccountAndOtp accountAndOtp){
        OTP otp= otpService.getOtpByUsername(accountAndOtp.getUsername());

        if(otp==null)
            return ResponseEntity.badRequest().body("OTP not found!");

        if(new Date().getTime() - (otp.getTimeGenerate()) > (3*60*1000) || !otp.getOtpCode().equals(accountAndOtp.getOTP())){
            return ResponseEntity.badRequest().body("OTP expired or incorrect!");
        }
        studentService.updateStatus(accountAndOtp.getUsername(),true);
        return ResponseEntity.ok(true);
    }
}
