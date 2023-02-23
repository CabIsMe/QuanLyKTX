package root.quanlyktx.service;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import root.quanlyktx.dto.LoaiKTXDto;
import root.quanlyktx.dto.UserDto;

import root.quanlyktx.entity.HandleUserDetail;
import root.quanlyktx.entity.LoaiKTX;
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

    public UserDto getInfo(String username){

//        HandleUserDetail userDetails =
//                (HandleUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User user=userRepository.findByUsername(username);
            return modelMapper.map(user, UserDto.class);



    }
     public List<UserDto> getAll(){
         List <User> userList=userRepository.findAll();
         return userList.stream()
                 .map(user -> modelMapper
                 .map(user, UserDto.class))
                 .collect(Collectors.toList());
     }

     public UserDto updateSinhVien(String MSSV, UserDto userDto){

         if (userRepository.existsById(MSSV)){
             try{
                 User user_root= userRepository.findById(MSSV).get();
                 user_root.setHoTen(userDto.getHoTen());
                 user_root.setGioiTinh(userDto.isGioiTinh());
                 user_root.setNgaySinh(userDto.getNgaySinh());
                 user_root.setCMND(userDto.getCMND());
                 user_root.setSDT(userDto.getSDT());
                 user_root.setMail(userDto.getMail());
                 userRepository.save(user_root);
                 return modelMapper.map(user_root, UserDto.class);
             }
             catch (Exception e){
                 e.printStackTrace();
             }
         }
         return null;
     }
}
