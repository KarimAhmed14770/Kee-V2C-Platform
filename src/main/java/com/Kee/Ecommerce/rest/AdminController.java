package com.Kee.Ecommerce.rest;

import com.Kee.Ecommerce.dto.PromotionRequest;
import com.Kee.Ecommerce.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;

    @Autowired
    public  AdminController( AdminService adminService){
        this.adminService=adminService;
    }

    @PostMapping("/promote")
    public ResponseEntity<String> promoteToSeller(@RequestBody PromotionRequest promotionRequest){

        adminService.promoteToSeller(promotionRequest.userId());

        return ResponseEntity.ok("Promotion successfull for user with id:"+promotionRequest.userId());
    }
}
