package root.quanlyktx.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import root.quanlyktx.entity.GiaDienTheoThang;
import root.quanlyktx.entity.PhieuDienKTX;
import root.quanlyktx.entity.PhieuNuocKTX;

import java.util.List;

public interface PhieuDienKTXRepository extends JpaRepository<PhieuDienKTX,Integer> {
    List<PhieuDienKTX> findAllByMaSoKTXAndGiaDienTheoThang_ThangGreaterThanEqualAndGiaDienTheoThang_NamGreaterThanEqual(Integer idPhongKTX,Integer month,Integer year);
    @Query("SELECT phieu FROM PhieuDienKTX phieu JOIN phieu.giaDienTheoThang gia " +
            "WHERE phieu.trangThai = :status AND gia.nam = :currentYear AND gia.thang BETWEEN :monthStart AND :monthEnd")
    List<PhieuDienKTX> findByStatusAndMonthRange(@Param("status") Boolean status, @Param("monthStart") Integer monthStart, @Param("monthEnd") Integer monthEnd, @Param("currentYear") Integer currentYear, Pageable pageable);
    List<PhieuDienKTX> findAllByTrangThaiAndGiaDienTheoThang_ThangAndGiaDienTheoThang_Nam(boolean status,Integer month,Integer year,Pageable pageable);
    boolean existsByGiaDienTheoThangAndMaSoKTX(GiaDienTheoThang giaDienTheoThang, Integer idRoom);
}
