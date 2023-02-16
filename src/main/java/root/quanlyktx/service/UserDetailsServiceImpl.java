package root.quanlyktx.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import root.quanlyktx.entity.User;
import root.quanlyktx.entity.HandleUserDetail;
import root.quanlyktx.repository.UserRepository;

import javax.transaction.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserRepository userRepository;
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if(user ==null){
            throw new UsernameNotFoundException("User not found");
        }
//        InMemoryUserDetailsManager inMemoryUserDetailsManager= new InMemoryUserDetailsManager(account);

        return HandleUserDetail.build(user);
    }

}
