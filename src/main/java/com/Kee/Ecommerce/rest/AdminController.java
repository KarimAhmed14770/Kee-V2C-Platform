package com.Kee.Ecommerce.rest;

import com.Kee.Ecommerce.dto.CustomerProfileResponse;
import com.Kee.Ecommerce.dto.PromotionRequest;
import com.Kee.Ecommerce.dto.CustomerRegistrationResponse;
import com.Kee.Ecommerce.dto.VendorProfileResponse;
import com.Kee.Ecommerce.enums.UserRoles;
import com.Kee.Ecommerce.service.AdminServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminServiceImpl adminService;

    @Autowired
    public  AdminController( AdminServiceImpl adminService){
        this.adminService=adminService;
    }
/*
    @PostMapping("/users/promote")
    public ResponseEntity<String> promoteToSeller(@RequestBody PromotionRequest promotionRequest){

        adminService.promoteToSeller(promotionRequest.userId());

        return ResponseEntity.ok("Promotion successfull for user with id:"+promotionRequest.userId());
    }
*/
    @GetMapping("/customers")
    public ResponseEntity<Page<CustomerProfileResponse>> getAllUsers(Pageable page){
        return ResponseEntity.ok(adminService.getAllCustomers(page));
    }

    @GetMapping("/vendors")
    public ResponseEntity<Page<VendorProfileResponse>> getAllSellers(Pageable page){
        return ResponseEntity.ok(adminService.getAllVendors(page));
    }

    @GetMapping("/customers/email/{email}")
    public ResponseEntity<CustomerProfileResponse> getUserByMail(@PathVariable("email")String email){
        return ResponseEntity.ok(adminService.getCustomerByEmail(email));
    }

    @GetMapping("/customers/id/{id}")
    public ResponseEntity<CustomerProfileResponse> getUserById(@PathVariable("id") Long id){
        return ResponseEntity.ok(adminService.getCustomerById(id));
    }

    @GetMapping("/customers/user_name/{userName}")
    public ResponseEntity<CustomerProfileResponse> getUserByUserName(@PathVariable("userName") String userName){
        return ResponseEntity.ok(adminService.getCustomerByUsername(userName));
    }

    @GetMapping("/vendors/email/{email}")
    public ResponseEntity<VendorProfileResponse> getVendorByMail(@PathVariable("email")String email){
        return ResponseEntity.ok(adminService.getVendorByEmail(email));
    }

    @GetMapping("/vendors/id/{id}")
    public ResponseEntity<VendorProfileResponse> getVendorById(@PathVariable("id") Long id){
        return ResponseEntity.ok(adminService.getVendorById(id));
    }

    @GetMapping("/vendors/user_name/{userName}")
    public ResponseEntity<VendorProfileResponse> getVendorByUserName(@PathVariable("userName") String userName){
        return ResponseEntity.ok(adminService.getVendorByUsername(userName));
    }

    //admin may also want to get seller info



}
