package root.quanlyktx.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import root.quanlyktx.entity.OTP;

@Repository
public interface OtpRepository extends JpaRepository<OTP, String> {
    OTP getOTPByUsername(String username);
}
