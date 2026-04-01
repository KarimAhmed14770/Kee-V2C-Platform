package com.Kee.Ecommerce.rest;


import com.Kee.Ecommerce.security.UserDetailsImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestRestController {
    @GetMapping("/securityDBhandShakeCustomer")
    public String checkSecurityDbHandshakeCustomer(){
        return "handshake successfull welcome:";
    }

    @GetMapping("/securityDBhandShakeSeller")
    public String checkSecurityDbHandshakeSeller(){
        return "handshake successfull";
    }

    @GetMapping("/securityDBhandShakeAdmin")
    public String checkSecurityDbHandshakeAdmin(){
        return "handshake successfull";
    }

    @GetMapping("/my-profile")
    public ResponseEntity<String> getProfile(Authentication authentication) {
        // Cast the principal to your specific UserDetails implementation
        UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();

        return ResponseEntity.ok("Hello, " + user.getUsername() +" your id is: "+user.getId()+ ". Your roles are: " + user.getAuthorities());
    }
}
