package com.Kee.Ecommerce.service;

import com.Kee.Ecommerce.Repository.UserRepository;
import com.Kee.Ecommerce.entity.Role;
import com.Kee.Ecommerce.entity.User;
import com.Kee.Ecommerce.enums.UserRoles;
import com.Kee.Ecommerce.exception.UserAlreadyExistsException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    private BCryptPasswordEncoder passwordEncoder;
    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,BCryptPasswordEncoder passwordEncoder){
        this.userRepository=userRepository;
        this.passwordEncoder=passwordEncoder;
    }

    @Override
    @Transactional
    public User register(User user){
        if(userRepository.existsByEmail(user.getEmail())){
            //throw a runtime exception, this mail is already listed
            throw new UserAlreadyExistsException("Email Already exists");
        }
        if(userRepository.existsByCredentialUserName(user.getCredential().getUserName()))
        {
            //throw a runtime exception, this username is already listed
            throw new UserAlreadyExistsException("User name Already exists");
        }

        //hardcode the role, each user is set to customer regardless the request
        //the addRole method ensures the bi-directional link
        user.addRole(new Role(UserRoles.ROLE_CUSTOMER));


        //ensure the bidirectional link between user and credential
        user.getCredential().setUser(user);


        //hash the password using the password encoder
        String encodedPassword=passwordEncoder.encode(user.getCredential().getPassword());
        user.getCredential().setPassword("{bcrypt}"+encodedPassword);

        userRepository.save(user);
        return user;
    }
}
