package root.quanlyktx.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import root.quanlyktx.entity.GiaNuocTheoThang;
import root.quanlyktx.entity.PhieuNuocKTX;

import java.util.List;
import java.util.Optional;

public interface PhieuNuocKTXRepository extends JpaRepository<PhieuNuocKTX, Integer> {
    List<PhieuNuocKTX> findAllByMaSoKTXAndGiaNuocTheoThang_ThangGreaterThanEqualAndGiaNuocTheoThang_NamGreaterThanEqual(Integer idPhongKTX,Integer month,Integer year);
//    List<PhieuNuocKTX> findAllByMaSoKTXAndGiaNuocTheoThang_NamOrderByGiaNuocTheoThang_ThangDesc(Integer idphongktx,Integer year);
    @Query("SELECT phieu FROM PhieuNuocKTX phieu JOIN phieu.giaNuocTheoThang gia " +
            "WHERE phieu.trangThai = :status AND gia.nam = :currentYear AND gia.thang BETWEEN :monthStart AND :monthEnd")
    List<PhieuNuocKTX> findByStatusAndMonthRange(@Param("status") Boolean status, @Param("monthStart") Integer monthStart, @Param("monthEnd") Integer monthEnd, @Param("currentYear") Integer currentYear, Pageable pageable);
    boolean existsByGiaNuocTheoThangAndMaSoKTX(GiaNuocTheoThang giaNuocTheoThang, Integer idRoom);
    List <PhieuNuocKTX> findAllByTrangThaiAndGiaNuocTheoThang_ThangAndGiaNuocTheoThang_Nam(boolean status,Integer month,Integer year,Pageable pageable);

}
