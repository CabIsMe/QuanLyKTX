package root.quanlyktx.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import root.quanlyktx.entity.PhieuDienKTX;

import java.util.List;

public interface PhieuDienKTXRepository extends JpaRepository<PhieuDienKTX,Integer> {
    List<PhieuDienKTX> findAllByMaSoKTXAndGiaDienTheoThang_ThangGreaterThanEqualAndGiaDienTheoThang_NamGreaterThanEqual(Integer idPhongKTX,Integer month,Integer year);
}
