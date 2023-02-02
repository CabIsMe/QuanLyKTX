package root.quanlyktx.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
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
    public boolean deleteLoaiKTX(Integer id){
        LoaiKTX loaiKTX=loaiKTXRepository.findById(id).get();
        if(loaiKTXRepository.findById(id).isPresent()){
            loaiKTXRepository.delete(loaiKTX);
            return true;
        }
        return false;
    }
    public LoaiKTX updateLoaiKTX(Integer id, LoaiKTX loaiKTX){
        if(loaiKTXRepository.existsById(id)){
            try{
                LoaiKTX loaiKTX_root= loaiKTXRepository.findById(id).get();
                loaiKTX_root.setGiaPhong(loaiKTX.getGiaPhong());
                loaiKTX_root.setSoNguoi(loaiKTX.getSoNguoi());
                loaiKTXRepository.save(loaiKTX_root);
                return loaiKTX_root;
            }
            catch (Exception e){
                e.printStackTrace();
            }

        }
        return null;
    }
}
