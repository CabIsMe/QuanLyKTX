package root.quanlyktx.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import root.quanlyktx.entity.PhieuNuocKTX;

import java.util.List;
import java.util.Optional;

public interface PhieuNuocKTXRepository extends JpaRepository<PhieuNuocKTX, Integer> {
    List<PhieuNuocKTX> findAllByMaSoKTX(Integer idPhongKTX);
    List<PhieuNuocKTX> findAllByMaSoKTXAndGiaNuocTheoThang_ThangGreaterThanEqualAndGiaNuocTheoThang_NamGreaterThanEqual(Integer idPhongKTX,Integer month,Integer year);
    List<PhieuNuocKTX> findAllByMaSoKTXAndAndGiaNuocTheoThang_NamOrderByGiaNuocTheoThang_ThangDesc(Integer idphongktx,Integer year);
    Optional<PhieuNuocKTX> findPhieuNuocKTXByMaSoKTXAndGiaNuocTheoThang_NamAndGiaNuocTheoThang_Thang(Integer idphongktx, Integer year, Integer month);
}
