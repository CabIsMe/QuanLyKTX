package root.quanlyktx.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import root.quanlyktx.entity.HopDongKTX;



import java.util.Date;
import java.util.List;

@Repository
public interface HopDongKTXRepository extends JpaRepository<HopDongKTX, Integer> {
    List<HopDongKTX> findAllByIdPhongKTX(Integer idPhong);
    HopDongKTX findHopDongKTXByMSSVAndTrangThaiTrue(String mssv);
    Integer countHopDongKTXByIdPhongKTXAndIdTerm(Integer idPhong, Integer idTerm);
    List <HopDongKTX> findAllByMSSV(String MSSV);
    HopDongKTX getHopDongKTXById(Integer id);
    boolean existsByIdTermAndMSSV(Integer idTerm, String MSSV);
    boolean existsByIdTermAndMSSVAndTrangThaiTrue(Integer idTerm, String MSSV);
    boolean existsById(Integer idHopDong);
    List <HopDongKTX> findAllByNgayLamDonBeforeAndTrangThaiFalse(Date date);
    boolean existsByIdTerm(Integer idTerm);
    HopDongKTX findHopDongKTXByMSSVAndTerm_NgayKetThucAfter(String mssv,Date date);
    List<HopDongKTX> findAllByTerm_Id(Integer idTerm);
    List<HopDongKTX> findByIdPhongKTXAndTerm_IdAndTrangThaiOrderByNgayLamDonDesc(Integer idPhongKTX, Integer idTerm,boolean status, Pageable pageable);
    boolean getHopDongKTXByMSSVAndTrangThaiTrue(String MSSV);
    boolean existsByMSSV(String MSSV);
    HopDongKTX findHopDongKTXByMSSVAndTerm_NgayMoDangKyBeforeAndTerm_NgayKetThucAfter(String mssv,Date date1,Date date2);
    HopDongKTX findHopDongKTXByMSSV(String mssv);
    boolean existsByIdPhongKTXAndIdTermAndTrangThaiTrue(Integer idPhong, Integer idTerm);
//    Integer countAllByTrangThaiTrueAndTerm_Id(Integer idTerm);
}
