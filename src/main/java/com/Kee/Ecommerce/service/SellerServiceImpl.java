package com.Kee.Ecommerce.service;

import com.Kee.Ecommerce.Repository.CategoryRepository;
import com.Kee.Ecommerce.Repository.ProductRepository;
import com.Kee.Ecommerce.Repository.SellerProfileRepository;
import com.Kee.Ecommerce.dto.ProductRequest;
import com.Kee.Ecommerce.dto.ProductResponse;
import com.Kee.Ecommerce.dto.SellerProfileRequest;
import com.Kee.Ecommerce.entity.Product;
import com.Kee.Ecommerce.entity.SellerProfile;
import com.Kee.Ecommerce.exception.CategoryNotFoundException;
import com.Kee.Ecommerce.utils.SecurityUtil;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SellerServiceImpl implements SellerService {
    private final SecurityUtil securityUtil;
    private final SellerProfileRepository sellerProfileRepository;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;


    public SellerServiceImpl(SecurityUtil securityUtil, SellerProfileRepository sellerProfileRepository,
                             CategoryRepository categoryRepository, ProductRepository productRepository){
        this.securityUtil=securityUtil;
        this.sellerProfileRepository=sellerProfileRepository;
        this.categoryRepository=categoryRepository;
        this.productRepository=productRepository;
    }

    @Transactional
    public void updateSellerProfile(SellerProfileRequest sellerProfileRequest){

    }

    @Transactional
    public ProductResponse addProduct(ProductRequest productRequest){
        Product product=convertToProduct(productRequest);
        Long userId=securityUtil.getCurrentUserId();//this user id is extracted from the jwt to identify
        //who is the seller of this request
        SellerProfile sellerProfile=sellerProfileRepository.findByUserId(userId)
                .orElseThrow(()->new UsernameNotFoundException("No seller with this ID"));

        product.setCategory(categoryRepository.findById(productRequest.categoryId())//bidirectional link
                .orElseThrow(()->new CategoryNotFoundException("Category with id: "
                        +productRequest.categoryId() +" Not Found")));
        product.getCategory().addProduct(product);

        sellerProfile.addProduct(product);//bi-directional link is handled inside

        productRepository.save(product);

        return convertToDto(product);


    }

    private Product convertToProduct(ProductRequest productRequest){
        Product product=new Product();
        product.setName(productRequest.name());
        product.setDescription(productRequest.description());
        product.setPrice(productRequest.price());
        product.setStock(productRequest.stock());
        product.setImageUrl(productRequest.imageUrl());
        product.setActive(productRequest.status());


        return product;
    }

    private ProductResponse convertToDto(Product product){
        ProductResponse response=new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStock(),
                product.getImageUrl(),
                product.getCategory().getName(),
                product.getActive()

        );
        return response;
    }
}
