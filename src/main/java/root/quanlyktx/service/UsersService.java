package root.quanlyktx.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import root.quanlyktx.entity.Users;
import root.quanlyktx.repository.UsersRepository;

import java.util.List;

@Service
public class UsersService {

@Autowired
UsersRepository sinhVienRepository;
    public List<Users> getAll() { return sinhVienRepository.findAll();}
    public Users findById(String MSSV) { return sinhVienRepository.findById(MSSV).get();}

}
