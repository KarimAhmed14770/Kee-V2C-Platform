package com.Kee.V2C.rest;


import com.Kee.V2C.dto.product.NewProductRequest;
import com.Kee.V2C.dto.product.ProductRequestResponse;
import com.Kee.V2C.dto.vendor.ShopRequest;
import com.Kee.V2C.dto.vendor.ShopResponse;
import com.Kee.V2C.dto.vendor.VendorProfileRequest;
import com.Kee.V2C.dto.vendor.VendorProfileResponse;
import com.Kee.V2C.service.VendorServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/vendors")
public class VendorController {

    private final VendorServiceImpl vendorService;




    public VendorController(VendorServiceImpl sellerService){
        this.vendorService=sellerService;
    }

    @PatchMapping("/my-profile/update")
    public ResponseEntity<VendorProfileResponse> updateSeller(@RequestBody VendorProfileRequest vendorProfileRequest){
        return ResponseEntity.status(HttpStatus.OK).body(vendorService.updateVendorProfile(vendorProfileRequest));
    }

    @GetMapping("/my-profile")
    public ResponseEntity<VendorProfileResponse> myProfile(){
        return ResponseEntity.status(HttpStatus.OK).body(vendorService.myProfile());
    }

    @PostMapping("/my-shop")
    public ResponseEntity<ShopResponse> registerShop(@RequestBody ShopRequest shopRequest){
        ShopResponse response=vendorService.registerShop(shopRequest);
        URI location= ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(response.id())
                .toUri();
        return ResponseEntity.created(location).body(response);
    }

    @PatchMapping("/my-shop")
    public ResponseEntity<ShopResponse> updateShop(@RequestBody ShopRequest shopRequest){
        return ResponseEntity.ok(vendorService.updateShopInfo(shopRequest));
    }

    @GetMapping("/my-shop")
    public ResponseEntity<ShopResponse> viewShop(){
        return ResponseEntity.ok(vendorService.viewShop());
    }

    @PatchMapping("/my-shop/deactivate")
    public ResponseEntity<ShopResponse> deactivateShop(){
        return ResponseEntity.ok(vendorService.deactivateShop());
    }

    @PatchMapping("/my-shop/activate")
    public ResponseEntity<ShopResponse> activateShop(){
        return ResponseEntity.ok(vendorService.activateShop());
    }

    @PostMapping("/product-requests")
    public ResponseEntity<ProductRequestResponse> requestNewProduct(@RequestBody NewProductRequest newProductRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(vendorService.requestNewProduct(newProductRequest));
    }

     /*
        ShopResponse registerShop(ShopRequest shopRequest);
        ShopResponse updateShopInfo(Long id,ShopRequest shopRequest);
        ShopResponse deactivateShop(Long id);
        ShopResponse activateShop(Long id);
        ShopResponse viewShop();
        */
}
