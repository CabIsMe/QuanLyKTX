package root.quanlyktx.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
//import root.quanlyktx.entity.GiaNuocTheoThang;
import root.quanlyktx.entity.PhieuNuocKTX;
import root.quanlyktx.entity.PhongKTX;
import root.quanlyktx.repository.GiaNuocTheoThangRepository;
import root.quanlyktx.repository.PhieuNuocKTXRepository;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class PhieuNuocKTXService {

    @Autowired
    PhieuNuocKTXRepository phieuNuocKTXRepository;
    GiaNuocTheoThangRepository giaNuocTheoThangRepository;
    public List<PhieuNuocKTX> getAll(){return phieuNuocKTXRepository.findAll();}
    public PhieuNuocKTX findById(Integer id){ return phieuNuocKTXRepository.findById(id).get();}
    public String addPhieuNuocKTX(PhieuNuocKTX phieuNuocKTX){
        try{
            int idDonGia;
            int month = LocalDate.now().getMonth().getValue();
            int year = LocalDate.now().getYear();
            if(month == 1){
                idDonGia = 120000+ year-1;
            }else{
                idDonGia = (month-1)*10000+year;
            }

            phieuNuocKTX.setGiaNuocTheoThang(giaNuocTheoThangRepository.findById(idDonGia).get()
            );
            phieuNuocKTX.setTrangThai(false);
            phieuNuocKTXRepository.save(phieuNuocKTX);
            return "success";
        }catch (Exception e){
            e.getStackTrace();
            return "error";
        }
    }
    public PhieuNuocKTX updatePhieuNuocKTX(Integer id, PhieuNuocKTX phieuNuocKTX){
        if(phieuNuocKTXRepository.existsById(id)){
            try{
                PhieuNuocKTX phieuNuocKTX_root = phieuNuocKTXRepository.findById(id).get();
                phieuNuocKTX_root.setLuongNuocTieuThu(phieuNuocKTX.getLuongNuocTieuThu());
                phieuNuocKTX_root.setTrangThai(phieuNuocKTX.isTrangThai());
                phieuNuocKTXRepository.save(phieuNuocKTX_root);
                return phieuNuocKTX_root;
            }
            catch (Exception e){
                e.printStackTrace();
            }

        }
        return null;
    }
}
