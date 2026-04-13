package com.Kee.V2C.rest;


import com.Kee.V2C.dto.product.*;
import com.Kee.V2C.dto.vendor.*;
import com.Kee.V2C.service.VendorServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public ResponseEntity<VendorProfileResponse> updateSeller(@RequestBody VendorUpdateProfileRequest vendorUpdateProfileRequest){
        return ResponseEntity.status(HttpStatus.OK).body(vendorService.updateVendorProfile(vendorUpdateProfileRequest));
    }

    @GetMapping("/my-profile")
    public ResponseEntity<VendorProfileResponse> myProfile(){
        return ResponseEntity.status(HttpStatus.OK).body(vendorService.myProfile());
    }

    @PostMapping("/my-shop")
    public ResponseEntity<ShopResponse> registerShop(@RequestBody ShopRegisterRequest shopRegisterRequest){
        ShopResponse response=vendorService.registerShop(shopRegisterRequest);
        URI location= ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(response.id())
                .toUri();
        return ResponseEntity.created(location).body(response);
    }

    @PatchMapping("/my-shop")
    public ResponseEntity<ShopResponse> updateShop(@RequestBody ShopUpdateRequest shopRequest){
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

    @GetMapping("/product-models/my")
    public ResponseEntity<Page<ProductModelResponse>> viewMyProductModels(Pageable page){
        return ResponseEntity.ok(vendorService.myProductModels(page));

    }
    @GetMapping("/product-models/search")
    public ResponseEntity<Page<ProductModelResponse>> searchGlobalProductModels(
            @RequestParam(required = false) Long brandId,
            @RequestParam(required = false) Long subCategoryId,
            @RequestParam(required = false) String description,
            Pageable page){
        return ResponseEntity.ok(vendorService.searchGlobalProductModel(brandId, subCategoryId, description, page));

    }

    @PostMapping("/products/add-to-shop")
    public ResponseEntity<ProductResponse> addToStock(@RequestBody ProductAddToStockRequest productAddToStockRequest){
        ProductResponse productResponse=vendorService.addProductToStock(productAddToStockRequest);
        URI location=ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(productResponse.id()).toUri();
        return ResponseEntity.created(location).body(productResponse);
    }


    @GetMapping("/products")
    public ResponseEntity<Page<ProductResponse>> getMyProducts(Pageable page){
        return ResponseEntity.ok(vendorService.showMyProducts(page));
    }

    @PatchMapping("/products/edit-product-stock/{id}")
    public ResponseEntity<ProductResponse> addToStock(@PathVariable("id") Long id,int quantity){
        return ResponseEntity.ok(vendorService.addStock(id,quantity));
    }

    @PatchMapping("/products/update/{id}")
    public ResponseEntity<ProductResponse> addToStock(@PathVariable("id") Long id,ProductUpdateRequest productUpdateRequest){
        return ResponseEntity.ok(vendorService.updateProductInfo(id,productUpdateRequest));
    }


     /*
     ProductResponse updateProductInfo(Long id,ProductUpdateRequest productUpdateRequest);
    ProductResponse AddStock(Long id,Integer quantity);


        */
}
