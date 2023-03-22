package root.quanlyktx.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
//import root.quanlyktx.entity.GiaNuocTheoThang;
import root.quanlyktx.dto.PhieuNuocKTXDTO;
import root.quanlyktx.entity.HopDongKTX;
import root.quanlyktx.entity.PhieuNuocKTX;
import root.quanlyktx.entity.PhongKTX;
import root.quanlyktx.model.ViewBills;
import root.quanlyktx.repository.GiaNuocTheoThangRepository;
import root.quanlyktx.repository.HopDongKTXRepository;
import root.quanlyktx.repository.PhieuNuocKTXRepository;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PhieuNuocKTXService {

    @Autowired
    HopDongKTXRepository hopDongKTXRepository;
    @Autowired
    PhieuNuocKTXRepository phieuNuocKTXRepository;
    @Autowired
    GiaNuocTheoThangRepository giaNuocTheoThangRepository;
    @Autowired
    ModelMapper modelMapper;


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

    public List<ViewBills> getWaterBills(String mssv){
        Optional<HopDongKTX> hopDongKTX = Optional.ofNullable(hopDongKTXRepository.findHopDongKTXByMSSVAndTrangThaiTrue(mssv));
        if(hopDongKTX.isEmpty()) return Collections.emptyList();
        Date dateStart = hopDongKTX.get().getNgayLamDon();
        Integer month = dateStart.getMonth()+1;
        Integer year = dateStart.getYear()+1900;
        List<PhieuNuocKTX> phieuNuocKTXList = phieuNuocKTXRepository.findAllByMaSoKTXAndGiaNuocTheoThang_ThangGreaterThanEqualAndGiaNuocTheoThang_NamGreaterThanEqual(hopDongKTX.get().getIdPhongKTX(),month,year);
//        List<PhieuNuocKTX> phieuNuocKTXList = hopDongKTX.get().getPhongKTX().getPhieuNuocKTXList();
        if(phieuNuocKTXList.isEmpty()) return Collections.emptyList();
        List<PhieuNuocKTXDTO> phieuNuocKTXDTOList = phieuNuocKTXList.stream().map(phieuNuocKTX -> modelMapper.map(phieuNuocKTX,PhieuNuocKTXDTO.class)).collect(Collectors.toList());
        List<Optional<PhieuNuocKTXDTO>> optionalPhieuNuocKTXDTOList = phieuNuocKTXDTOList.stream().map(phieuNuocKTXDTO -> Optional.ofNullable(phieuNuocKTXDTO)).collect(Collectors.toList());
//        Double total = phieuNuocKTXDTO.getGiaNuocTheoThang().getGiaNuoc()*phieuNuocKTXDTO;
        List<ViewBills> viewBillsList = new ArrayList<>();
        for(Optional<PhieuNuocKTXDTO> phieuNuocKTXDTO: optionalPhieuNuocKTXDTOList){
            viewBillsList.add(new ViewBills(phieuNuocKTXDTO,phieuNuocKTXDTO.get().getGiaNuocTheoThang().getGiaNuoc()*phieuNuocKTXDTO.get().getLuongNuocTieuThu()));
        }
        return viewBillsList;
    }
}
