package root.quanlyktx.repository;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import root.quanlyktx.entity.LoaiKTX;

import java.util.List;
import java.util.Optional;

@Repository
public interface LoaiKTXRepository extends JpaRepository<LoaiKTX, Integer> {
    LoaiKTX findLoaiKTXById(Integer id);
    @NonNull
    Optional<LoaiKTX> findById(Integer id);

}
