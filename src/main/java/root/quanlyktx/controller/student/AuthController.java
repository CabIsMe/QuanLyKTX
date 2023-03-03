package root.quanlyktx.controller.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import root.quanlyktx.entity.Student;
import root.quanlyktx.userdetail.HandleStudentDetail;
import root.quanlyktx.jwt.JwtResponse;
import root.quanlyktx.jwt.JwtUtils;
import root.quanlyktx.repository.StudentRepository;
import root.quanlyktx.service.StudentService;


import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    StudentService studentService;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody Student student) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(student.getUsername(), student.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        HandleStudentDetail userDetails = (HandleStudentDetail) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());
        return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getUsername(),roles));
    }


    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody Student student) {
        if (studentRepository.existsById(student.getUsername())) {
            return ResponseEntity.badRequest().body("Error: Username is already taken!");
        }

        // Create new user's account
        try{
            Student student1 = new Student(student.getUsername(), encoder.encode(student.getPassword()));
            // student la 2
            student1.setRole_id(4);
            studentRepository.save(student1);
            return ResponseEntity.ok("User registered successfully!");
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseEntity
                    .badRequest()
                    .body("Error: Can't save User");
        }

    }
}
