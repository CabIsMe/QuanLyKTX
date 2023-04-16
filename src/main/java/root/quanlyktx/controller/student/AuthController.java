package root.quanlyktx.controller.student;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import root.quanlyktx.entity.OTP;
import root.quanlyktx.entity.Student;
import root.quanlyktx.model.AccountAndOtp;
import root.quanlyktx.model.PasswordEditing;

import root.quanlyktx.repository.HopDongKTXRepository;
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
    private final int otpExp =3;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private StudentService studentService;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private OtpService otpService;

    @Autowired
    private HopDongKTXRepository hopDongKTXRepository;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody Student student) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(student.getUsername(), student.getPassword()));

        Student student1=studentService.getStudentByUsername(student.getUsername());
        if(!student1.isStatus()){
            if(hopDongKTXRepository.existsByMSSV(student1.getUsername())){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Account not accessible");
            }
            try {
                otpService.sendOTP(student1);
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Send mail error");
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


    @PostMapping("/signup")
    @Transactional(rollbackFor = {Exception.class, Throwable.class})
    public ResponseEntity<?> registerUser(@RequestBody Student student) {
        return studentService.registerStudent(student);

    }
    @PostMapping("/two-factor-auth")
    public ResponseEntity<?> verifyStudent(@RequestBody AccountAndOtp accountAndOtp){
        OTP otp= otpService.getOtpByUsername(accountAndOtp.getUsername());
        if(otp==null)
            return ResponseEntity.badRequest().body("OTP not found!");

        if(new Date().getTime() - (otp.getTimeGenerate()) > (otpExp*60*1000) || !otp.getOtpCode().equals(accountAndOtp.getOTP())){
            return ResponseEntity.badRequest().body("OTP expired or incorrect!");
        }
        studentService.updateStatus(accountAndOtp.getUsername(),true);
        return ResponseEntity.ok(true);
    }
    @GetMapping("/resend-otp/{MSSV}")
    public ResponseEntity<?> resendOtp(@PathVariable String MSSV){
        Student student=studentService.getStudentByUsername(MSSV);
       if(student!=null){
           try {
               otpService.sendOTP(student);
           } catch (Exception e) {
               e.printStackTrace();
               return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Send mail error");
           }
       }
       return ResponseEntity.ok(true);
    }

    @PreAuthorize("hasAuthority('student')")
    @PostMapping("/change-password")
    public boolean changePass(@RequestBody PasswordEditing passwordEditing){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authentication.getName(), passwordEditing.getOldPassword()));
        }catch (Exception e){
            return false;
        }
        if(!authentication.isAuthenticated())
            return false;

        return studentService.changePassword(authentication.getName(), passwordEditing.getNewPassword());
    }

    @GetMapping("/forgot-password/{mssv}")
    public ResponseEntity<?> forgotPassword(@PathVariable("mssv") String mssv)throws Exception{
        return studentService.forgotPass(mssv);
    }
}
