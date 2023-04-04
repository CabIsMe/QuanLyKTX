package root.quanlyktx.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import root.quanlyktx.dto.TermDTO;
import root.quanlyktx.entity.Term;
import root.quanlyktx.repository.HopDongKTXRepository;
import root.quanlyktx.repository.TermRepository;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TermService {
    @Autowired
    private TermRepository termRepository;
    @Autowired
    private HopDongKTXRepository hopDongKTXRepository;
    @Autowired
    private ModelMapper modelMapper;

    public List<TermDTO> getAllTerm(){
        List <Term> terms= termRepository.findAll();
        if(terms.isEmpty())
            return null;
        return terms
                .stream()
                .map(term -> modelMapper.map(term, TermDTO.class))
                .collect(Collectors.toList());
    }
    public TermDTO getSingleTerm(Integer id){
        Optional<Term> optional=termRepository.findById(id);
        if(optional.isEmpty())
            return null;
        Term term=optional.get();
        return modelMapper.map(term, TermDTO.class);
    }

    public ResponseEntity<?> createTerm(TermDTO termDTO){
        if(termDTO==null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        Date date=new Date();

        if(date.compareTo(termDTO.getNgayMoDangKy())>=0 || termDTO.getNgayMoDangKy().compareTo(termDTO.getNgayKetThucDangKy())>=0
        || termDTO.getNgayKetThucDangKy().compareTo(termDTO.getNgayKetThuc())>=0){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Date invalid - An illogical ");
        }
        if(termRepository.existsByNgayKetThucDangKyAfter(termDTO.getNgayMoDangKy())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Date invalid");
        }
        if(termRepository.existsByNgayKetThucAfter(termDTO.getNgayKetThucDangKy())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Date invalid, 2 terms at the same time");
        }
        Term term=new Term(termDTO.getNgayMoDangKy(),termDTO.getNgayKetThucDangKy(),termDTO.getNgayKetThuc(),termDTO.getHanDongPhi());
        try{
           termRepository.save(term);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Add term failed");
        }
        return ResponseEntity.ok(true);
    }


    public ResponseEntity<?> updateTerm(Integer id, TermDTO termDTO){
        if(!termRepository.existsById(id)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        Term term=termRepository.getReferenceById(id);
        if(hopDongKTXRepository.existsByIdTerm(term.getId())){
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("No editing allowed");
        }
        Date date=new Date();
        if(date.compareTo(termDTO.getNgayMoDangKy())>=0 || termDTO.getNgayMoDangKy().compareTo(termDTO.getNgayKetThucDangKy())>=0
                || termDTO.getNgayKetThucDangKy().compareTo(termDTO.getNgayKetThuc())>=0){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Date invalid - An illogical ");
        }
        if(termRepository.countByNgayKetThucDangKyAfter(termDTO.getNgayMoDangKy())>1){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Date invalid");
        }
        term.setHanDongPhi(termDTO.getHanDongPhi());
        term.setNgayMoDangKy(termDTO.getNgayMoDangKy());
        term.setNgayKetThucDangKy(termDTO.getNgayKetThucDangKy());
        term.setNgayKetThuc(termDTO.getNgayKetThuc());
        termRepository.save(term);
        return ResponseEntity.ok(modelMapper.map(term, TermDTO.class));
    }


    public ResponseEntity<?> deleteTerm(Integer id){
        Optional <Term> optional=termRepository.findById(id);
        if(optional.isEmpty())
             return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        Term term=optional.get();
        if(hopDongKTXRepository.existsByIdTerm(term.getId())){
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("No editing allowed");
        }
        try{
            termRepository.delete(term);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Delete term failed");
        }
        return ResponseEntity.ok(true);
    }
}
