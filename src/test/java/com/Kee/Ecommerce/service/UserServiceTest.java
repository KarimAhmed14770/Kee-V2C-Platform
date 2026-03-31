package com.Kee.Ecommerce.service;

import com.Kee.Ecommerce.Repository.UserRepository;
import com.Kee.Ecommerce.entity.Credential;
import com.Kee.Ecommerce.entity.User;
import com.Kee.Ecommerce.exception.UserAlreadyExistsException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    static private User user;
    static private Credential credential;

    @BeforeAll
    static void setup(){

        user=new User("Karim","Ahmed","kimo@gmail.com");
        credential=new Credential("kimo123","1234",true);
        user.setCredential(credential);
    }
    @Test
    void register_shouldHashPasswordIfUserIsNew(){

        when(userRepository.existsByEmail(any())).thenReturn(false);
        when(userRepository.existsByCredentialUserName(any())).thenReturn(false);
        when(passwordEncoder.encode("1234")).thenReturn("{bcrypt}HashedPassword");

        userService.register(user);

        assertEquals("{bcrypt}HashedPassword",user.getCredential().getPassword());

        verify(userRepository,times(1)).save(user);


    }

    @Test
    @DisplayName("Email Already Exists")
    void register_ShouldThrowException_WhenEmailExists() {
       when(userRepository.existsByEmail("kimo@gmail.com")).thenReturn(true);

       assertThrows(UserAlreadyExistsException.class,()->{
           userService.register(user);
       });

       verify(userRepository,never()).save(any());

    }

    @Test
    @DisplayName("User name Already Exists")
    void register_ShouldThrowException_WhenUserNameExists() {
        when(userRepository.existsByCredentialUserName("kimo123")).thenReturn(true);

        assertThrows(UserAlreadyExistsException.class,()->{
            userService.register(user);
        });

        verify(userRepository,never()).save(any());

    }
}

/*
 public User register(User user){

        //hardcode the role, each user is set to customer regardless the request
        //the addRole method ensures the bi-directional link
        user.addRole(new Role(UserRoles.ROLE_CUSTOMER));


        //ensure the bidirectional link between user and credential
        user.getCredential().setUser(user);


        //hash the password using the password encoder
        String encodedPassword=passwordEncoder.encode(user.getCredential().getPassword());
        user.getCredential().setPassword(encodedPassword);

        userRepository.save(user);
        return user;
    }
}
 */
