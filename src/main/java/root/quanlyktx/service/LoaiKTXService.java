package root.quanlyktx.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import root.quanlyktx.dto.LoaiKTXDto;
import root.quanlyktx.entity.LoaiKTX;
import root.quanlyktx.repository.LoaiKTXRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LoaiKTXService {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    LoaiKTXRepository loaiKTXRepository;
    public List<LoaiKTXDto> getAll(){
        List <LoaiKTX> loaiKTXList=loaiKTXRepository.findAll();
        return loaiKTXList.stream().map(loaiKTX -> modelMapper.map(loaiKTX,LoaiKTXDto.class)).collect(Collectors.toList());
    }
    public LoaiKTXDto getSingleLoaiKTX(Integer id){
        if(loaiKTXRepository.existsById(id)){
            return modelMapper.map(loaiKTXRepository.findById(id).get(),LoaiKTXDto.class);
        }
        return null;
    }
    public String addLoaiKTX(LoaiKTXDto loaiKTXDto){
        try{
            loaiKTXRepository.save(modelMapper.map(loaiKTXDto, LoaiKTX.class));
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
    public LoaiKTXDto updateLoaiKTX(Integer id, LoaiKTXDto loaiKTXDto){
        if(loaiKTXRepository.existsById(id)){

            try{
                LoaiKTX loaiKTX_root= loaiKTXRepository.findById(id).get();
                loaiKTX_root.setGiaPhong(loaiKTXDto.getGiaPhong());
                loaiKTX_root.setSoNguoi(loaiKTXDto.getSoGiuong());
                loaiKTX_root.setTenLoai(loaiKTXDto.getTenLoai());
                loaiKTX_root.setImage(loaiKTXDto.getImage());
                loaiKTXRepository.save(loaiKTX_root);
                return modelMapper.map(loaiKTX_root, LoaiKTXDto.class);
            }
            catch (Exception e){
                e.printStackTrace();
            }

        }
        return null;
    }
}
