package com.Kee.V2C.rest;


import com.Kee.V2C.dto.product.ProductViewResponse;
import com.Kee.V2C.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public  ProductController(ProductService productService){
        this.productService=productService;
    }

        @GetMapping("/")
        public ResponseEntity<Page<ProductViewResponse>> getProductsByDescription(@RequestParam String search, Pageable page){
            return ResponseEntity.ok(productService.getProductByDescription(search,page));
        }
}
