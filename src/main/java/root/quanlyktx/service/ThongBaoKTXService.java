package root.quanlyktx.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import root.quanlyktx.dto.LoaiKTXDto;
import root.quanlyktx.dto.ThongBaoKTXDTO;
import root.quanlyktx.entity.LoaiKTX;
import root.quanlyktx.entity.PhongKTX;
import root.quanlyktx.entity.ThongBaoKTX;
import root.quanlyktx.firebase.FBImageService;
import root.quanlyktx.repository.ThongBaoKTXRepository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ThongBaoKTXService {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    ThongBaoKTXRepository thongBaoKTXRepository;
    @Autowired
    FBImageService fbImageService;


    public List<ThongBaoKTXDTO> getAll(){
        List <ThongBaoKTX> thongBaoKTXList=thongBaoKTXRepository.findAll();
        return thongBaoKTXList.stream().map(thongBaoKTX -> modelMapper.map(thongBaoKTX,ThongBaoKTXDTO.class)).collect(Collectors.toList());
    }
    public ThongBaoKTXDTO getSingleThongBaoKTX(Integer id){
        if(thongBaoKTXRepository.existsById(id)){
            return modelMapper.map(thongBaoKTXRepository.findById(id).get(),ThongBaoKTXDTO.class);
        }
        return null;
    }

    public ResponseEntity<?> addThongBaoKTX(MultipartFile file, ThongBaoKTXDTO thongBaoKTXDTO){
        try{
            if(!file.isEmpty()){
                String fileName = fbImageService.save(file);
                String urlFile = fbImageService.getImageUrl(fileName);
                thongBaoKTXDTO.setHinhAnh(urlFile);
            }
            long millis=System.currentTimeMillis();
            Date date = new Date(millis);
            thongBaoKTXDTO.setThoiGian(date);
            thongBaoKTXRepository.save(modelMapper.map(thongBaoKTXDTO, ThongBaoKTX.class));
            return ResponseEntity.ok(true);
        }catch (Exception e){
            e.getStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Add ThongbaoKTX failed");
        }
    }
    public ResponseEntity<?> deleteThongBaoKTX(Integer id){
        Optional<ThongBaoKTX> optional =thongBaoKTXRepository.findById(id);
        if(optional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ThongBaoKTX invalid");
        }

        ThongBaoKTX thongBaoKTX= optional.get();
        thongBaoKTXRepository.delete(thongBaoKTX);
        return ResponseEntity.ok(true);

    }
    public ResponseEntity<?> updateThongBaoKTX(Integer id,MultipartFile file ,ThongBaoKTXDTO thongBaoKTXDTO) {

        Optional<ThongBaoKTX> optional =thongBaoKTXRepository.findById(id);
        if(optional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ThongBaoKTX invalid");
        }
        ThongBaoKTX thongBaoKTX_root = optional.get();
        try{
            if(!file.isEmpty()){
                String fileName = fbImageService.save(file);
                String urlFile = fbImageService.getImageUrl(fileName);
                thongBaoKTX_root.setHinhAnh(urlFile);
            }
            thongBaoKTX_root.setTieuDe(thongBaoKTXDTO.getTieuDe());
            thongBaoKTX_root.setNoiDung(thongBaoKTXDTO.getNoiDung());
            thongBaoKTX_root.setNguoiTao(thongBaoKTXDTO.getNguoiTao());
            thongBaoKTX_root.setThoiGian(thongBaoKTXDTO.getThoiGian());


            thongBaoKTXRepository.save(thongBaoKTX_root);
            return ResponseEntity.ok(true);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("update ThongBaoKTX failed");
        }
    }
}
