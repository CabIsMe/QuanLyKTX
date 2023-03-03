package root.quanlyktx.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import root.quanlyktx.entity.Admin;
import root.quanlyktx.entity.Student;

@Repository
public interface AdminRepository extends JpaRepository<Admin, String > {
    Admin findByUsername(String username);
    Admin findByUsernameAndPassword(String username, String pass);
//    Boolean existsByUsername(String username);
}
