package com.Kee.V2C.service;

import com.Kee.V2C.Repository.CategoryRepository;
import com.Kee.V2C.Repository.ShopRepository;
import com.Kee.V2C.Repository.ProductRepository;
import com.Kee.V2C.Repository.VendorRepository;
import com.Kee.V2C.dto.*;
import com.Kee.V2C.entity.Vendor;
import com.Kee.V2C.utils.SecurityUtil;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VendorServiceImpl implements VendorService {
    private final SecurityUtil securityUtil;
    private final VendorRepository vendorRepository;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final ShopRepository shopRepository;


    public VendorServiceImpl(SecurityUtil securityUtil, VendorRepository vendorRepository,
                             CategoryRepository categoryRepository, ProductRepository productRepository,
                             ShopRepository shopRepository){
        this.securityUtil=securityUtil;
        this.vendorRepository = vendorRepository;
        this.categoryRepository=categoryRepository;
        this.productRepository=productRepository;
        this.shopRepository = shopRepository;
    }

    @Transactional
    public VendorProfileResponse updateSellerProfile(VendorProfileRequest vendorProfileRequest){
        Vendor vendor=getCurrentVendor();
        vendor.setName(vendorProfileRequest.vendorName());
        vendor.setImageUrl(vendorProfileRequest.imageUrl());
        vendor.setAddress(vendorProfileRequest.address());
        vendorRepository.save(vendor);
        return new VendorProfileResponse(
                vendor.getId(),
                vendor.getName(),
                vendor.getAddress(),
                vendor.getImageUrl(),
                vendor.getRating(),
                vendor.getCredential().getUserStatus().name()
                );
    }

    /*
    @Transactional
    public ProductResponse addProduct(ProductRequest productRequest){
        Product product=convertToProduct(productRequest);
        productRepository.save(product);
        return convertToDto(product);
    }
    */

    /*
    @Transactional
    public InventoryResponse addShop(InventoryRequest inventoryRequest){
        Inventory inventory=new Inventory(inventoryRequest.name(),inventoryRequest.location());
        SellerProfile seller=getSellerProfile();
        inventory.setSeller(seller);
        shopRepository.save(inventory);
        return new InventoryResponse(inventory.getName(),inventory.getLocation(),inventory.getCreatedAt());
    }*/
/*
    private Product convertToProduct(ProductRequest productRequest){
        Product product=new Product();
        Shop shop= shopRepository.findById(productRequest.shopId())
                .orElseThrow(()->new InventoryNotFoundException("wrong shop Id"));
        Stock stock=new Stock(productRequest.stock(),product,shop);
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
    */
 /*

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
*/
    public VendorProfileResponse myProfile(){
        Vendor vendor=getCurrentVendor();
        return new VendorProfileResponse(
                vendor.getId(),
                vendor.getName(),
                vendor.getAddress(),
                vendor.getImageUrl(),
                vendor.getRating(),
                vendor.getCredential().getUserStatus().name());
    }

    private Vendor getCurrentVendor(){
        Long userId=securityUtil.getCurrentUserId();
        Vendor vendor= vendorRepository.findById(userId)
                .orElseThrow(()->new UsernameNotFoundException("Seller with id: "
                        +userId+"does not exist"));
        return vendor;
    }
}
