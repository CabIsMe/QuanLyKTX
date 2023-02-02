package root.quanlyktx.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import root.quanlyktx.entity.LoaiKTX;
import root.quanlyktx.repository.LoaiKTXRepository;

import java.util.List;

@Service
public class LoaiKTXService {
    @Autowired
    LoaiKTXRepository loaiKTXRepository;
    public List<LoaiKTX> getAll(){
        return loaiKTXRepository.findAll();
    }
    public String addLoaiKTX(LoaiKTX loaiKTX){
        try{
            loaiKTXRepository.save(loaiKTX);
            return "success";
        }catch (Exception e){
            e.getStackTrace();
            return "error";
        }
    }
}
