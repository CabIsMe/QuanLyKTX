package root.quanlyktx.userdetail;

import org.springframework.security.core.userdetails.UserDetails;



public interface UserDetailFactory {
    UserDetails createUserDetails(String username);
}
