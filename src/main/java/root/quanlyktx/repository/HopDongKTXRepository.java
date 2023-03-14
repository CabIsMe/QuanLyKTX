package root.quanlyktx.repository;

import lombok.Data;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import root.quanlyktx.entity.HopDongKTX;

import java.util.Date;
import java.util.List;

@Repository
public interface HopDongKTXRepository extends JpaRepository<HopDongKTX, Integer> {
    List<HopDongKTX> findAllByPhongKTX(Integer idPhong);
    Integer countHopDongKTXByPhongKTX(Integer idPhong);
    Integer countHopDongKTXByPhongKTXAndNgayKetThucAfterAndTrangThaiFalse(Integer idPhong,Date currentDate);
    Integer countHopDongKTXByPhongKTXAndTrangThaiTrue(Integer idPhong);
    Integer countHopDongKTXByTrangThaiTrue();
    List <HopDongKTX> findAllByMSSV(String MSSV);
    HopDongKTX findFirstByMSSVOrderByNgayLamDonDesc(String mssv);
    HopDongKTX getByMSSV(String MSSV);
}
