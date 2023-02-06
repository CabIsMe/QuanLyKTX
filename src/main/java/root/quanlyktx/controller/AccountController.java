package root.quanlyktx.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import root.quanlyktx.entity.Account;
import root.quanlyktx.repository.AccountRepository;
import root.quanlyktx.service.AccountService;

@RestController
@RequestMapping("/api")
public class AccountController {
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    AccountService accountService;

    @PostMapping("/sign-in")
    String login(@RequestBody Account account){
        if(accountService.proccessLogin(account)){
            return "login success";
        }
        return "login fail";
    }


}
