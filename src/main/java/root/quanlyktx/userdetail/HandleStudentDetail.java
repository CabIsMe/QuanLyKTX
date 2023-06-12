package root.quanlyktx.userdetail;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import root.quanlyktx.entity.Student;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class HandleStudentDetail implements UserDetails {
    private static final long serialVersionUID = 1L;
    private Student student;
    private Collection<? extends GrantedAuthority> authorities;



    public HandleStudentDetail(Student student, Collection<? extends GrantedAuthority> authorities
    ) {
        this.student = student;
        this.authorities=authorities;
    }
    public static HandleStudentDetail build(Student student) {
        List<GrantedAuthority> authorities= new ArrayList<>();
//        if(user.getRole()==4)
//            authorities.add(new SimpleGrantedAuthority("student"));
//        else if(user.getRole()==3)
//            authorities.add(new SimpleGrantedAuthority("admin"));

        authorities.add(new SimpleGrantedAuthority(student.getRole().getRoleName()));
        return new HandleStudentDetail(student, authorities);
    }
    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }
    @Override
    public Collection<? extends GrantedAuthority>  getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return student.getPassword();
    }

    @Override
    public String getUsername() {
        return student.getUsername();
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
