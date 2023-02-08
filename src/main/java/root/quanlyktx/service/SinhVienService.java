package root.quanlyktx.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import root.quanlyktx.entity.SinhVien;
import root.quanlyktx.repository.SinhVienRepository;

import java.util.List;

@Service
public class SinhVienService {

@Autowired
    SinhVienRepository sinhVienRepository;
    public List<SinhVien> getAll() { return sinhVienRepository.findAll();}
    public SinhVien findById(String MSSV) { return sinhVienRepository.findById(MSSV).get();}

}
