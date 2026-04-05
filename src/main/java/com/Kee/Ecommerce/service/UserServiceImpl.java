package com.Kee.Ecommerce.service;

import com.Kee.Ecommerce.Repository.UserRepository;
import com.Kee.Ecommerce.dto.AuthenticationResponse;
import com.Kee.Ecommerce.dto.LoginRequest;
import com.Kee.Ecommerce.dto.UserRegistrationDTO;
import com.Kee.Ecommerce.dto.UserResponseDTO;
import com.Kee.Ecommerce.entity.*;
import com.Kee.Ecommerce.enums.UserRoles;
import com.Kee.Ecommerce.exception.UserAlreadyExistsException;
import com.Kee.Ecommerce.exception.UserNotFoundException;
import com.Kee.Ecommerce.security.UserDetailsImpl;
import com.Kee.Ecommerce.utils.SecurityUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final SecurityUtil securityUtil;


    @Autowired
    public UserServiceImpl(UserRepository userRepository,PasswordEncoder passwordEncoder,
                           AuthenticationManager authenticationManager,JwtService jwtService,
                           SecurityUtil securityUtil){
        this.userRepository=userRepository;
        this.passwordEncoder=passwordEncoder;
        this.authenticationManager=authenticationManager;
        this.jwtService=jwtService;
        this.securityUtil=securityUtil;
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


    public AuthenticationResponse logIn(LoginRequest loginRequest){
        //first we create an unauthenticated token based on request body
        UsernamePasswordAuthenticationToken unauthenticatedToken=
                new UsernamePasswordAuthenticationToken(loginRequest.getUserName(),loginRequest.getPassword());

        //we call authentication manager to authenticate the login
        Authentication authentication=authenticationManager.authenticate(unauthenticatedToken);

        //if authentication succeeded and didn't throw an exception, we want to get the
        //data inside the principal UserDetails object and cast it to a UserDetails Impl then
        //pass it to the jwtService
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        String jwt=jwtService.generateToken(userDetails);
        boolean isProfileComplete=isProfileComplete(loginRequest.getUserName());
        return new AuthenticationResponse(jwt,isProfileComplete);
    }


    private boolean isProfileComplete(String userName) {
        User user=userRepository.findByUserNameWithRoles(userName)
                .orElseThrow(()->new UserNotFoundException("user not found"));
        if (user.getRoles().stream().anyMatch(r -> r.getRole() == UserRoles.ROLE_SELLER)) {
            SellerProfile seller = user.getSellerProfile();
            return seller != null && seller.getShopName() != null && seller.getShopAddress() != null;
        } else if (user.getRoles().stream().anyMatch(r -> r.getRole() == UserRoles.ROLE_CUSTOMER)) {
            CustomerProfile customer = user.getCustomerProfile();
            return customer != null && customer.getAddress() != null;
        }
        return true; // Admins or other roles might not need this check
    }

    private User convertToUser(UserRegistrationDTO userRegistrationDTO){
        User registeredUser=new User(userRegistrationDTO.firstName(), userRegistrationDTO.lastName(),
                userRegistrationDTO.email(),userRegistrationDTO.phoneNumber());
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
                user.getPhoneNumber(),
                user.getCustomerProfile().getCreatedAt(),
                roles);
        return responseDTO;
    }
}
