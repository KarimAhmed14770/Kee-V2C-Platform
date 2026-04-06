package com.Kee.Ecommerce.rest;


import com.Kee.Ecommerce.dto.*;
import com.Kee.Ecommerce.service.SellerServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sellers")
public class SellerController {

    private final SellerServiceImpl sellerService;




    public SellerController(SellerServiceImpl sellerService){
        this.sellerService=sellerService;
    }

    @PutMapping("/update")
    public ResponseEntity<SellerProfileResponse> updateSeller(@RequestBody SellerProfileRequest sellerProfileRequest){
        return ResponseEntity.status(HttpStatus.OK).body(sellerService.updateSellerProfile(sellerProfileRequest));
    }


    @PostMapping("/product")
    public ResponseEntity<ProductResponse> addProduct(@RequestBody ProductRequest productRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(sellerService.addProduct(productRequest));
    }

    @PostMapping("/inventory")
    public ResponseEntity<InventoryResponse> addInventory(@RequestBody InventoryRequest inventoryRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(sellerService.addInventory(inventoryRequest));
    }

    @GetMapping("/my-profile")
    public ResponseEntity<SellerProfileResponse> myProfile(){
        return ResponseEntity.status(HttpStatus.OK).body(sellerService.myProfile());
    }
}
