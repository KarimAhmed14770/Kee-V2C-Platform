package com.Kee.Ecommerce.service;


import com.Kee.Ecommerce.Repository.CredentialRepository;
import com.Kee.Ecommerce.Repository.CustomerRepository;
import com.Kee.Ecommerce.entity.Credential;
import com.Kee.Ecommerce.entity.Customer;
import com.Kee.Ecommerce.exception.UserNotFoundException;
import com.Kee.Ecommerce.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private CredentialRepository credentialRepository;

    @Autowired
    public CustomUserDetailsService(CredentialRepository credentialRepository){
        this.credentialRepository = credentialRepository;
    }

    @Override
    @Transactional(readOnly = true) //this is
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        /*fetch all userinfo from the db*/
        Credential credential=credentialRepository.findByUserName(userName)
                .orElseThrow(()->new UserNotFoundException("user with username: "+userName+" not found."));

        /*return the user object that holds the info for spring security*/
        return new UserDetailsImpl(credential);

    }
}
