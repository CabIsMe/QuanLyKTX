package root.quanlyktx.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import root.quanlyktx.entity.Student;


import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, String > {
    Student findByUsername(String username);
    Student findByUsernameAndPassword(String username, String pass);
//    Boolean existsByUsername(String username);
    @Query(value = "SELECT s FROM Student as s WHERE CONCAT(s.username,s.hoTen) LIKE %:keyword%")
    public List<Student> search(String keyword);
}
