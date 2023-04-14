package root.quanlyktx.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import root.quanlyktx.entity.HopDongKTX;
import root.quanlyktx.entity.PhongKTX;
import root.quanlyktx.entity.Term;
import root.quanlyktx.model.RoomStatistics;
import root.quanlyktx.model.StudentInTerm;
import root.quanlyktx.model.StudentsOfRoomtypes;
import root.quanlyktx.model.totalMoneyInTerm;
import root.quanlyktx.repository.HopDongKTXRepository;
import root.quanlyktx.repository.PhongKTXRepository;
import root.quanlyktx.repository.TermRepository;

import java.util.*;

@Service
public class ThongKeService {
    @Autowired
    HopDongKTXRepository hopDongKTXRepository;
    @Autowired
    TermRepository termRepository;
    @Autowired
    PhongKTXRepository phongKTXRepository;

    public ResponseEntity<?> getAmountStudentInTerm(Integer idTerm) {
        Optional<Term> term = termRepository.findById(idTerm);
        if (term.isEmpty()) return ResponseEntity.status(HttpStatus.NO_CONTENT).body("not found");
        Date dateEnd = term.get().getNgayKetThuc();
        List<Term> termList = termRepository.   findAllByNgayKetThucGreaterThanEqualAndAndNgayKetThucDangKyBefore(dateEnd,new Date());
        List<StudentInTerm> studentInTerms = new ArrayList<>();
        termList.forEach(term1 -> studentInTerms.add(new StudentInTerm(term1.getId(),term1.getNgayMoDangKy(),term1.getNgayKetThuc(),term1.getHopDongKTXList().size())));
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
}
