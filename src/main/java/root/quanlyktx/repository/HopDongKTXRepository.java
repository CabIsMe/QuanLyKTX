package root.quanlyktx.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import root.quanlyktx.entity.HopDongKTX;

import java.util.List;

@Repository
public interface HopDongKTXRepository extends JpaRepository<HopDongKTX, Integer> {
    List<HopDongKTX> findAllByPhongKTX(Integer idPhong);
    Integer countHopDongKTXByPhongKTX(Integer idPhong);
    Integer countHopDongKTXByTrangThaiTrue();
}
