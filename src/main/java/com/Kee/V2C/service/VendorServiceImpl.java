package com.Kee.V2C.service;

import com.Kee.V2C.Repository.*;
import com.Kee.V2C.dto.*;
import com.Kee.V2C.entity.*;
import com.Kee.V2C.enums.ProductRequestStatus;
import com.Kee.V2C.exception.ResourceAlreadyExistsException;
import com.Kee.V2C.exception.ResourceNotFoundException;
import com.Kee.V2C.mapper.ShopMapper;
import com.Kee.V2C.mapper.VendorMapper;
import com.Kee.V2C.utils.SecurityUtil;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VendorServiceImpl implements VendorService {
    private final SecurityUtil securityUtil;
    private final VendorRepository vendorRepository;
    private final SubCategoryRepository subCategoryRepository;
    private final ProductRepository productRepository;
    private final ShopRepository shopRepository;
    private final ProductModelRepository productModelRepository;
    private final StockRepository stockRepository;
    private final ShopMapper shopMapper;
    private final ProductRequestRepository productRequestRepository;
    private final VendorMapper vendorMapper;


    public VendorServiceImpl(SecurityUtil securityUtil, VendorRepository vendorRepository,
                             SubCategoryRepository subCategoryRepository, ProductRepository productRepository,
                             ShopRepository shopRepository,ProductModelRepository productModelRepository,
                             StockRepository stockRepository,ShopMapper shopMapper,
                             ProductRequestRepository productRequestRepository,VendorMapper vendorMapper){
        this.securityUtil=securityUtil;
        this.vendorRepository = vendorRepository;
        this.subCategoryRepository=subCategoryRepository;
        this.productRepository=productRepository;
        this.shopRepository = shopRepository;
        this.productModelRepository=productModelRepository;
        this.stockRepository=stockRepository;
        this.shopMapper=shopMapper;
        this.productRequestRepository=productRequestRepository;
        this.vendorMapper=vendorMapper;
    }

    @Transactional
    public VendorProfileResponse updateVendorProfile(VendorProfileRequest vendorProfileRequest){
        Vendor vendor=getCurrentVendor();
        vendorMapper.updateVendorFromDto(vendorProfileRequest,vendor);
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
    public ShopResponse updateShopInfo(ShopRequest shopRequest){
        Long id=getCurrentVendor().getId();
        Shop shop=shopRepository.findByVendorId(id).orElseThrow(
                ()->new ResourceNotFoundException("vendor with id: "+id+" didn't register a shop yet.")
        );
        shopMapper.updateShopFromDto(shopRequest,shop);
        shopRepository.save(shop);
        return convertShopToDto(shop);
    }

    @Override
    public ShopResponse viewShop(){
        Long id=getCurrentVendor().getId();
        Shop shop=shopRepository.findByVendorId(id)
                .orElseThrow(()->new ResourceNotFoundException("there are no shops for vendor with id: "+id+"."));
        return convertShopToDto(shop);
    }
    @Override
    @Transactional
    public ShopResponse deactivateShop(){
        Long id=getCurrentVendor().getId();
        Shop shop=shopRepository.findByVendorId(id).orElseThrow(
                ()->new ResourceNotFoundException("vendor with id: "+id+" didn't register a shop yet.")
        );
        shop.setActive(false);
        shopRepository.save(shop);
        return convertShopToDto(shop);
    }

    @Override
    @Transactional
    public ShopResponse activateShop(){
        Long id=getCurrentVendor().getId();
        Shop shop=shopRepository.findByVendorId(id).orElseThrow(
                ()->new ResourceNotFoundException("vendor with id: "+id+" didn't register a shop yet.")
        );
        shop.setActive(true);
        shopRepository.save(shop);
        return convertShopToDto(shop);
    }


    @Override
    public ProductRequestResponse requestNewProduct(NewProductRequest newProductRequest){
        Vendor vendor=getCurrentVendor();
        ProductRequest productRequest=new ProductRequest(newProductRequest.name(), newProductRequest.description(),
                newProductRequest.imageUrl(), newProductRequest.isGlobal(), ProductRequestStatus.PENDING,vendor);
        productRequestRepository.save(productRequest);
        return new ProductRequestResponse(
                productRequest.getId(), productRequest.getName(), productRequest.getDescription(),
                newProductRequest.imageUrl(), productRequest.getGlobal(),productRequest.getStatus()
        );



    }
/*
    @Override
    @Transactional
    public ProductResponse requestNewLocalProduct(LocalProductAddToStockRequest globalProductAddToStockRequest){

        return null;
    }

    @Override
    @Transactional
    public ProductResponse addNewProductToStock(GlobalProductAddToStockRequest globalProductAddToStockRequest){

        ProductModel productModel=getProductModelById(globalProductAddToStockRequest.productModelId());
        Product product=addProductToStockFromProductModel(productModel,globalProductAddToStockRequest);
        productRepository.save(product);
        return convertProductToDto(product);
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



 */
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
    /*
    private Product addProductToStockFromProductModel(ProductModel productModel,GlobalProductAddToStockRequest productAddToStockRequest){
            Vendor vendor=getCurrentVendor();
            Shop shop=shopRepository.findByVendorId(vendor.getId()).orElseThrow(
                    ()->new ResourceNotFoundException("Shop with id: "+vendor.getId()+" not found.")
            );

            Product product=new Product(vendor,productModel,productAddToStockRequest.name(),
                    productAddToStockRequest.description(),productAddToStockRequest.price(),
                    productAddToStockRequest.imageUrl());

            Stock stock=new Stock(productAddToStockRequest.stock(),product,shop);
            product.setStock(stock);
            vendor.addProduct(product);
            productModel.addProduct(product);
        return product;
    }
*/
    private ProductResponse convertProductToDto(Product product){
        return new ProductResponse(
                product.getId(),
                product.getProductModel().getId(),
                product.getProductModel().getSubCategory().getId(),
                product.getStock().getShop().getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStock().getQuantity(),
                product.getImageUrl(),
                product.getActive()

        );
    }

}