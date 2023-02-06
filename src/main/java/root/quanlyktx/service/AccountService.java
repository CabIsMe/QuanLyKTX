package root.quanlyktx.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import root.quanlyktx.entity.Account;
import root.quanlyktx.repository.AccountRepository;

@Service
public class AccountService {
    @Autowired
    AccountRepository accountRepository;
//    @Autowired
//    BCryptPasswordEncoder bCryptPasswordEncoder;

     public Boolean proccessLogin(Account account){
         Account account1=accountRepository.findByUsername(account.getUsername());
         BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
         if(account1==null){
             return false;
         }
         if(!encoder.matches(account.getPassword(),account1.getPassword())){
             return false;
         }

         return true;
     }
}
