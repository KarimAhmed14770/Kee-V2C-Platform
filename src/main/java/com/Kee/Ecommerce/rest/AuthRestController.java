package com.Kee.Ecommerce.rest;


import com.Kee.Ecommerce.dto.AuthenticationResponse;
import com.Kee.Ecommerce.dto.LoginRequest;
import com.Kee.Ecommerce.dto.UserRegistrationDTO;
import com.Kee.Ecommerce.dto.UserResponseDTO;
import com.Kee.Ecommerce.entity.Credential;
import com.Kee.Ecommerce.entity.Role;
import com.Kee.Ecommerce.entity.User;
import com.Kee.Ecommerce.security.UserDetailsImpl;
import com.Kee.Ecommerce.service.JwtService;
import com.Kee.Ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthRestController {

    private UserService userService;
    private AuthenticationManager authenticationManager;
    private JwtService jwtService;

    @Autowired
    public AuthRestController(UserService userService,AuthenticationManager authenticationManager,
                              JwtService jwtService){
        this.userService=userService;
        this.authenticationManager=authenticationManager;
        this.jwtService=jwtService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> register(@RequestBody UserRegistrationDTO userRegisterationDTO){
        User registeredUser=new User(userRegisterationDTO.getFirstName(), userRegisterationDTO.getLastName(),
                            userRegisterationDTO.getEmail(),userRegisterationDTO.getAddress()
                            ,userRegisterationDTO.getPhoneNumber());
        Credential credential=new Credential(userRegisterationDTO.getUserName(),
                                    userRegisterationDTO.getPassword(),true);

        registeredUser.setCredential(credential);

        registeredUser=userService.register(registeredUser);

        List<String> roles=new ArrayList<>();
        registeredUser.getRoles().forEach(role -> roles.add(role.getRole().name()));
        return new ResponseEntity<>(new UserResponseDTO(registeredUser.getId(), registeredUser.getFirstName(),
                registeredUser.getLastName(), registeredUser.getEmail(), registeredUser.getAddress()
                ,registeredUser.getPhoneNumber(),roles,registeredUser.getCreatedAt()), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> loginProcess(@RequestBody LoginRequest loginRequest){
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

        return ResponseEntity.ok(new AuthenticationResponse(jwt));
        }

    }

