package com.Kee.Ecommerce.service;

import com.Kee.Ecommerce.Repository.SellerProfileRepository;
import com.Kee.Ecommerce.Repository.UserRepository;
import com.Kee.Ecommerce.entity.Role;
import com.Kee.Ecommerce.entity.SellerProfile;
import com.Kee.Ecommerce.entity.User;
import com.Kee.Ecommerce.enums.UserRoles;
import com.Kee.Ecommerce.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class AdminService {
    private final UserRepository userRepository;
    private final SellerProfileRepository sellerProfileRepository;

    @Autowired
    public AdminService(UserRepository userRepository,SellerProfileRepository sellerProfileRepository){
        this.userRepository=userRepository;
        this.sellerProfileRepository=sellerProfileRepository;
    }

    @Transactional
    public void promoteToSeller(Long userId){
        Optional<User> optUser=userRepository.findByIdWithRoles(userId);
        if(optUser.isPresent()){
            User user=optUser.get();
            user.addRole(new Role(UserRoles.ROLE_SELLER));
            SellerProfile sellerProfile=new SellerProfile(null,user);
            user.setSellerProfile(sellerProfile);
            sellerProfileRepository.save(sellerProfile);
            userRepository.save(user);
        }
        else{
            throw new UserNotFoundException("user with id: "+userId+" not  found");
        }
    }
    public User getUserById(Long Id){
        return userRepository.findById(Id)
                .orElseThrow(()->new UserNotFoundException("user with id: "+ Id +" not  found"));
    }

    public User getUserByUsername(String userName){
        return userRepository.findByCredentialUserName(userName)
                .orElseThrow(()->new UserNotFoundException("user with id: "+ userName +" not  found"));
    }

    public User getUserByEMail(String email){
        return userRepository.findByEmail(email)
                .orElseThrow(()->new UserNotFoundException("user with id: "+ email +" not  found"));
    }


    public Page<User> getAllUsers(Pageable page){
        return userRepository.findAll(page);
    }

    public Page<User> getAllUsersByRole(UserRoles role,Pageable page){
        return  userRepository.findUsersByRole(role,page);
    }
}
