package root.quanlyktx.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import root.quanlyktx.entity.LoaiKTX;

import java.util.List;

@Repository
public interface LoaiKTXRepository extends JpaRepository<LoaiKTX, Integer> {

}
