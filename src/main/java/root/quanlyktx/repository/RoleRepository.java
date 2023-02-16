package root.quanlyktx.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import root.quanlyktx.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

}
