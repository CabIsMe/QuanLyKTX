package root.quanlyktx.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import root.quanlyktx.entity.Users;

public interface UsersRepository extends JpaRepository<Users, String> {
}
