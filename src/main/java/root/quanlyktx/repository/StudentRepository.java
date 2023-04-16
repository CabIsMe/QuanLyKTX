package root.quanlyktx.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import root.quanlyktx.entity.Student;


import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, String >, PagingAndSortingRepository<Student, String > {
    Student findByUsername(String username);
//    Boolean existsByUsername(String username);
    @Query(value = "SELECT s FROM Student as s WHERE CONCAT(s.username,s.hoTen) LIKE %:keyword%")
    public List<Student> search(String keyword);

    List<Student> findAllByUsernameLikeOrHoTenLike(String username, String name);
//    List<Student> findAllByStatusTrueOrderByUsernameAsc();
//    List<Student> findAllByStatusTrueOrderByUsernameDesc();


    Page<Student> findAllByStatusTrue(Pageable paging);
    Page<Student> findAllByStatusTrueAndGioiTinh(Pageable paging, boolean gender);
}
