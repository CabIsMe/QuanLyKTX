package root.quanlyktx.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import root.quanlyktx.dto.HopDongKTXDTO;
import root.quanlyktx.dto.LoaiKTXDto;
import root.quanlyktx.dto.PhongKTXDTO;
import root.quanlyktx.entity.*;
import root.quanlyktx.model.ThongTinPhong;
import root.quanlyktx.model.ViewBillRoom;
import root.quanlyktx.repository.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HopDongKTXService {

    @Autowired
    private HopDongKTXRepository hopDongKTXRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PhongKTXRepository phongKTXRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private MapperToDTOService mapperToDTOService;
    @Autowired
    private LoaiKTXService loaiKTXService;
    @Autowired
    private LoaiKTXRepository loaiKTXRepository;
    @Autowired
    private TermRepository termRepository;

    public List<HopDongKTXDTO> getAll() {
        List<HopDongKTX> hopDongKTXList = hopDongKTXRepository.findAll();
        return hopDongKTXList.stream().map(hopDongKTX -> modelMapper.map(hopDongKTX, HopDongKTXDTO.class)).collect(Collectors.toList());
    }

    public HopDongKTXDTO getById(Integer id) {
        HopDongKTX hopDongKTX = hopDongKTXRepository.findById(id).get();

        return modelMapper.map(hopDongKTX, HopDongKTXDTO.class);
    }

    public List<HopDongKTXDTO> getByPhongKTX(Integer idPhongKTX) {
        List<HopDongKTX> hopDongKTXList = hopDongKTXRepository.findAllByIdPhongKTX(idPhongKTX);
        return hopDongKTXList.stream()
                .map(hopDongKTX -> modelMapper.map(hopDongKTX, HopDongKTXDTO.class))
                .collect(Collectors.toList());
    }

    public Integer countHopDongInPhong(Integer idPhong) {
        Date date= new Date();
        Term term= termRepository.getByNgayMoDangKyBeforeAndNgayKetThucDangKyAfter(date,date);
        return hopDongKTXRepository.countHopDongKTXByIdPhongKTXAndTerm_Id(idPhong, term.getId());
    }
    public List<ThongTinPhong> thongTinPhongs(Integer idLoaiPhong){
        LoaiKTX loaiKTX=loaiKTXRepository.findLoaiKTXById(idLoaiPhong);
        List<PhongKTX> phongKTXDTOList=phongKTXRepository .findAllByIdLoaiKTX(idLoaiPhong);

        List<ThongTinPhong> thongTinPhongList= new ArrayList<>();
        for (PhongKTX phongKTX: phongKTXDTOList) {

            thongTinPhongList.add(new ThongTinPhong(phongKTX.getId(),loaiKTX.getGiaPhong(),
                    loaiKTX.getSoGiuong()- countHopDongInPhong(phongKTX.getId())
                    ,loaiKTX.getImage()));
        }
        return thongTinPhongList;
    }

//    public Integer countByTrangThaiTrue() {
//        return hopDongKTXRepository.countHopDongKTXByTrangThaiTrue();
//    }



    public List<HopDongKTX> getHopDongTemp(String mssv) {

        return hopDongKTXRepository.findAllByMSSV(mssv);
    }


//    public ViewBillRoom getBillRoom(String mssv) {
//        Student student = studentRepository.findByUsername(mssv);
//        HopDongKTX hopDongKTX = hopDongKTXRepository.findFirstByMSSVOrderByNgayLamDonDesc(mssv);
//        HopDongKTXDTO hopDongKTXDTO = modelMapper.map(hopDongKTX,HopDongKTXDTO.class);
//        if (hopDongKTX.getNgayKetThuc().getTime() > new Date().getTime() && hopDongKTX.isTrangThai() == false) {
//            return null;
//        } else {
//            LoaiKTXDto loaiKTXDto = hopDongKTXDTO.getPhongKTX().getLoaiKTX();
//            return new ViewBillRoom(hopDongKTXDTO,loaiKTXDto,student.getHoTen(),student.getSDT(),loaiKTXDto.getGiaPhong()*5);
//        }
//    }

//    public Integer checkAddBillRoom(String mssv) {
//        HopDongKTX hopDongKTX = hopDongKTXRepository.findFirstByMSSVOrderByNgayLamDonDesc(mssv);
//        if (hopDongKTX==null) return 1;
//        else {
//            LocalDate oneWeek = LocalDate.now().plusDays(7);
//            //if status is paid, check datEnd to less than (current date plus 7 days)
//            if (hopDongKTX.isTrangThai()) {
//                if (hopDongKTX.getNgayHieuLuc().compareTo(Date.from(oneWeek.atStartOfDay(ZoneId.systemDefault()).toInstant())) <= 0) {
//                    return 1;
//                } else return 0;
//            } else {
//                // Room registration is not allowed if payment has not been made and the time has not expired
//                if (hopDongKTX.getNgayKetThuc().compareTo(new Date()) > 0) {
//                    return 0;
//                }
//                return 1;
//            }
//
//        }
//    }


//    public Integer numBedEmpty(Integer phongKTX){
//        return   hopDongKTXRepository.countHopDongKTXByIdPhongKTXAndNgayKetThucAfter(phongKTX,new Date());
////                +hopDongKTXRepository.countHopDongKTXByIdPhongKTXAndTrangThaiTrue(phongKTX);
//    }

//    public Integer checkNumBedEmpty(Integer phongKTX,Integer numBed){
//        Integer numBedEmpty = numBedEmpty(phongKTX);
//        if(numBedEmpty > numBed) return 0;
//        return 1;
//    }

//    public String addBillRoom(String mssv, Integer phongKTX,Integer numBed) {
//        Integer checkAddBillRoom = checkAddBillRoom(mssv);
//        Integer checkNumBed = checkNumBedEmpty(phongKTX,numBed);
//        if (checkAddBillRoom == 0 || checkNumBed == 0) return "null";
//        else{
//            LocalDate paymentDueDate = LocalDate.now().plusDays(7);
//
//            HopDongKTX hopDongKTX = new HopDongKTX(phongKTX,mssv,new Date(),Date.from(paymentDueDate.atStartOfDay(ZoneId.systemDefault()).toInstant()),Date.from(paymentDueDate.plusMonths(5).atStartOfDay(ZoneId.systemDefault()).toInstant()),false);
////            hopDongKTX.setMSSV(mssv);
////            hopDongKTX.setIdPhongKTX(phongKTX);
////            hopDongKTX.setNgayLamDon(new Date());
////            hopDongKTX.setNgayKetThuc(Date.from(paymentDueDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
////            hopDongKTX.setNgayHieuLuc(Date.from(paymentDueDate.plusMonths(5).atStartOfDay(ZoneId.systemDefault()).toInstant()));
////            hopDongKTX.setTrangThai(false);
//            try{
//                hopDongKTXRepository.save(hopDongKTX);
//                return "success";
//            }catch (Exception e) {
//                e.getStackTrace();
//                return "error";
//            }
//        }
//    }

//    public String addBillRoom(HopDongKTXDTO hopDongKTXDTO) {
//        System.out.println(hopDongKTXDTO.getIdPhongKTX());
//        System.out.println(hopDongKTXDTO.getMSSV());
//        Integer checkAddBillRoom = checkAddBillRoom(hopDongKTXDTO.getMSSV());
//        Integer checkNumBed = checkNumBedEmpty(hopDongKTXDTO.getIdPhongKTX(),phongKTXRepository.findPhongKTXById(hopDongKTXDTO.getId()).getLoaiKTX().getSoGiuong());
//        if (checkAddBillRoom == 0 || checkNumBed == 0) return "null";
//        else{
//            LocalDate paymentDueDate = LocalDate.now().plusDays(7);
//
//            HopDongKTX hopDongKTX = new HopDongKTX(hopDongKTXDTO.getIdPhongKTX(),hopDongKTXDTO.getMSSV(),new Date(),Date.from(paymentDueDate.atStartOfDay(ZoneId.systemDefault()).toInstant()),Date.from(paymentDueDate.plusMonths(5).atStartOfDay(ZoneId.systemDefault()).toInstant()),false);
////            hopDongKTX.setMSSV(mssv);
////            hopDongKTX.setIdPhongKTX(phongKTX);
////            hopDongKTX.setNgayLamDon(new Date());
////            hopDongKTX.setNgayKetThuc(Date.from(paymentDueDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
////            hopDongKTX.setNgayHieuLuc(Date.from(paymentDueDate.plusMonths(5).atStartOfDay(ZoneId.systemDefault()).toInstant()));
////            hopDongKTX.setTrangThai(false);
//            try{
//                hopDongKTXRepository.save(hopDongKTX);
//                return "success";
//            }catch (Exception e) {
//                e.getStackTrace();
//                return "error";
//            }
//        }
//    }


    public String deleteBillRoom(Integer idBillRoom) {
        HopDongKTX hopDongKTX = hopDongKTXRepository.getHopDongKTXById(idBillRoom);
        try {
            hopDongKTXRepository.delete(hopDongKTX);
            return "success";
        }catch (Exception e){
            e.getStackTrace();
            return "error";
        }
    }
}
