package com.Kee.Ecommerce.security;

import com.Kee.Ecommerce.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;


/*a wrapper class around the UserDetails to minimize db queries*/
public class UserDetailsImpl implements UserDetails {
        private final Long id; // This is the magic "slot" for your ID
        private final String username;
        private final String password;
        private final Collection<? extends GrantedAuthority> authorities;

        public UserDetailsImpl(User user) {
            this.id = user.getId();
            this.username = user.getCredential().getUserName();
            this.password = user.getCredential().getPassword();
            this.authorities = user.getRoles().stream()
                    .map(role -> new SimpleGrantedAuthority(role.getRole().name()))
                    .toList();
        }

        public Long getId() {
            return id;
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() { return authorities; }
        @Override
        public String getPassword() { return password; }
        @Override
        public String getUsername() { return username; }

        // Set these to true for your portfolio project
        @Override public boolean isAccountNonExpired() { return true; }
        @Override public boolean isAccountNonLocked() { return true; }
        @Override public boolean isCredentialsNonExpired() { return true; }
        @Override public boolean isEnabled() { return true; }
    }
