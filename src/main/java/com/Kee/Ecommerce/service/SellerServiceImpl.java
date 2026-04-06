package com.Kee.Ecommerce.service;

import com.Kee.Ecommerce.Repository.CategoryRepository;
import com.Kee.Ecommerce.Repository.InventoryRepository;
import com.Kee.Ecommerce.Repository.ProductRepository;
import com.Kee.Ecommerce.Repository.SellerProfileRepository;
import com.Kee.Ecommerce.dto.*;
import com.Kee.Ecommerce.entity.Inventory;
import com.Kee.Ecommerce.entity.Product;
import com.Kee.Ecommerce.entity.SellerProfile;
import com.Kee.Ecommerce.entity.Stock;
import com.Kee.Ecommerce.exception.CategoryNotFoundException;
import com.Kee.Ecommerce.exception.InventoryNotFoundException;
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
    private final InventoryRepository inventoryRepository;


    public SellerServiceImpl(SecurityUtil securityUtil, SellerProfileRepository sellerProfileRepository,
                             CategoryRepository categoryRepository, ProductRepository productRepository,
                             InventoryRepository inventoryRepository){
        this.securityUtil=securityUtil;
        this.sellerProfileRepository=sellerProfileRepository;
        this.categoryRepository=categoryRepository;
        this.productRepository=productRepository;
        this.inventoryRepository=inventoryRepository;
    }

    @Transactional
    public SellerProfileResponse updateSellerProfile(SellerProfileRequest sellerProfileRequest){
        Long userId=securityUtil.getCurrentUserId();
        SellerProfile seller=sellerProfileRepository.findByUserId(userId)
                .orElseThrow(()->new UsernameNotFoundException("Seller with id: "
                        +userId+"does not exist"));
        seller.setShopName(sellerProfileRequest.shopName());
        seller.setImageUrl(sellerProfileRequest.imageUrl());
        seller.setShopAddress(sellerProfileRequest.shopAddress());
        sellerProfileRepository.save(seller);
        return new SellerProfileResponse(seller.getShopName(),
                seller.getShopAddress(),
                seller.getImageUrl(),
                seller.getRating());
    }

    @Transactional
    public ProductResponse addProduct(ProductRequest productRequest){
        Product product=convertToProduct(productRequest);
        productRepository.save(product);
        return convertToDto(product);
    }

    @Transactional
    public InventoryResponse addInventory(InventoryRequest inventoryRequest){
        Inventory inventory=new Inventory(inventoryRequest.name(),inventoryRequest.location());
        SellerProfile seller=getSellerProfile();
        inventory.setSeller(seller);
        inventoryRepository.save(inventory);
        return new InventoryResponse(inventory.getName(),inventory.getLocation(),inventory.getCreatedAt());
    }

    private Product convertToProduct(ProductRequest productRequest){
        Product product=new Product();
        Inventory inventory=inventoryRepository.findById(productRequest.inventoryId())
                .orElseThrow(()->new InventoryNotFoundException("wrong inventory Id"));
        Stock stock=new Stock(productRequest.stock(),product,inventory);
        product.setName(productRequest.name());
        product.setDescription(productRequest.description());
        product.setPrice(productRequest.price());
        product.getStock().add(stock);
        product.setImageUrl(productRequest.imageUrl());
        product.setActive(productRequest.status());

        SellerProfile sellerProfile=getSellerProfile();

        product.setCategory(categoryRepository.findById(productRequest.categoryId())//bidirectional link
                .orElseThrow(()->new CategoryNotFoundException("Category with id: "
                        +productRequest.categoryId() +" Not Found")));
        product.getCategory().addProduct(product);

        sellerProfile.addProduct(product);//bi-directional link is handled inside
        return product;
    }

    private ProductResponse convertToDto(Product product){
        ProductResponse response=new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStock().stream().map(stock->stock.getQuantity()).toList(),
                product.getImageUrl(),
                product.getCategory().getName(),
                product.getActive(),
                product.getSellerProfile().getInventories().stream().
                        map(Inventory::getName).toList()
        );
        return response;
    }

    public SellerProfileResponse myProfile(){
        SellerProfile seller=getSellerProfile();
        return new SellerProfileResponse(seller.getShopName(),
                seller.getShopAddress(),
                seller.getImageUrl(),
                seller.getRating());
    }

    private SellerProfile getSellerProfile(){
        Long userId=securityUtil.getCurrentUserId();
        SellerProfile seller=sellerProfileRepository.findByUserId(userId)
                .orElseThrow(()->new UsernameNotFoundException("Seller with id: "
                        +userId+"does not exist"));
        return seller;
    }
}
