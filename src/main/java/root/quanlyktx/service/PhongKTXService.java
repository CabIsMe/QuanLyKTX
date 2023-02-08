package root.quanlyktx.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import root.quanlyktx.entity.LoaiKTX;
import root.quanlyktx.entity.PhongKTX;
import root.quanlyktx.repository.PhongKTXRepository;

@Service
public class PhongKTXService {

    @Autowired
    PhongKTXRepository phongKTXRepository;
    public String addPhongKTX(PhongKTX phongKTX){
        try{
            phongKTXRepository.save(phongKTX);
            return "success";
        }catch (Exception e){
            e.getStackTrace();
            return "error";
        }
    }


    public boolean deletePhongKTX(Integer id){
        PhongKTX phongKTX=phongKTXRepository.findById(id).get();
        if(phongKTXRepository.findById(id).isPresent()){
            phongKTXRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public PhongKTX updatePhongKTX(Integer id, PhongKTX phongKTX){
        if(phongKTXRepository.existsById(id)){
            try{
                PhongKTX phongKTX_root= phongKTXRepository.findById(id).get();
                phongKTX_root.setLoaiKTX(phongKTX.getLoaiKTX());
                phongKTX_root.setHinhAnh(phongKTX.getHinhAnh());
                phongKTXRepository.save(phongKTX_root);
                return phongKTX_root;
            }
            catch (Exception e){
                e.printStackTrace();
            }

        }
        return null;
    }
}
