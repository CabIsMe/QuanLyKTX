package root.quanlyktx.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import root.quanlyktx.entity.PhongKTX;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface PhongKTXRepository extends JpaRepository<PhongKTX, Integer> {
    List<PhongKTX> findAllByIdLoaiKTXAndTrangThaiTrue(Integer id);
    List<PhongKTX> findAllByIdLoaiKTX(Integer id);
    List<PhongKTX> findAllByTrangThaiTrue();
    Optional<PhongKTX> findByIdAndTrangThaiTrue(Integer id);

    @Query("SELECT p.id, COALESCE(COUNT(hd), 0) FROM PhongKTX p LEFT JOIN p.hopDongKTXList hd ON hd.term.ngayKetThucDangKy <= :curDate AND hd.term.ngayKetThuc >= :curDate where p.trangThai=true GROUP BY p.id")
    List<Object[]> findAllRoomsHaveStudentThisTerm(@Param("curDate") Date curDate1);

    @Query("SELECT loai.tenLoai, COUNT(hd), loai.giaPhong "
            + "FROM LoaiKTX loai "
            + "JOIN loai.phongKTXList phong "
            + "JOIN phong.hopDongKTXList hd "
            + "JOIN hd.term t "
            + "WHERE t.ngayKetThucDangKy <= :currentDate AND t.ngayKetThuc >= :currentDate "
            + "GROUP BY loai.tenLoai, loai.giaPhong")
    List<Object[]> countStudentsOfRoomtypes(@Param("currentDate") Date currentDate);
}
