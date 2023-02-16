package root.quanlyktx.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class HandleUserDetail implements UserDetails {
    private static final long serialVersionUID = 1L;
    private User user;
    private Collection<? extends GrantedAuthority> authorities;



    public HandleUserDetail(User user, Collection<? extends GrantedAuthority> authorities
    ) {
        this.user = user;
        this.authorities=authorities;
    }
//    public static HandleUserDetail build(User user) {
//        List<GrantedAuthority> authorities= new ArrayList<>();
//        if(user.getRole()==4)
//            authorities.add(new SimpleGrantedAuthority("student"));
//        else if(user.getRole()==3)
//            authorities.add(new SimpleGrantedAuthority("admin"));
//
//
//        return new HandleUserDetail(user, authorities);
//    }
    public static HandleUserDetail build(User user) {
        List<GrantedAuthority> authorities= new ArrayList<>();
//        if(user.getRole()==4)
//            authorities.add(new SimpleGrantedAuthority("student"));
//        else if(user.getRole()==3)
//            authorities.add(new SimpleGrantedAuthority("admin"));

        authorities.add(new SimpleGrantedAuthority(user.getRole().getRoleName()));
        return new HandleUserDetail(user, authorities);
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
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
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
