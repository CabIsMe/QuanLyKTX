package root.quanlyktx.userdetail;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import root.quanlyktx.entity.Admin;
import root.quanlyktx.entity.Student;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AbstractHandleDetail implements UserDetails {
    private Collection<? extends GrantedAuthority> authorities;
    public AbstractHandleDetail(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    public static AbstractHandleDetail buildAdminDetail(Admin admin) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(admin.getRole().getRoleName()));
        return new HandleAdminDetail(admin, authorities);
    }

    public static AbstractHandleDetail buildStudentDetail(Student student) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(student.getRole().getRoleName()));
        return new HandleStudentDetail(student, authorities);
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }
    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
