package com.Kee.V2C.rest;


import com.Kee.V2C.dto.*;
import com.Kee.V2C.service.VendorServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sellers")
public class VendorController {

    private final VendorServiceImpl vendorService;




    public VendorController(VendorServiceImpl sellerService){
        this.vendorService=sellerService;
    }

    @PutMapping("/update")
    public ResponseEntity<VendorProfileResponse> updateSeller(@RequestBody VendorProfileRequest vendorProfileRequest){
        return ResponseEntity.status(HttpStatus.OK).body(vendorService.updateVendorProfile(vendorProfileRequest));
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
        return ResponseEntity.status(HttpStatus.OK).body(vendorService.myProfile());
    }
}
