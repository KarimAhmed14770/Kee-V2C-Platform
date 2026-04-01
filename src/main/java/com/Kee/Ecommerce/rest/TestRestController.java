package com.Kee.Ecommerce.rest;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestRestController {
    @GetMapping("/securityDBhandShakeCustomer")
    public String checkSecurityDbHandshakeCustomer(){
        return "handshake successfull";
    }

    @GetMapping("/securityDBhandShakeSeller")
    public String checkSecurityDbHandshakeSeller(){
        return "handshake successfull";
    }

    @GetMapping("/securityDBhandShakeAdmin")
    public String checkSecurityDbHandshakeAdmin(){
        return "handshake successfull";
    }
}
