package root.quanlyktx.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;
import root.quanlyktx.entity.PhongKTX;

import java.util.List;
import java.util.Optional;

@Repository
public interface PhongKTXRepository extends JpaRepository<PhongKTX, Integer> {
    List<PhongKTX> findAllByIdLoaiKTXAndTrangThaiTrue(Integer id);
    List<PhongKTX> findAllByIdLoaiKTX(Integer id);
    List<PhongKTX> findAllByTrangThaiTrue();
    Optional<PhongKTX> findByIdAndTrangThaiTrue(Integer id);

//    @Query("select distinct p.id,p.idLoaiKTX,p.trangThai from PhongKTX p join HopDongKTX hd on p.id = hd.idPhongKTX where hd.trangThai = true And p.trangThai = true")
//    List<PhongKTX> test();
}
