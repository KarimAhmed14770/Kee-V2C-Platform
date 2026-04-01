package com.Kee.Ecommerce.service;


import com.Kee.Ecommerce.Repository.UserRepository;
import com.Kee.Ecommerce.entity.User;
import com.Kee.Ecommerce.security.UserDetailsImpl;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository){
        this.userRepository=userRepository;
    }

    @Override
    @Transactional(readOnly = true) //this is
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        /*fetch all userinfo from the db*/
        User user=userRepository.findByUserNameWithRoles(username)
                .orElseThrow(()->new UsernameNotFoundException("User Name not found!"));

        /*return the user object that holds the info for spring security*/
        return new UserDetailsImpl(user);

    }
}
