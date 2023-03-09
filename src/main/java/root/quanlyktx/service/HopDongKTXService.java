package root.quanlyktx.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import root.quanlyktx.dto.HopDongKTXDTO;
import root.quanlyktx.entity.HopDongKTX;
import root.quanlyktx.repository.HopDongKTXRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HopDongKTXService {

    @Autowired
    HopDongKTXRepository hopDongKTXRepository;
    @Autowired
    private ModelMapper modelMapper;

    public List<HopDongKTXDTO> getAll(){
        List<HopDongKTX> hopDongKTXList = hopDongKTXRepository.findAll();
        return hopDongKTXList.stream().map(hopDongKTX -> modelMapper.map(hopDongKTX,HopDongKTXDTO.class)).collect(Collectors.toList());
    }

    public HopDongKTXDTO getById(Integer id){
        HopDongKTX hopDongKTX = hopDongKTXRepository.findById(id).get();

        return modelMapper.map(hopDongKTX, HopDongKTXDTO.class);
    }

    public List<HopDongKTXDTO> getByPhongKTX(Integer idPhongKTX){
        List<HopDongKTX> hopDongKTXList = hopDongKTXRepository.findAllByPhongKTX(idPhongKTX);
        return hopDongKTXList.stream()
                .map(hopDongKTX -> modelMapper.map(hopDongKTX,HopDongKTXDTO.class))
                .collect(Collectors.toList());
    }
    public Integer countByPhongKTX(Integer idPhong){
        return hopDongKTXRepository.countHopDongKTXByPhongKTX(idPhong);
    }
    public Integer countByTrangThaiTrue(){
        return hopDongKTXRepository.countHopDongKTXByTrangThaiTrue();
    }

    public List<HopDongKTXDTO> xoaHopDongChuaDongPhi(){
        List<HopDongKTX> hopDongKTXList = hopDongKTXRepository.findAll();
        Date date = new java.sql.Date(new java.util.Date().getTime());
        System.out.println(date);
        List<HopDongKTXDTO> hdXoa = new ArrayList<HopDongKTXDTO>();
        for(HopDongKTX hd : hopDongKTXList){
            // hd day occurs before date
            if (hd.getNgayHieuLuc().compareTo(date) < 0 && hd.isTrangThai() == false){
                hdXoa.add(modelMapper.map(hd, HopDongKTXDTO.class));
                hopDongKTXRepository.delete(hd);
            }
        }

        return hdXoa;
    }
    public List<HopDongKTX> getHopDongTemp(String mssv){

        return hopDongKTXRepository.findAllByMSSV(mssv);
    }

}
