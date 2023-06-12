package root.quanlyktx.userdetail;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import root.quanlyktx.entity.Admin;
import root.quanlyktx.entity.Student;
import root.quanlyktx.repository.AdminRepository;
import root.quanlyktx.repository.StudentRepository;

@Component
public class UserDetailFactoryImpl implements UserDetailFactory{
    private final StudentRepository studentRepository;
    private final AdminRepository adminRepository;

    public UserDetailFactoryImpl(StudentRepository studentRepository, AdminRepository adminRepository) {
        this.studentRepository = studentRepository;
        this.adminRepository = adminRepository;
    }

    @Override
    @Transactional
    public UserDetails createUserDetails(String username) {
        Student student = studentRepository.findByUsername(username);
        Admin admin = adminRepository.findByUsername(username);
        if (student == null && admin == null) {
            throw new UsernameNotFoundException("User not found");
        }
        if (student == null) {

            return HandleAdminDetail.build(admin);
        }
        return HandleStudentDetail.build(student);
    }
}
