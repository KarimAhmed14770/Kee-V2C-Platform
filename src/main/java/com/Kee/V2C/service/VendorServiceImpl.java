package com.Kee.V2C.service;

import com.Kee.V2C.Repository.*;
import com.Kee.V2C.dto.*;
import com.Kee.V2C.entity.*;
import com.Kee.V2C.exception.ResourceAlreadyExistsException;
import com.Kee.V2C.exception.ResourceNotFoundException;
import com.Kee.V2C.mapper.ShopMapper;
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
    private final ProductModelRepository productModelRepository;
    private final StockRepository stockRepository;
    private final ShopMapper shopMapper;


    public VendorServiceImpl(SecurityUtil securityUtil, VendorRepository vendorRepository,
                             CategoryRepository categoryRepository, ProductRepository productRepository,
                             ShopRepository shopRepository,ProductModelRepository productModelRepository,
                             StockRepository stockRepository,ShopMapper shopMapper){
        this.securityUtil=securityUtil;
        this.vendorRepository = vendorRepository;
        this.categoryRepository=categoryRepository;
        this.productRepository=productRepository;
        this.shopRepository = shopRepository;
        this.productModelRepository=productModelRepository;
        this.stockRepository=stockRepository;
        this.shopMapper=shopMapper;
    }

    @Transactional
    public VendorProfileResponse updateVendorProfile(VendorProfileRequest vendorProfileRequest){
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

    @Override
    @Transactional
    public ShopResponse registerShop(ShopRequest shopRequest){
        Vendor vendor=getCurrentVendor();
        if(shopRepository.existsByVendorId(vendor.getId())){
            throw new ResourceAlreadyExistsException("you already have a shop");
        }
        Shop shop=new Shop(shopRequest.name(),shopRequest.address(),shopRequest.active(),vendor);
        vendor.setShop(shop);
        shopRepository.save(shop);

        return convertShopToDto(shop);
    }

    @Override
    @Transactional
    public ShopResponse updateShopInfo(Long id,ShopRequest shopRequest){
        Shop shop=shopRepository.findById(id).orElseThrow(
                ()->new ResourceNotFoundException("Shop with id: "+id+" not found.")
        );
        shopMapper.updateShopFromDto(shopRequest,shop);
        shopRepository.save(shop);
        return convertShopToDto(shop);
    }

    @Override
    @Transactional
    public ShopResponse deactivateShop(Long id){
        Shop shop=shopRepository.findById(id).orElseThrow(
                ()->new ResourceNotFoundException("Shop with id: "+id+" not found.")
        );
        shop.setActive(false);
        shopRepository.save(shop);
        return convertShopToDto(shop);
    }

    @Override
    @Transactional
    public ShopResponse activateShop(Long id){
        Shop shop=shopRepository.findById(id).orElseThrow(
                ()->new ResourceNotFoundException("Shop with id: "+id+" not found.")
        );
        shop.setActive(true);
        shopRepository.save(shop);
        return convertShopToDto(shop);
    }

    @Override
    @Transactional
    public ProductResponse addNewGlobalProductToStock(GlobalProductAddToStockRequest globalProductAddToStockRequest){

        ProductModel productModel=getProductModelById(globalProductAddToStockRequest.productModelId());
        Product product=addProductToStockFromProductModel(productModel,globalProductAddToStockRequest);
        return null;
    }

    @Override
    @Transactional
    public ProductResponse addNewLocalProductToStock(GlobalProductAddToStockRequest globalProductAddToStockRequest){

        return null;
    }

    @Override
    @Transactional
    public ProductResponse updateProductStock(GlobalProductAddToStockRequest globalProductAddToStockRequest) {

        return null;
    }

    @Override
    @Transactional
    public ProductResponse hideProduct(GlobalProductAddToStockRequest globalProductAddToStockRequest){

        return null;
    }

    @Override
    @Transactional
    public ProductResponse showProduct(GlobalProductAddToStockRequest globalProductAddToStockRequest){

        return null;
    }


    private Vendor getCurrentVendor(){
        Long userId=securityUtil.getCurrentUserId();
        Vendor vendor= vendorRepository.findById(userId)
                .orElseThrow(()->new UsernameNotFoundException("Seller with id: "
                        +userId+"does not exist"));
        return vendor;
    }

    private ShopResponse convertShopToDto(Shop shop){
        return new ShopResponse(shop.getId(), shop.getName(), shop.getAddress(), shop.isActive());
    }
    private ProductModel getProductModelById(Long id){
        return productModelRepository.findById(id).orElseThrow(
                ()->new ResourceNotFoundException("porduct model with id: "+id+" not found.")
        );
    }
    private Product addProductToStockFromProductModel(ProductModel productModel,GlobalProductAddToStockRequest productAddToStockRequest){
            Vendor vendor=getCurrentVendor();

        Stock stock=new Stock();
            /*
            Product(Vendor vendor,ProductModel productModel ,Stock stock,String name, String description,
                   BigDecimal price,String imageUrl)
             */

        return null;
    }


}
 /*
     public Product(Vendor vendor, String name, String description, BigDecimal price,String imageUrl) {
        this.vendor = vendor;
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageUrl=imageUrl;
    }

        Long productModelId,
        Long vendorId,
        Long shopId,
        String name,
        String description,
        BigDecimal price ,
        Integer stock,
        String imageUrl,
        Boolean status
         */