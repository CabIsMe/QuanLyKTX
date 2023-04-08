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
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import root.quanlyktx.dto.PhongKTXDTO;
import root.quanlyktx.entity.HopDongKTX;
import root.quanlyktx.entity.LoaiKTX;
import root.quanlyktx.entity.PhongKTX;
import root.quanlyktx.entity.Term;
import root.quanlyktx.model.RoomDetails;
import root.quanlyktx.repository.HopDongKTXRepository;
import root.quanlyktx.repository.LoaiKTXRepository;
import root.quanlyktx.repository.PhongKTXRepository;
import root.quanlyktx.repository.TermRepository;
import java.util.ArrayList;
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
    private HopDongKTXRepository hopDongKTXRepository;
    @Autowired
    private LoaiKTXRepository loaiKTXRepository;
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
        List<PhongKTX>phongKTXList=phongKTXRepository.findAllByIdLoaiKTXAndTrangThaiTrue(id);
        return phongKTXList.stream()
                .map(phongKTX -> modelMapper.map(phongKTX, PhongKTXDTO.class))
                .collect(Collectors.toList());
    }


    public List<Integer> getAllPhongHaveStudents(boolean status) {
        List<PhongKTX> phongKTXList = phongKTXRepository.findAllByTrangThaiTrue();
        List<Integer> idPhong = new ArrayList<>();
        idPhong.add(0);
        for(PhongKTX phongKTX : phongKTXList){
            for (HopDongKTX hopDongKTX : phongKTX.getHopDongKTXList()){
                if(hopDongKTX.isTrangThai()==status){
                    idPhong.add(phongKTX.getId());
                    break;
                }
            }
        }
        return idPhong;
    }

//    public ViewInforRoom getViewInforRoom(Integer idPhongKTX){
//        PhongKTXDTO phongKTXDTO = findPhongKTXById(idPhongKTX);
//        LoaiKTX loaiKTX = phongKTXDTO.getLoaiKTX();
//        return new ViewInforRoom(phongKTXDTO.getId(),loaiKTX,hopDongKTXService.numBedEmpty(idPhongKTX));
//    }


    public Integer countHopDongInPhong(Integer idPhong) {
        Date date= new Date();
        Term term= termRepository.getByNgayMoDangKyBeforeAndNgayKetThucDangKyAfter(date,date);
        if(term == null){
            return null;
        }
        return hopDongKTXRepository.countHopDongKTXByIdPhongKTXAndIdTerm(idPhong, term.getId());
    }

    public List<RoomDetails> roomDetailsList(Integer idLoaiPhong){
        LoaiKTX loaiKTX=loaiKTXRepository.findLoaiKTXById(idLoaiPhong);
        List<PhongKTX> phongKTXDTOList=phongKTXRepository .findAllByIdLoaiKTXAndTrangThaiTrue(idLoaiPhong);
        if(loaiKTX==null || phongKTXDTOList.isEmpty()){
            System.out.println("List Room is Empty (1)");
            return null;
        }

        List<RoomDetails> roomDetailsList = new ArrayList<>();
        for (PhongKTX phongKTX: phongKTXDTOList) {

            roomDetailsList.add(new RoomDetails(phongKTX.getId(), loaiKTX.getTenLoai(),loaiKTX.getGiaPhong(),
                    loaiKTX.getSoGiuong()- countHopDongInPhong(phongKTX.getId())
                    ,loaiKTX.getImage(),loaiKTX.getDescription()));
        }
        if(roomDetailsList.isEmpty()){
            System.out.println("List Room is Empty (3)");
            return null;
        }

        return roomDetailsList;
    }
    public RoomDetails roomInfo(Integer idLoaiPhong, Integer idPhong){
        LoaiKTX loaiKTX=loaiKTXRepository.findLoaiKTXById(idLoaiPhong);
        Optional<PhongKTX> optional=phongKTXRepository.findById(idPhong);
        if(optional.isEmpty()){
            System.out.println("List Room is Empty (1)");
            return null;
        }

        PhongKTX phongKTX=optional.get();

        return new RoomDetails(phongKTX.getId(),loaiKTX.getTenLoai(),loaiKTX.getGiaPhong()
                ,loaiKTX.getSoGiuong()-countHopDongInPhong(phongKTX.getId())
                ,loaiKTX.getImage(), loaiKTX.getDescription());
    }
}
