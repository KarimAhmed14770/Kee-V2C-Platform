package com.Kee.V2C.service;


import com.Kee.V2C.dto.ProductViewResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {
    Page<ProductViewResponse> getProductByDescription(String description, Pageable page);

}
