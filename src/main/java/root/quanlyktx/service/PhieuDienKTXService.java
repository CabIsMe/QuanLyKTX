package root.quanlyktx.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import root.quanlyktx.dto.PhieuDienKTXDTO;
import root.quanlyktx.dto.PhieuNuocKTXDTO;
import root.quanlyktx.entity.HopDongKTX;
import root.quanlyktx.entity.PhieuDienKTX;
import root.quanlyktx.entity.PhieuNuocKTX;
import root.quanlyktx.model.ViewBills;
import root.quanlyktx.repository.HopDongKTXRepository;
import root.quanlyktx.repository.PhieuDienKTXRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PhieuDienKTXService {
    @Autowired
    HopDongKTXRepository hopDongKTXRepository;
    @Autowired
    PhieuDienKTXRepository phieuDienKTXRepository;
    @Autowired
    ModelMapper modelMapper;

    public List<ViewBills> getElectricBills(String mssv){
        Optional<HopDongKTX> hopDongKTX = Optional.ofNullable(hopDongKTXRepository.findHopDongKTXByMSSVAndTrangThaiTrue(mssv));
        if(hopDongKTX.isEmpty()) return Collections.emptyList();
        Date dateStart = hopDongKTX.get().getNgayLamDon();
        Integer month = dateStart.getMonth()+1;
        Integer year = dateStart.getYear()+1900;
                List<PhieuDienKTX> phieuDienKTXList = phieuDienKTXRepository.findAllByMaSoKTXAndGiaDienTheoThang_ThangGreaterThanEqualAndGiaDienTheoThang_NamGreaterThanEqual(hopDongKTX.get().getIdPhongKTX(),month,year);
//        List<PhieuDienKTX> phieuDienKTXList = hopDongKTX.get().getPhongKTX().getPhieuDienKTXList();
        if(phieuDienKTXList.isEmpty()) return Collections.emptyList();
        List<PhieuDienKTXDTO> phieuDienKTXDTOList = phieuDienKTXList.stream().map(phieuDienKTX -> modelMapper.map(phieuDienKTX, PhieuDienKTXDTO.class)).collect(Collectors.toList());
        List<Optional<PhieuDienKTXDTO>> optionalPhieuDienKTXDTOList = phieuDienKTXDTOList.stream().map(phieuDienKTXDTO -> Optional.ofNullable(phieuDienKTXDTO)).collect(Collectors.toList());
//        Double total = phieuNuocKTXDTO.getGiaNuocTheoThang().getGiaNuoc()*phieuNuocKTXDTO;
//        optionalPhieuNuocKTXDTOList.get()
        List<ViewBills> viewBillsList = new ArrayList<>();
        for(Optional<PhieuDienKTXDTO> phieuDienKTXDTO: optionalPhieuDienKTXDTOList){
            viewBillsList.add(new ViewBills(phieuDienKTXDTO,phieuDienKTXDTO.get().getGiaDienTheoThang().getGiaDien()*phieuDienKTXDTO.get().getSoDienTieuThu()));
        }
        return viewBillsList;
    }
}

