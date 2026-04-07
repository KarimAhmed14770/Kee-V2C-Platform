package com.Kee.Ecommerce.service;


import com.Kee.Ecommerce.dto.ProductViewResponse;
import com.Kee.Ecommerce.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

public interface ProductService {
    Page<ProductViewResponse> getProductByDescription(String description, Pageable page);

}
