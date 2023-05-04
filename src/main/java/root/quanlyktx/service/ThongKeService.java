package root.quanlyktx.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import root.quanlyktx.entity.HopDongKTX;
import root.quanlyktx.entity.PhongKTX;
import root.quanlyktx.entity.Term;
import root.quanlyktx.model.*;
import root.quanlyktx.repository.HopDongKTXRepository;
import root.quanlyktx.repository.PhongKTXRepository;
import root.quanlyktx.repository.TermRepository;

import java.time.LocalDate;
import java.util.*;

@Service
public class ThongKeService {
    @Autowired
    HopDongKTXRepository hopDongKTXRepository;
    @Autowired
    TermRepository termRepository;
    @Autowired
    PhongKTXRepository phongKTXRepository;

    public ResponseEntity<?> getAmountStudentInTerm() {
        LocalDate curDate = LocalDate.now();
        int year = curDate.getYear();
        List<Term> terms = termRepository.findTermsByYear(year);
        if (terms.isEmpty()) return ResponseEntity.status(HttpStatus.NO_CONTENT).body("not found");
        List<amountStudentInTerm> studentInTerms = new ArrayList<>();
        terms.forEach(term -> {
            Integer amountStudent = hopDongKTXRepository.countHopDongKTXByTrangThaiTrueAndTerm_Id(term.getId());
            studentInTerms.add(new amountStudentInTerm(amountStudent,term.getNgayKetThucDangKy(),term.getNgayKetThuc()));
        });

        return ResponseEntity.ok(studentInTerms);
    }

    public ResponseEntity<?> getRoomStatistics() {
        Date curDate= new Date();
        List<Object[]> rooms = phongKTXRepository.findAllRoomsHaveStudentThisTerm(curDate);
        Map<Integer, Long> amountStudentsOfRooms = new HashMap<>();
        Integer count=0;
        for (Object[] result : rooms) {
            Integer phongId = (Integer) result[0];
            Long hdCount = (Long) result[1];
            if(hdCount==0) count++;
            amountStudentsOfRooms.put(phongId, hdCount);
        }
        return ResponseEntity.ok(new RoomStatistics(amountStudentsOfRooms.size()-count,count));
    }

    public ResponseEntity<?> getTotalMoneyInCurTerm() {
        Date curDate = new Date();
        List<Double> sum = hopDongKTXRepository.sumTotalByCurTerm(curDate,PageRequest.of(0, 2));
        Double percent;
        if(sum.size()==1) {
            percent = 0.0;
        }
        else {
            percent = sum.get(0)/sum.get(1);
        }
        return ResponseEntity.ok(new totalMoneyInTerm(sum.get(0),percent));

    }

    public ResponseEntity<?> getStudentsOfRoomTypes() {
        Date curDate = new Date();
        List<Object[]> stusOfRooms = phongKTXRepository.countStudentsOfRoomtypes(curDate);
        List<StudentsOfRoomtypes> studentsOfRoomtypes = new ArrayList<>();
        for (Object[] result : stusOfRooms){
            studentsOfRoomtypes.add(new StudentsOfRoomtypes(String.valueOf(result[0]),((Long) result[1]).intValue(),(Double) result[2]));
        }
        return ResponseEntity.ok(studentsOfRoomtypes);
    }

//    public ResponseEntity<?> getAmountGender() {
//        Date curdate = new Date();
//        List<Object[]> objects = hopDongKTXRepository.countHdInCurrentTermWithTrueGenderAndTotal(curdate);
//        List<amountGender> amountGenders = new ArrayList<>();
//        objects.forEach(result -> amountGenders.add(new amountGender(((Long) result[0]).longValue(),((Long) result[1]).longValue())));
//        return ResponseEntity.ok(amountGenders);
//    }
}
