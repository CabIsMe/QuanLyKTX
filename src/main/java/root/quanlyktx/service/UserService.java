package root.quanlyktx.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import root.quanlyktx.entity.User;
import root.quanlyktx.repository.UserRepository;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
//    @Autowired
//    BCryptPasswordEncoder bCryptPasswordEncoder;

     public Boolean handleLogin(User user){
         User user1 = userRepository.findByUsername(user.getUsername());
         BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
         if(user1 ==null){
             return false;
         }
         if(!encoder.matches(user.getPassword(), user1.getPassword())){
             return false;
         }

         return true;
     }
     public Boolean addAccount(User user){
         BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
         try {
             user.setPassword(encoder.encode(user.getPassword()));
             userRepository.save(user);
             return true;
         }catch (Exception e){
             e.getStackTrace();
             return false;
         }
     }
     public List<User> getAll(){
         return userRepository.findAll();
     }
}
