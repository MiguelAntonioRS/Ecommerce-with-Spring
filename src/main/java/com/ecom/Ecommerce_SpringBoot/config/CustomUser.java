package com.ecom.Ecommerce_SpringBoot.config;

import com.ecom.Ecommerce_SpringBoot.entities.UserDtls;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Arrays;
import java.util.Collection;

public class CustomUser implements UserDetails {

    private UserDtls user;

    public CustomUser(UserDtls user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String role = user.getRole();
        if (role == null || role.trim().isEmpty()) {
            role = "ROLE_USER"; // Valor por defecto seguro
        }
        return Arrays.asList(new SimpleGrantedAuthority(role));
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        Boolean locked = user.getAccountNonBlocked();
        return locked != null ? locked : true; // Valor por defecto
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    // Método útil para acceder al usuario completo en las vistas
    public UserDtls getUser() {
        return user;
    }
}
