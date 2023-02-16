package root.quanlyktx.service;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import root.quanlyktx.dto.LoaiKTXDto;
import root.quanlyktx.dto.UserDto;

import root.quanlyktx.entity.User;
import root.quanlyktx.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;
//    @Autowired
//    BCryptPasswordEncoder bCryptPasswordEncoder;


     public List<UserDto> getAll(){
         List <User> userList=userRepository.findAll();
         return userList.stream()
                 .map(user -> modelMapper
                 .map(user, UserDto.class))
                 .collect(Collectors.toList());
     }
}
