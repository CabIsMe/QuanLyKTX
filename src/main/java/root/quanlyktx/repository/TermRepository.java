package root.quanlyktx.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import root.quanlyktx.entity.Term;

import java.util.Date;


public interface TermRepository extends JpaRepository<Term, Integer> {
    Term getByNgayMoDangKyBeforeAndNgayKetThucDangKyAfter(Date date, Date date1);
    boolean existsByNgayKetThucDangKyAfter(Date d1);
    Integer countByNgayKetThucDangKyAfter(Date d);


}
