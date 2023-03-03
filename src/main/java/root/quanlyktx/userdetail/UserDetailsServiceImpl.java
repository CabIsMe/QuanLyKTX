package root.quanlyktx.userdetail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import root.quanlyktx.entity.Admin;
import root.quanlyktx.entity.Student;
import root.quanlyktx.repository.AdminRepository;
import root.quanlyktx.userdetail.HandleStudentDetail;
import root.quanlyktx.repository.StudentRepository;

import javax.transaction.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    AdminRepository adminRepository;
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Student student = studentRepository.findByUsername(username);
        Admin admin= adminRepository.findByUsername(username);
        if(student ==null && admin == null){
            throw new UsernameNotFoundException("User not found");
        }
//        InMemoryUserDetailsManager inMemoryUserDetailsManager= new InMemoryUserDetailsManager(account);
        if(student == null){
            return HandleAdminDetail.build(admin);
        }
        return HandleStudentDetail.build(student);
    }

}
