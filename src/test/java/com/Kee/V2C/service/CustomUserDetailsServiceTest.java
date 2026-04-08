package com.Kee.V2C.service;

import com.Kee.V2C.Repository.CustomerRepository;
import com.Kee.V2C.entity.Credential;
import com.Kee.V2C.entity.Role;
import com.Kee.V2C.entity.User;
import com.Kee.V2C.enums.UserRoles;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class CustomUserDetailsServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomUserDetailsService customUserDetailsService;

    private User createMockUser(String username, String password, UserRoles roleName) {
      User user=new User();
      Credential credential=new Credential(username,password,true);
      user.setCredential(credential);
        Role role=new Role(roleName);
        user.addRole(role);
        return user;
    }

    @Test
    void loadUserByUsername_shouldReturnUserDetailsIfExists(){

        String targetUser="karim";
        //mocking the db object
        User dbUser =createMockUser(targetUser,"{bcrypt}encoded",UserRoles.ROLE_CUSTOMER);
         when(customerRepository.findByUserNameWithRoles(targetUser)).thenReturn(Optional.of(dbUser));

        UserDetails result=customUserDetailsService.loadUserByUsername(targetUser);

        //verify the user is not null
        assertNotNull(result);
        assertEquals(targetUser,result.getUsername());//verify the result username is the same as in db
        assertEquals("{bcrypt}encoded",result.getPassword());

        //checking for the customer role specifically
        boolean hasRoleCustomer=result.getAuthorities().stream()
                .anyMatch(a->a.getAuthority().equals("ROLE_CUSTOMER"));

        assertTrue(hasRoleCustomer);

        verify(customerRepository,times(1)).findByUserNameWithRoles(targetUser);


    }

    @Test
    void whenUserNotFoundThrowException(){

        when(customerRepository.findByUserNameWithRoles("unknown_user")).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class,()->{
            customUserDetailsService.loadUserByUsername("unknown_user");
        });

        // Confirm the first signal was sent
        verify(customerRepository).findByUserNameWithRoles("unknown_user");

// THE CLINCHER: Ensure NO OTHER signals were sent to any mocks!
        verifyNoMoreInteractions(customerRepository);

    }

    /*
     /*fetch all userinfo from the db
    User user=userRepository.findByUserNameWithRoles(username)
            .orElseThrow(()->new UsernameNotFoundException("User Name not found!"));

    /*convert the roles into a list of strings
    List<SimpleGrantedAuthority> roles=user.getRoles().stream()
            .map(role -> new SimpleGrantedAuthority(role.getRole().name())).toList();

    /*return the user object that holds the info for spring security
        return new org.springframework.security.core.userdetails.User(user.getCredential().getUserName(),
                user.getCredential().getPassword(),roles);
     */

}