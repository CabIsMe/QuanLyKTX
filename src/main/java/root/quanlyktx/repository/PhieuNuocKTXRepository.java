package root.quanlyktx.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import root.quanlyktx.entity.PhieuNuocKTX;

import java.util.List;

public interface PhieuNuocKTXRepository extends JpaRepository<PhieuNuocKTX, Integer> {
    List<PhieuNuocKTX> findAllByMaSoKTX(Integer idPhongKTX);
    List<PhieuNuocKTX> findAllByMaSoKTXAndGiaNuocTheoThang_ThangGreaterThanEqualAndGiaNuocTheoThang_NamGreaterThanEqual(Integer idPhongKTX,Integer month,Integer year);
}
