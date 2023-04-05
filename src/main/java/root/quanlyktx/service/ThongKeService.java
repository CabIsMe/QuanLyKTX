package root.quanlyktx.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import root.quanlyktx.entity.Term;
import root.quanlyktx.model.StudentInTerm;
import root.quanlyktx.repository.HopDongKTXRepository;
import root.quanlyktx.repository.TermRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ThongKeService {
    @Autowired
    HopDongKTXRepository hopDongKTXRepository;
    @Autowired
    TermRepository termRepository;

    public ResponseEntity<?> getAmountStudentInTerm(Integer idTerm) {
        Optional<Term> term = termRepository.findById(idTerm);
        if (term.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("empty");
        Date dateEnd = term.get().getNgayKetThuc();
        List<Term> termList = termRepository.findAllByNgayKetThucGreaterThanEqualAndAndNgayKetThucDangKyBefore(dateEnd,new Date());
        List<StudentInTerm> studentInTerms = new ArrayList<>();
        termList.forEach(term1 -> studentInTerms.add(new StudentInTerm(term1.getId(),term1.getNgayMoDangKy(),term1.getNgayKetThuc(),term1.getHopDongKTXList().size())));
        if(studentInTerms.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("empty");
        else return ResponseEntity.ok(studentInTerms);
    }
}
