package com.Kee.Ecommerce.service;

import com.Kee.Ecommerce.Repository.SellerProfileRepository;
import com.Kee.Ecommerce.Repository.UserRepository;
import com.Kee.Ecommerce.dto.UserResponseDTO;
import com.Kee.Ecommerce.entity.Role;
import com.Kee.Ecommerce.entity.SellerProfile;
import com.Kee.Ecommerce.entity.User;
import com.Kee.Ecommerce.enums.UserRoles;
import com.Kee.Ecommerce.exception.UserAlreadyExistsException;
import com.Kee.Ecommerce.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {
    private final UserRepository userRepository;
    private final SellerProfileRepository sellerProfileRepository;

    @Autowired
    public AdminServiceImpl(UserRepository userRepository, SellerProfileRepository sellerProfileRepository){
        this.userRepository=userRepository;
        this.sellerProfileRepository=sellerProfileRepository;
    }

    @Transactional
    public void promoteToSeller(Long userId){
        Optional<User> optUser=userRepository.findByIdWithRoles(userId);
        if(optUser.isPresent()){
            User user=optUser.get();
            if(userRepository.existsByIdAndRoles_Role(userId,UserRoles.ROLE_SELLER)){
                throw new UserAlreadyExistsException("User is Already a seller");
            }
            user.addRole(new Role(UserRoles.ROLE_SELLER));
            SellerProfile sellerProfile=new SellerProfile(null,user,true);
            user.setSellerProfile(sellerProfile);
            sellerProfileRepository.save(sellerProfile);
            userRepository.save(user);
        }
        else{
            throw new UserNotFoundException("user with id: "+userId+" not  found");
        }
    }
    public UserResponseDTO getUserById(Long Id){
        User user= userRepository.findById(Id)
                .orElseThrow(()->new UserNotFoundException("user with id: "+ Id +" not  found"));
        return convertToDto(user);
    }

    public UserResponseDTO getUserByUsername(String userName){
        User user= userRepository.findByCredentialUserName(userName)
                .orElseThrow(()->new UserNotFoundException("user with id: "+ userName +" not  found"));
        return convertToDto(user);
    }

    public UserResponseDTO getUserByEmail(String email){
        User user=  userRepository.findByEmail(email)
                .orElseThrow(()->new UserNotFoundException("user with id: "+ email +" not  found"));
        return convertToDto(user);
    }


    public Page<UserResponseDTO> getAllUsers(Pageable page){
        Page<User> users=userRepository.findAll(page);

        return users.map(user->convertToDto(user));

    }

    public Page<UserResponseDTO> getAllUsersByRole(UserRoles role,Pageable page){
        Page<User> users=userRepository.findUsersByRole(role,page);
        return users.map(this::convertToDto);

    }


    private UserResponseDTO convertToDto(User user) {
        List<String> roles=new ArrayList<>();
        user.getRoles().forEach(role -> roles.add(role.getRole().name()));
        UserResponseDTO dto = new UserResponseDTO(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getAddress(),
                user.getPhoneNumber(),
                user.getCustomerProfile().getCreatedAt(),
                roles
        );
        return dto;
    }
}
