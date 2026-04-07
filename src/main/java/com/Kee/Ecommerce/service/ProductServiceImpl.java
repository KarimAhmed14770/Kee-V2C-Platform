package com.Kee.Ecommerce.service;

import com.Kee.Ecommerce.Repository.ProductRepository;
import com.Kee.Ecommerce.dto.ProductViewResponse;
import com.Kee.Ecommerce.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository){
        this.productRepository=productRepository;
    }


    @Override
    public Page<ProductViewResponse> getProductByDescription(String description, Pageable page){
        Page<Product> products=productRepository.findByDescriptionContainingIgnoreCaseAndActiveTrue(description,page);
        return products.map(this::convertToDto);
    }

    private ProductViewResponse convertToDto(Product product){
        ProductViewResponse productViewResponse=new ProductViewResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStock().get(0).getQuantity(),
                product.getImageUrl(),
                product.getCategory().getName()
        );
        return productViewResponse;
    }
}
