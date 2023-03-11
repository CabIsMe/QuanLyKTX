package root.quanlyktx.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import root.quanlyktx.dto.HopDongKTXDTO;
import root.quanlyktx.dto.ViewBillRoomDTO;
import root.quanlyktx.entity.HopDongKTX;
import root.quanlyktx.entity.LoaiKTX;
import root.quanlyktx.entity.PhongKTX;
import root.quanlyktx.entity.Student;
import root.quanlyktx.repository.HopDongKTXRepository;
import root.quanlyktx.repository.LoaiKTXRepository;
import root.quanlyktx.repository.PhongKTXRepository;
import root.quanlyktx.repository.StudentRepository;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HopDongKTXService {

    @Autowired
    HopDongKTXRepository hopDongKTXRepository;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    PhongKTXRepository phongKTXRepository;
    @Autowired
    LoaiKTXRepository loaiKTXRepository;
    @Autowired
    StudentRepository studentRepository;


    public List<HopDongKTXDTO> getAll() {
        List<HopDongKTX> hopDongKTXList = hopDongKTXRepository.findAll();
        return hopDongKTXList.stream().map(hopDongKTX -> modelMapper.map(hopDongKTX, HopDongKTXDTO.class)).collect(Collectors.toList());
    }

    public HopDongKTXDTO getById(Integer id) {
        HopDongKTX hopDongKTX = hopDongKTXRepository.findById(id).get();

        return modelMapper.map(hopDongKTX, HopDongKTXDTO.class);
    }

    public List<HopDongKTXDTO> getByPhongKTX(Integer idPhongKTX) {
        List<HopDongKTX> hopDongKTXList = hopDongKTXRepository.findAllByPhongKTX(idPhongKTX);
        return hopDongKTXList.stream()
                .map(hopDongKTX -> modelMapper.map(hopDongKTX, HopDongKTXDTO.class))
                .collect(Collectors.toList());
    }

    public Integer countByPhongKTX(Integer idPhong) {
        return hopDongKTXRepository.countHopDongKTXByPhongKTX(idPhong);
    }

    public Integer countByTrangThaiTrue() {
        return hopDongKTXRepository.countHopDongKTXByTrangThaiTrue();
    }

    public List<HopDongKTXDTO> xoaHopDongChuaDongPhi() {
        List<HopDongKTX> hopDongKTXList = hopDongKTXRepository.findAll();
        Date date = new java.sql.Date(new java.util.Date().getTime());
        System.out.println(date);
        List<HopDongKTXDTO> hdXoa = new ArrayList<HopDongKTXDTO>();
        for (HopDongKTX hd : hopDongKTXList) {
            // hd day occurs before date
            if (hd.getNgayHieuLuc().compareTo(date) < 0 && hd.isTrangThai() == false) {
                hdXoa.add(modelMapper.map(hd, HopDongKTXDTO.class));
                hopDongKTXRepository.delete(hd);
            }
        }

        return hdXoa;
    }

    public List<HopDongKTX> getHopDongTemp(String mssv) {

        return hopDongKTXRepository.findAllByMSSV(mssv);
    }


    public ViewBillRoomDTO getBillRoom(String mssv) {
        Student student = studentRepository.findByUsername(mssv);
        HopDongKTX hopDongKTX = hopDongKTXRepository.findFirstByMSSVOrderByNgayLamDonDesc(mssv);
        if (hopDongKTX.getNgayKetThuc().getTime() > new Date().getTime() && hopDongKTX.isTrangThai() == false) {
            return null;
        } else {
            PhongKTX phongKTX = phongKTXRepository.findPhongKTXById(hopDongKTX.getPhongKTX());
            LoaiKTX loaiKTX = loaiKTXRepository.findLoaiKTXById(phongKTX.getLoaiKTX());
            return new ViewBillRoomDTO().mapper(student, hopDongKTX, phongKTX, loaiKTX);
        }
    }

    public Integer checkAddBillRoom(String mssv) {
        HopDongKTX hopDongKTX = hopDongKTXRepository.findFirstByMSSVOrderByNgayLamDonDesc(mssv);
        LocalDate oneWeek = LocalDate.now().plusDays(7);
        if (hopDongKTX==null) return 0;
        else {
            //if status is paid, check datEnd to less than (current date plus 7 days)
            if (hopDongKTX.isTrangThai()) {
                if (hopDongKTX.getNgayHieuLuc().compareTo(Date.from(oneWeek.atStartOfDay(ZoneId.systemDefault()).toInstant())) <= 0) {
                    return 1;
                } else return 0;
            } else {
                // Room registration is not allowed if payment has not been made and the time has not expired
                if (hopDongKTX.getNgayKetThuc().compareTo(new Date()) > 0) {
                    return 0;
                }
                return 1;
            }

        }
    }

    public Integer checkNumBedEmpty(Integer phongKTX){

        return 1;
    }

    public String addBillRoom(String mssv, Integer phongKTX) {
        Integer checkAddBillRoom = checkAddBillRoom(mssv);
        if (checkAddBillRoom == 0) return "null";
        else{
            HopDongKTX hopDongKTX = new HopDongKTX();
            LocalDate paymentDueDate = LocalDate.now().plusDays(7);
            hopDongKTX.setMSSV(mssv);
            hopDongKTX.setPhongKTX(phongKTX);
            hopDongKTX.setNgayLamDon(new Date());
            hopDongKTX.setNgayKetThuc(Date.from(paymentDueDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
            hopDongKTX.setNgayHieuLuc(Date.from(paymentDueDate.plusMonths(5).atStartOfDay(ZoneId.systemDefault()).toInstant()));
            hopDongKTX.setTrangThai(false);
            try{
                hopDongKTXRepository.save(hopDongKTX);
                return "success";
            }catch (Exception e) {
                e.getStackTrace();
                return "error";
            }
        }
        //thieu check so giuong con trong khong
    }
}
