package com.Kee.V2C.rest;

import com.Kee.V2C.dto.brand.BrandResponse;
import com.Kee.V2C.service.Brand.BrandService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/brands")
public class BrandController {

    private final BrandService brandService;

    public BrandController(BrandService brandService){
        this.brandService=brandService;
    }

    @GetMapping("/")
    public ResponseEntity<Page<BrandResponse>> getBrands(Pageable page){
        return ResponseEntity.status(HttpStatus.OK).body(brandService.getAllBrands(page).map(brandService::convertBrandToDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BrandResponse> getBrandById(@PathVariable("id") Long id){
        return ResponseEntity.status(HttpStatus.OK).body( brandService.convertBrandToDto(brandService.getBrandById(id)));
    }

}
