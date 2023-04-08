package root.quanlyktx.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import root.quanlyktx.dto.PhongKTXDTO;
import root.quanlyktx.entity.LoaiKTX;
import root.quanlyktx.entity.PhongKTX;
import root.quanlyktx.entity.Term;
import root.quanlyktx.model.RoomDetails;
import root.quanlyktx.repository.HopDongKTXRepository;
import root.quanlyktx.repository.LoaiKTXRepository;
import root.quanlyktx.repository.PhongKTXRepository;
import root.quanlyktx.repository.TermRepository;

import javax.annotation.PostConstruct;
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
    private TermRepository termRepository;


    public String addPhongKTX(PhongKTXDTO phongKTXDTO){

        try{
            phongKTXRepository.save(modelMapper.map(phongKTXDTO, PhongKTX.class));
            return "success";
        }catch (Exception e){
            e.getStackTrace();
            return "error";
        }
    }


    public boolean deletePhongKTX(Integer id){
        PhongKTX phongKTX=phongKTXRepository.findById(id).get();
        if(phongKTXRepository.findById(id).isPresent()){
            phongKTXRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public PhongKTXDTO updatePhongKTX(Integer id, PhongKTXDTO phongKTXDTO){
        if(phongKTXRepository.existsById(id)){
            try{
                PhongKTX phongKTX_root= phongKTXRepository.findById(id).get();
                phongKTX_root.setIdLoaiKTX(phongKTXDTO.getIdLoaiKTX());
           //     phongKTX_root.setHinhAnh(phongKTX.getHinhAnh());
                phongKTXRepository.save(phongKTX_root);
                return modelMapper.map(phongKTX_root, PhongKTXDTO.class);
            }
            catch (Exception e){
                e.printStackTrace();
            }

        }
        return null;
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

    private Integer countHopDongInPhong(Integer idPhong) {
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
