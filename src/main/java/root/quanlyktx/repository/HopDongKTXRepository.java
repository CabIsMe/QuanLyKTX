package root.quanlyktx.repository;


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
    List <HopDongKTX> findAllByNgayLamDonBeforeAndTrangThaiFalse(Date date);
    void deleteAllByNgayLamDonBeforeAndTrangThaiFalse(Date date);
    boolean existsByIdTerm(Integer idTerm);
    HopDongKTX findHopDongKTXByMSSVAndTerm_NgayKetThucAfter(String mssv,Date date);
    List<HopDongKTX> findAllByTerm_Id(Integer idTerm);
    List<HopDongKTX> findAllByIdPhongKTXAndTerm_Id(Integer idPhongKTX,Integer idTerm);
}
