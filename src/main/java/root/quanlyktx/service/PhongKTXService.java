package root.quanlyktx.service;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import root.quanlyktx.dto.HopDongKTXDTO;
import root.quanlyktx.dto.LoaiKTXDto;
import root.quanlyktx.dto.PhongKTXDTO;

import root.quanlyktx.entity.LoaiKTX;
import root.quanlyktx.entity.PhongKTX;
import root.quanlyktx.entity.Term;
import root.quanlyktx.entity.HopDongKTX;
import root.quanlyktx.model.CurrentInfoRoom;
import root.quanlyktx.model.QuickSort;
import root.quanlyktx.model.RoomDetails;
import root.quanlyktx.repository.HopDongKTXRepository;
import root.quanlyktx.repository.LoaiKTXRepository;
import root.quanlyktx.repository.PhongKTXRepository;
import root.quanlyktx.repository.TermRepository;

import java.util.*;
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

    private static final Logger logger = LoggerFactory.getLogger(PhongKTXService.class);

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

    boolean checkExistRoomInContract(Integer idPhong){
        Date date = new Date();
        // ngayMoDangKy <= date register <= ngayKetThucDangKy
        // date != date register
        Term term= termRepository.getByNgayMoDangKyBeforeAndNgayKetThucDangKyAfter(date,date);
        if(term!=null){
            return true;
        }
        else{
            return hopDongKTXRepository.existsByIdTermAndIdPhongKTX(termRepository.getCurrentTerm(), idPhong);
        }
    }

    public ResponseEntity<?> deletePhongKTX(Integer id){
        if(checkExistRoomInContract(id))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Unable to perform corrections at this time");
        Optional<PhongKTX> optional = phongKTXRepository.findById(id);
        if(optional.isEmpty())
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("PhongKTX invalid");
        }
        PhongKTX phongKTX = optional.get();
                phongKTX.setTrangThai(!phongKTX.isTrangThai());
                phongKTXRepository.save(phongKTX);
                return ResponseEntity.ok(true);
    }

    public ResponseEntity<?> updatePhongKTX(Integer id, Integer idLoaiKTX){
        if(checkExistRoomInContract(id))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Unable to perform corrections at this time");
        Optional<PhongKTX> optional = phongKTXRepository.findById(id);
        if(optional.isEmpty())
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("PhongKTX invalid");
        }

                try{
                    PhongKTX phongKTX_root= optional.get();
                    phongKTX_root.setIdLoaiKTX(idLoaiKTX);
                    phongKTXRepository.save(phongKTX_root);
                    return ResponseEntity.ok(true);
                }
                catch (Exception e){
                    e.printStackTrace();
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("update phongKTX failed");
                }
    }

    Integer amountContractRoom(Integer idRoom){
        Date date=new Date();
        Term term= termRepository.getByNgayMoDangKyBeforeAndNgayKetThucDangKyAfter(date, date);
        if (term == null) {
            term = termRepository.getByNgayKetThucDangKyBeforeAndNgayKetThucAfter(date, date);
        }
        return hopDongKTXRepository.countHopDongKTXByIdPhongKTXAndIdTerm(idRoom, term.getId());

    }
    public List<CurrentInfoRoom> getALL(boolean status, Boolean sortByType){
        List<CurrentInfoRoom> infoRooms=new ArrayList<>();
        List<PhongKTX> phongKTXList = phongKTXRepository.findAllByTrangThai(status);
        if(sortByType){
            phongKTXList= phongKTXList.stream().sorted(Comparator.comparing(PhongKTX::getIdLoaiKTX))
                    .collect(Collectors.toList());
        }
        for(PhongKTX phongKTX: phongKTXList){
            infoRooms.add(new CurrentInfoRoom(modelMapper.map(phongKTX, PhongKTXDTO.class), amountContractRoom(phongKTX.getId())));
        }

        return infoRooms;
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


    public List<PhongKTXDTO> getAllPhongHaveStudents(boolean status) {
        List<PhongKTX> phongKTXList = phongKTXRepository.findAllByTrangThaiTrue();
        List<PhongKTXDTO> phongKTXDTOList= new ArrayList<>();
//        idPhong.add(0);
        for(PhongKTX phongKTX : phongKTXList){
            for (HopDongKTX hopDongKTX : phongKTX.getHopDongKTXList()){
                if(hopDongKTX.isTrangThai()==status){
                    phongKTXDTOList.add(modelMapper.map(phongKTX, PhongKTXDTO.class));
                    break;
                }
            }

        }

        return phongKTXDTOList;
    }

//    public ViewInforRoom getViewInforRoom(Integer idPhongKTX){
//        PhongKTXDTO phongKTXDTO = findPhongKTXById(idPhongKTX);
//        LoaiKTX loaiKTX = phongKTXDTO.getLoaiKTX();
//        return new ViewInforRoom(phongKTXDTO.getId(),loaiKTX,hopDongKTXService.numBedEmpty(idPhongKTX));
//    }



    public Integer countContractRoom(Integer idPhong) {
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
            logger.error("List Room is Empty (1)");
            return null;
        }

        List<RoomDetails> roomDetailsList = new ArrayList<>();
        try{
            for (PhongKTX phongKTX: phongKTXDTOList) {
                roomDetailsList.add(new RoomDetails(phongKTX.getId(), loaiKTX.getTenLoai(),loaiKTX.getGiaPhong(),
                        loaiKTX.getSoGiuong()- countContractRoom(phongKTX.getId())
                        ,loaiKTX.getImage(),loaiKTX.getDescription()));
            }
        }catch (Exception e){
            logger.error("Out of viewable time");
            return null;
        }
        if(roomDetailsList.isEmpty()){
            logger.error("List Room is Empty (3)");
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
                ,loaiKTX.getSoGiuong()- countContractRoom(phongKTX.getId())
                ,loaiKTX.getImage(), loaiKTX.getDescription());
    }


}
