package root.quanlyktx.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;
import root.quanlyktx.dto.LoaiKTXDto;
import root.quanlyktx.dto.PhongKTXDTO;
import root.quanlyktx.entity.LoaiKTX;
import root.quanlyktx.entity.PhongKTX;
import root.quanlyktx.firebase.FBImageService;
import root.quanlyktx.repository.LoaiKTXRepository;
import root.quanlyktx.repository.PhongKTXRepository;
import root.quanlyktx.repository.TermRepository;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LoaiKTXService {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    LoaiKTXRepository loaiKTXRepository;
    @Autowired
    FBImageService fbImageService;
    @Autowired
    PhongKTXRepository phongKTXRepository;
    @Autowired
    TermRepository termRepository;

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
    public ResponseEntity<?> addLoaiKTX(MultipartFile file, LoaiKTXDto loaiKTXDto){
        try{
            if(!file.isEmpty()){
                String fileName = fbImageService.save(file);
                String urlFile = fbImageService.getImageUrl(fileName);
                loaiKTXDto.setImage(urlFile);
            }
            loaiKTXRepository.save(modelMapper.map(loaiKTXDto, LoaiKTX.class));
            return ResponseEntity.ok(true);
        }catch (Exception e){
            e.getStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Add loaiKTX failed");
        }
    }
    public ResponseEntity<?> deleteLoaiKTX(Integer id){
            Date date = new Date();
        if(termRepository.getByNgayMoDangKyBeforeAndNgayKetThucDangKyAfter(date, date) != null)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Date invalid");
        }
        Optional<LoaiKTX>  optional =loaiKTXRepository.findById(id);
        if(optional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("loaiKTX invalid");
        }
        List<PhongKTX> phongKTXList = phongKTXRepository.findAllByIdLoaiKTX(id);
        if(phongKTXList.size() > 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("can't delete this loaiKTX");
        }
        LoaiKTX loaiKTX= optional.get();
        loaiKTXRepository.delete(loaiKTX);
        return ResponseEntity.ok(true);

    }
    public ResponseEntity<?> updateLoaiKTX(Integer id, LoaiKTXDto loaiKTXDto) {
        Date date = new Date();
        if(termRepository.getByNgayMoDangKyBeforeAndNgayKetThucDangKyAfter(date, date) != null)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Date invalid");
        }
        Optional<LoaiKTX>  optional =loaiKTXRepository.findById(id);
        if(optional.isEmpty())
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("loaiKTX invalid");
        }
        LoaiKTX loaiKTX_root = optional.get();
        try{
                    loaiKTX_root.setGiaPhong(loaiKTXDto.getGiaPhong());
                    loaiKTX_root.setSoGiuong(loaiKTXDto.getSoGiuong());
                    loaiKTX_root.setTenLoai(loaiKTXDto.getTenLoai());
                    loaiKTX_root.setImage(loaiKTXDto.getImage());
                    loaiKTXRepository.save(loaiKTX_root);
                    return ResponseEntity.ok(true);
        } catch (Exception e) {
                    e.printStackTrace();
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("update loaiKTX failed");
        }
    }
}
