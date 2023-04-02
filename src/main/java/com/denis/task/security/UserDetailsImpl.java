package com.denis.task.security;

import com.denis.task.model.Role;
import com.denis.task.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserDetailsImpl implements UserDetails {
    private final User user;

    public UserDetailsImpl(User user) {;
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //return "ROLE_" + name();

        List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();
        for (Role role : user.getRoles()) {
            //grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
            list.add(new SimpleGrantedAuthority("ROLE_" + role));
            //System.out.println(role);
        }

        //list.add(new SimpleGrantedAuthority("ROLE_" + role));
        return list;
        //return Collections.singletonList(new SimpleGrantedAuthority(user.getRole())); //РОЛИ пользователя
    }

    @Override
    public String getPassword() {
        return this.user.getPassword();
    }

    @Override
    public String getUsername() {
        //return this.user.getUsername();
        return this.user.getName();
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

    //Нужно, чтобы получать данные аутентифицированного пользователя
    public User getUser(){
        return this.user;
    }
}
