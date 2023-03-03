package root.quanlyktx.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import root.quanlyktx.entity.Admin;
import root.quanlyktx.jwt.JwtUtils;
import root.quanlyktx.repository.AdminRepository;
import root.quanlyktx.userdetail.HandleAdminDetail;
import root.quanlyktx.entity.OTP;
import root.quanlyktx.model.JwtResponseAndOtp;
import root.quanlyktx.model.OTPCode;
import root.quanlyktx.service.OtpService;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("api/admin")
public class AdminAuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    AdminRepository adminRepository;

    @Autowired
    OtpService otpService;


    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody Admin admin) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(admin.getUsername(), admin.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
//
        char[] OTP= OTPCode.generateOTP();
        Date date = new Date();
        System.out.println(date.getTime());
        OTP otp=new OTP(admin.getUsername(),new String(OTP),date.getTime());
        if(!otpService.saveOTP(otp)){
            return ResponseEntity.badRequest().body("Save OTP failed");
        }
        HandleAdminDetail userDetails = (HandleAdminDetail) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());
        return ResponseEntity.ok(new JwtResponseAndOtp(jwt, userDetails.getUsername(),roles,OTP));
    }
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody Admin admin) {
        if (adminRepository.existsById(admin.getUsername())) {
            return ResponseEntity.badRequest().body("Error: Username is already taken!");
        }
        // Create new user's account
        try{
            Admin admin1 = new Admin(admin.getUsername(), encoder.encode(admin.getPassword()));
            // student la 2
            admin1.setRole_id(1);
            adminRepository.save(admin1);
            return ResponseEntity.ok("User registered successfully!");
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseEntity
                    .badRequest()
                    .body("Error: Can't save User");
        }

    }
    @PostMapping("/two-factor-auth")
    public boolean twoFactorAuthentication(@RequestBody OTP otp){
        System.out.println("=>>"+new Date().getTime() +"<====>"+ (otp.getTimeGenerate()));
        // Thời gian xác thực tối đa 3p
        return new Date().getTime() - (otp.getTimeGenerate()) < (3*60*1000);
    }
}
