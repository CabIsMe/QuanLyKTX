package root.quanlyktx.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import root.quanlyktx.dto.PhongKTXDTO;

import root.quanlyktx.entity.LoaiKTX;
import root.quanlyktx.entity.PhongKTX;
import root.quanlyktx.entity.Term;
import root.quanlyktx.repository.LoaiKTXRepository;
import root.quanlyktx.repository.PhongKTXRepository;
import root.quanlyktx.repository.TermRepository;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PhongKTXService {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    PhongKTXRepository phongKTXRepository;
    @Autowired
    HopDongKTXService hopDongKTXService;
    @Autowired
    LoaiKTXRepository loaiKTXRepository;
    @Autowired
    StudentService studentService;
    @Autowired
    TermRepository termRepository;

    public ResponseEntity<?> addPhongKTX(PhongKTXDTO phongKTXDTO){
        Optional<LoaiKTX> optional = loaiKTXRepository.findById(phongKTXDTO.getIdLoaiKTX());
        if(optional.isEmpty())
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("loaiKTX invalid");
        }
            try{
                phongKTXRepository.save(modelMapper.map(phongKTXDTO, PhongKTX.class));
            return ResponseEntity.ok(true);
            }catch (Exception e){
                e.getStackTrace();
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Add phongktx fialed");
            }
    }


    public ResponseEntity<?> deletePhongKTX(Integer id){
        Date date = new Date();
        if(termRepository.getByNgayMoDangKyBeforeAndNgayKetThucDangKyAfter(date, date) != null)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Date invalid");
        }
        Optional<PhongKTX> optional = phongKTXRepository.findById(id);
        if(optional.isEmpty())
        {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("PhongKTX invalid");
        }
        PhongKTX phongKTX = optional.get();
                phongKTX.setTrangThai(false);
                phongKTXRepository.save(phongKTX);
                return ResponseEntity.ok(true);
    }

    public ResponseEntity<?> updatePhongKTX(Integer id, PhongKTXDTO phongKTXDTO){
        Date date = new Date();
        if(termRepository.getByNgayMoDangKyBeforeAndNgayKetThucDangKyAfter(date, date) != null)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Date invalid");
        }
        Optional<PhongKTX> optional = phongKTXRepository.findById(id);
        if(optional.isEmpty())
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("PhongKTX invalid");
        }

                try{
                    PhongKTX phongKTX_root= optional.get();
                    phongKTX_root.setIdLoaiKTX(phongKTXDTO.getIdLoaiKTX());
                    phongKTX_root.setTrangThai(phongKTXDTO.getTrangThai());
                    //     phongKTX_root.setHinhAnh(phongKTX.getHinhAnh());
                    phongKTXRepository.save(phongKTX_root);
                    return ResponseEntity.ok(true);
                }
                catch (Exception e){
                    e.printStackTrace();
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("update phongKTX failed");
                }
    }


    public List<PhongKTXDTO> getALL(){
        List<PhongKTX> phongKTXList = phongKTXRepository.findAll();
        return phongKTXList.stream().map(phongKTX -> modelMapper.map(phongKTX, PhongKTXDTO.class)).collect(Collectors.toList());
    }

    public PhongKTXDTO getById(Integer id){
        PhongKTX phongKTX = phongKTXRepository.findById(id).get();
        return modelMapper.map(phongKTX, PhongKTXDTO.class);
    }
    public List<PhongKTXDTO> getAllByLoaiPhong(Integer id){
        List<PhongKTX>phongKTXList=phongKTXRepository.findAllByIdLoaiKTX(id);
        return phongKTXList.stream()
                .map(phongKTX -> modelMapper.map(phongKTX, PhongKTXDTO.class))
                .collect(Collectors.toList());
    }


//    public ViewInforRoom getViewInforRoom(Integer idPhongKTX){
//        PhongKTXDTO phongKTXDTO = findPhongKTXById(idPhongKTX);
//        LoaiKTX loaiKTX = phongKTXDTO.getLoaiKTX();
//        return new ViewInforRoom(phongKTXDTO.getId(),loaiKTX,hopDongKTXService.numBedEmpty(idPhongKTX));
//    }


}
