package root.quanlyktx.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import root.quanlyktx.entity.GiaNuocTheoThang;

public interface GiaNuocTheoThangRepository extends JpaRepository<GiaNuocTheoThang, Integer> {
    GiaNuocTheoThang findGiaNuocTheoThangByNamAndThang(Integer nam,Integer thang);
}
