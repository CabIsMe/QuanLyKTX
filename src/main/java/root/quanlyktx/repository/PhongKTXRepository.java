package root.quanlyktx.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import root.quanlyktx.entity.PhongKTX;

@Repository
public interface PhongKTXRepository extends JpaRepository<PhongKTX, Integer> {
}
