package root.quanlyktx.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import root.quanlyktx.entity.Account;
import root.quanlyktx.entity.HandleUserDetail;
import root.quanlyktx.repository.AccountRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    AccountRepository accountRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account=accountRepository.findByUsername(username);
        if(account==null){
            throw new UsernameNotFoundException("User not found");
        }
        return new HandleUserDetail(account);
    }
}
