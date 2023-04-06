package root.quanlyktx.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import root.quanlyktx.entity.ThongBaoKTX;


@Repository
public interface ThongBaoKTXRepository extends JpaRepository<ThongBaoKTX, Integer> {
}
