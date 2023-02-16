package root.quanlyktx.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import root.quanlyktx.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, String > {
    User findByUsername(String username);
    User findByUsernameAndPassword(String username, String pass);
//    Boolean existsByUsername(String username);
}
