package root.quanlyktx.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import root.quanlyktx.entity.HopDongKTX;



import java.util.Date;
import java.util.List;

@Repository
public interface HopDongKTXRepository extends JpaRepository<HopDongKTX, Integer> {
    List<HopDongKTX> findAllByIdPhongKTX(Integer idPhong);
    Integer countHopDongKTXByIdPhongKTXAndIdTerm(Integer idPhong, Integer idTerm);
    HopDongKTX getHopDongKTXById(Integer id);
    boolean existsByIdTermAndMSSV(Integer idTerm, String MSSV);
    boolean existsByIdTermAndMSSVAndTrangThaiTrue(Integer idTerm, String MSSV);
    boolean existsById(Integer idHopDong);
    List <HopDongKTX> findAllByNgayLamDonBeforeAndTrangThaiFalse(Date date);
    boolean existsByIdTerm(Integer idTerm);
    List<HopDongKTX> findAllByTerm_Id(Integer idTerm);
    HopDongKTX findHopDongKTXByMSSVAndTerm_NgayKetThucAfter(String mssv,Date date);
    List<HopDongKTX> findAllByTrangThai(boolean status,Pageable pageable);
    List<HopDongKTX> findAllByIdPhongKTXAndTrangThai(Integer idPhongKTX,boolean status,Pageable pageable);
    List<HopDongKTX> findAllByTerm_IdAndTrangThai(Integer idTerm,boolean status,Pageable pageable);
    List<HopDongKTX> findByIdPhongKTXAndTerm_IdAndTrangThaiOrderByNgayLamDonDesc(Integer idPhongKTX, Integer idTerm,boolean status, Pageable pageable);
    boolean getHopDongKTXByMSSVAndTrangThaiTrue(String MSSV);
    boolean existsByMSSV(String MSSV);
    HopDongKTX findHopDongKTXByMSSVAndTerm_NgayKetThucDangKyBeforeAndTerm_NgayKetThucAfter(String mssv,Date date1,Date date2);
    HopDongKTX findByMSSVAndTrangThaiFalseAndIdTerm(String MSSV, Integer idTerm);
    boolean existsByIdPhongKTXAndIdTermAndTrangThaiTrue(Integer idPhong, Integer idTerm);
    HopDongKTX getFirstByMSSVAndNgayLamDonDesc(String mssv);
    //thong ke
    @Query("SELECT COALESCE(SUM(hd.tongTien),0) FROM HopDongKTX hd WHERE hd.term.ngayKetThucDangKy <= :curDate AND hd.trangThai=true GROUP BY hd.term.id,hd.term.ngayKetThucDangKy HAVING SUM(hd.tongTien) IS NOT NULL ORDER BY hd.term.ngayKetThucDangKy DESC ")
    List<Double> sumTotalByCurTerm(@Param("curDate") Date curDate,Pageable pageable);
}
