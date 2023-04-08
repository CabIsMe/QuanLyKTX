package root.quanlyktx.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;
import root.quanlyktx.entity.HopDongKTX;
import root.quanlyktx.entity.PhongKTX;

import java.util.List;
import java.util.Optional;

@Repository
public interface PhongKTXRepository extends JpaRepository<PhongKTX, Integer> {
    List<PhongKTX> findAllByIdLoaiKTXAndTrangThaiTrue(Integer id);
    Optional<PhongKTX> findByIdAndTrangThaiTrue(Integer id);
}
