package com.Kee.Ecommerce.rest;


import com.Kee.Ecommerce.dto.ProductRequest;
import com.Kee.Ecommerce.dto.ProductResponse;
import com.Kee.Ecommerce.service.SellerServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sellers")
public class SellerController {

    private final SellerServiceImpl sellerService;




    public SellerController(SellerServiceImpl sellerService){
        this.sellerService=sellerService;
    }



    @PostMapping("/product")
    public ResponseEntity<ProductResponse> addProduct(@RequestBody ProductRequest productRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(sellerService.addProduct(productRequest));
    }
}
