package com.Kee.Ecommerce.service;

import com.Kee.Ecommerce.Repository.SellerProfileRepository;
import com.Kee.Ecommerce.Repository.UserRepository;
import com.Kee.Ecommerce.dto.UserRegistrationDTO;
import com.Kee.Ecommerce.entity.Role;
import com.Kee.Ecommerce.entity.SellerProfile;
import com.Kee.Ecommerce.entity.User;
import com.Kee.Ecommerce.enums.UserRoles;
import com.Kee.Ecommerce.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
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
            throw new UserNotFoundException("user not found");
        }
    }
}
