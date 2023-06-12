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
    private final UserDetailFactory userDetailFactory;

    @Autowired
    public UserDetailsServiceImpl(UserDetailFactory userDetailFactory) {
        this.userDetailFactory = userDetailFactory;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userDetailFactory.createUserDetails(username);
    }
}
