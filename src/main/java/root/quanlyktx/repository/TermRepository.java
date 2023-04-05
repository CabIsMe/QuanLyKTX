package root.quanlyktx.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import root.quanlyktx.entity.Term;
import root.quanlyktx.model.StudentInTerm;

import java.util.Date;
import java.util.List;


public interface TermRepository extends JpaRepository<Term, Integer> {
    Term getByNgayMoDangKyBeforeAndNgayKetThucDangKyAfter(Date date, Date date1);
    Term getByNgayKetThucDangKyBeforeAndNgayKetThucAfter(Date date, Date date1);
    List<Term> findAllByNgayMoDangKyAfter(Date d);
    boolean existsByNgayKetThucDangKyAfter(Date d1);
    boolean existsByNgayKetThucAfter(Date date);
    Integer countByNgayKetThucDangKyAfter(Date d);
    @Query("SELECT new root.quanlyktx.model.StudentInTerm(t.id,t.ngayMoDangKy,t.ngayKetThuc,COUNT(hd.id)) " +
            "FROM HopDongKTX hd " +
            "join Term t " +
            "on hd.idTerm=t.id " +
            "where hd.trangThai = true " +
            "group by t.id,t.ngayMoDangKy,t.ngayKetThuc")
    List<StudentInTerm> countStudentInTerm();

    default Term getTheNextTerm(){
        Date date=new Date();
        List<Term> terms= findAllByNgayMoDangKyAfter(date);

        if(terms.size()<1){
            return null;
        }
        else if (terms.size()==1){
            return terms.get(0);
        }
        else{
            Integer idCheck=terms.get(0).getId();
            long min=terms.get(0).getNgayMoDangKy().getTime()-date.getTime();
            for (Term term: terms){
                if(term.getNgayMoDangKy().getTime()-date.getTime()<min){
                    idCheck=term.getId();
                }
            }
            return getReferenceById(idCheck);
        }

    }

    default Integer getCurrentTerm(){
        Date date = new Date();
        Term term= getByNgayKetThucDangKyBeforeAndNgayKetThucAfter(date,date);
        if(term!=null)
            return term.getId();
        return null;
    }
}
