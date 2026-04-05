package com.Kee.Ecommerce.rest;


import com.Kee.Ecommerce.dto.*;
import com.Kee.Ecommerce.entity.Credential;
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
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class AuthRestController {

    private final UserService userService;

    @Autowired
    public AuthRestController(UserService userService,AuthenticationManager authenticationManager,
                              JwtService jwtService){
        this.userService=userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> register(@RequestBody UserRegistrationDTO userRegistrationDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.register(userRegistrationDTO));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> loginProcess(@RequestBody LoginRequest loginRequest){
        return ResponseEntity.ok(userService.logIn(loginRequest));
        }

}

