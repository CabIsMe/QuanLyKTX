package root.quanlyktx.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;
import root.quanlyktx.entity.HopDongKTX;
import root.quanlyktx.entity.PhongKTX;

import java.util.List;

@Repository
public interface PhongKTXRepository extends JpaRepository<PhongKTX, Integer> {
    public List<PhongKTX> findAllByLoaiKTX(Integer id);
    PhongKTX findPhongKTXById(Integer id);
}
