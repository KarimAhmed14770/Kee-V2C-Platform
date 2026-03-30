package com.Kee.Ecommerce.rest;


import com.Kee.Ecommerce.dto.UserRegistrationDTO;
import com.Kee.Ecommerce.dto.UserResponseDTO;
import com.Kee.Ecommerce.entity.Credential;
import com.Kee.Ecommerce.entity.Role;
import com.Kee.Ecommerce.entity.User;
import com.Kee.Ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class AuthRestController {

    private UserService userService;

    @Autowired
    public AuthRestController(UserService userService){
        this.userService=userService;
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
}
