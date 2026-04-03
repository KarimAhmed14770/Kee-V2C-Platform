package com.Kee.Ecommerce.rest;

import com.Kee.Ecommerce.dto.PromotionRequest;
import com.Kee.Ecommerce.dto.UserResponseDTO;
import com.Kee.Ecommerce.entity.User;
import com.Kee.Ecommerce.enums.UserRoles;
import com.Kee.Ecommerce.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;

    @Autowired
    public  AdminController( AdminService adminService){
        this.adminService=adminService;
    }

    @PostMapping("/users/promote")
    public ResponseEntity<String> promoteToSeller(@RequestBody PromotionRequest promotionRequest){

        adminService.promoteToSeller(promotionRequest.userId());

        return ResponseEntity.ok("Promotion successfull for user with id:"+promotionRequest.userId());
    }

    @GetMapping("/users")
    public ResponseEntity<Page<UserResponseDTO>> getAllUsers(Pageable page){
        return ResponseEntity.ok(adminService.getAllUsers(page));
    }

    @GetMapping("/users/sellers")
    public ResponseEntity<Page<UserResponseDTO>> getAllSellers(Pageable page){
        return ResponseEntity.ok(adminService.getAllUsersByRole(UserRoles.ROLE_SELLER,page));
    }

    @GetMapping("/users/email/{email}")
    public ResponseEntity<UserResponseDTO> getUserByMail(@PathVariable("email")String email){
        return ResponseEntity.ok(adminService.getUserByEmail(email));
    }

    @GetMapping("/users/id/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable("id") Long id){
        return ResponseEntity.ok(adminService.getUserById(id));
    }

    @GetMapping("/users/user_name/{userName}")
    public ResponseEntity<UserResponseDTO> getUserByUserName(@PathVariable("userName") String userName){
        return ResponseEntity.ok(adminService.getUserByUsername(userName));
    }

    //admin may also want to get seller info



}
