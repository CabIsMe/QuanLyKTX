package root.quanlyktx.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import root.quanlyktx.dto.HopDongKTXDTO;
import root.quanlyktx.entity.HopDongKTX;
import root.quanlyktx.entity.PhongKTX;
import root.quanlyktx.repository.HopDongKTXRepository;

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
}
