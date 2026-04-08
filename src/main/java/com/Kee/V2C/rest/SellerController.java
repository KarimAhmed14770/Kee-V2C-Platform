package com.Kee.V2C.rest;


import com.Kee.V2C.dto.*;
import com.Kee.V2C.service.VendorServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sellers")
public class SellerController {

    private final VendorServiceImpl sellerService;




    public SellerController(VendorServiceImpl sellerService){
        this.sellerService=sellerService;
    }

    @PutMapping("/update")
    public ResponseEntity<VendorProfileResponse> updateSeller(@RequestBody VendorProfileRequest vendorProfileRequest){
        return ResponseEntity.status(HttpStatus.OK).body(sellerService.updateSellerProfile(vendorProfileRequest));
    }

/*
    @PostMapping("/product")
    public ResponseEntity<ProductResponse> addProduct(@RequestBody ProductRequest productRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(sellerService.addProduct(productRequest));
    }

    @PostMapping("/inventory")
    public ResponseEntity<InventoryResponse> addInventory(@RequestBody InventoryRequest inventoryRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(sellerService.addInventory(inventoryRequest));
    }
*/
    @GetMapping("/my-profile")
    public ResponseEntity<VendorProfileResponse> myProfile(){
        return ResponseEntity.status(HttpStatus.OK).body(sellerService.myProfile());
    }
}
