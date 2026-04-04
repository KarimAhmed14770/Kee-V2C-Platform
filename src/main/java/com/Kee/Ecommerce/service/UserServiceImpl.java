package com.Kee.Ecommerce.service;

import com.Kee.Ecommerce.Repository.UserRepository;
import com.Kee.Ecommerce.dto.UserRegistrationDTO;
import com.Kee.Ecommerce.dto.UserResponseDTO;
import com.Kee.Ecommerce.entity.Credential;
import com.Kee.Ecommerce.entity.CustomerProfile;
import com.Kee.Ecommerce.entity.Role;
import com.Kee.Ecommerce.entity.User;
import com.Kee.Ecommerce.enums.UserRoles;
import com.Kee.Ecommerce.exception.UserAlreadyExistsException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    private PasswordEncoder passwordEncoder;
    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,PasswordEncoder passwordEncoder){
        this.userRepository=userRepository;
        this.passwordEncoder=passwordEncoder;
    }

    @Override
    @Transactional
    public UserResponseDTO register(UserRegistrationDTO userRegistrationDTO){
        User user=convertToUser(userRegistrationDTO);
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
        user.getCredential().setPassword(encodedPassword);

        CustomerProfile customerProfile=new CustomerProfile(user,null,true);
        user.setCustomerProfile(customerProfile);

        userRepository.save(user);

        return convertToDto(user);
    }

    private User convertToUser(UserRegistrationDTO userRegistrationDTO){
        User registeredUser=new User(userRegistrationDTO.firstName(), userRegistrationDTO.lastName(),
                userRegistrationDTO.email(),userRegistrationDTO.address()
                ,userRegistrationDTO.phoneNumber());
        Credential credential=new Credential(userRegistrationDTO.userName(),
                userRegistrationDTO.password(),true);

        registeredUser.setCredential(credential);
        return registeredUser;
    }

    private UserResponseDTO convertToDto(User user){
        List<String> roles=user.getRoles().stream().map((role)->role.getRole().name()).toList();
        UserResponseDTO responseDTO=new UserResponseDTO(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getAddress(),
                user.getPhoneNumber(),
                user.getCustomerProfile().getCreatedAt(),
                roles);
        return responseDTO;
    }
}
