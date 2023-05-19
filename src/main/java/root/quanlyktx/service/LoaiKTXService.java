package root.quanlyktx.service;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import root.quanlyktx.dto.LoaiKTXDto;
import root.quanlyktx.entity.LoaiKTX;
import root.quanlyktx.entity.PhongKTX;
import root.quanlyktx.entity.Student;
import root.quanlyktx.firebase.FBImageService;
import root.quanlyktx.repository.*;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
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
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    HopDongKTXRepository hopDongKTXRepository;

    private static final Logger logger = LoggerFactory.getLogger(LoaiKTXService.class);

    public static String getUsernameFromSecurityContextHolder(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication.getName().equals("anonymousUser")){
            logger.error("Is not authenticated");
            return null;
        }

        return authentication.getName();
    }


    public List<LoaiKTXDto> getAll(){
        List <LoaiKTX> loaiKTXList=loaiKTXRepository.findAll();

        return loaiKTXList.stream().map(loaiKTX -> modelMapper.map(loaiKTX,LoaiKTXDto.class)).collect(Collectors.toList());
    }


    public ResponseEntity<?> getAllListLoaiKTXGender() {
        String mssv = getUsernameFromSecurityContextHolder();
        if (mssv==null) return ResponseEntity.status(HttpStatus.FORBIDDEN).body("unauthorization");
        Student student = studentRepository.findByUsername(mssv);
        List<LoaiKTX> loaiKTXList = loaiKTXRepository.findAllByGioiTinh(student.isGioiTinh());
        return ResponseEntity.ok(loaiKTXList.stream().map(loaiKTX -> modelMapper.map(loaiKTX,LoaiKTXDto.class)).collect(Collectors.toList()));
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
            LoaiKTX loaiKTX=modelMapper.map(loaiKTXDto, LoaiKTX.class);
            loaiKTXRepository.save(loaiKTX);
            return ResponseEntity.ok(true);
        }catch (Exception e){
            e.getStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Add loaiKTX failed");
        }
    }
    public ResponseEntity<?> deleteLoaiKTX(Integer id){
        Date date= new Date();
        if(termRepository.existsByNgayMoDangKyBeforeAndNgayKetThucDangKyAfter(date,date)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Unable to delete at this time");
        }
        Optional<LoaiKTX>  optional =loaiKTXRepository.findById(id);
        if(optional.isEmpty())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ID not found");

        LoaiKTX loaiKTX= optional.get();
        try {
            loaiKTXRepository.delete(loaiKTX);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("It already contains room");
        }
        return ResponseEntity.ok("Delete type room successfully!");

    }

    boolean checkEditTypeRoom(Integer idType){
        Date date= new Date();
        // Khác null => Đang trong khoảng thời gian mở đk, không sửa xóa
        if(termRepository.getByNgayMoDangKyBeforeAndNgayKetThucDangKyAfter(date, date) != null)
        {
            return true;
        }
        List<PhongKTX> phongKTXList= phongKTXRepository.findAllByIdLoaiKTX(idType);
        Integer idTerm= termRepository.getCurrentTerm();
        for(PhongKTX phongKTX: phongKTXList){
            // Tồn tại phòng trong hđ rồi, không sửa xóa loại phòng
            if(hopDongKTXRepository.existsByIdTermAndIdPhongKTX(idTerm, phongKTX.getId())){
                return true;
            }
        }
        return false;
    }

    public ResponseEntity<?> updateLoaiKTX(Integer id, MultipartFile file, LoaiKTXDto loaiKTXDto) {
        if(checkEditTypeRoom(id)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Unable to update at this time");
        }
        Optional<LoaiKTX>  optional =loaiKTXRepository.findById(id);
        if(optional.isEmpty())
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ID not found");
        }
        LoaiKTX loaiKTX_root = optional.get();
        try{
            loaiKTX_root.setGiaPhong(loaiKTXDto.getGiaPhong());
            loaiKTX_root.setSoGiuong(loaiKTXDto.getSoGiuong());
            loaiKTX_root.setTenLoai(loaiKTXDto.getTenLoai());
            loaiKTX_root.setDescription(loaiKTXDto.getDescription());
            if(file!= null){
                String fileName = fbImageService.save(file);
                String urlFile = fbImageService.getImageUrl(fileName);
                loaiKTX_root.setImage(urlFile);
            }
            loaiKTXRepository.save(loaiKTX_root);
            return ResponseEntity.ok("Update type room successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Update type room failed");
        }
    }


}
