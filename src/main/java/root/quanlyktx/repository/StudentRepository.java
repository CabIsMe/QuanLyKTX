package root.quanlyktx.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import root.quanlyktx.entity.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, String > {
    Student findByUsername(String username);
    Student findByUsernameAndPassword(String username, String pass);
//    Boolean existsByUsername(String username);
}
