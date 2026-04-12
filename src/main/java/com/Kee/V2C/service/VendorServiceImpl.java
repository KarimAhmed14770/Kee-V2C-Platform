package com.Kee.V2C.service;

import com.Kee.V2C.Repository.*;
import com.Kee.V2C.dto.product.*;
import com.Kee.V2C.dto.vendor.ShopRequest;
import com.Kee.V2C.dto.vendor.ShopResponse;
import com.Kee.V2C.dto.vendor.VendorProfileRequest;
import com.Kee.V2C.dto.vendor.VendorProfileResponse;
import com.Kee.V2C.entity.*;
import com.Kee.V2C.enums.ProductModelStatus;
import com.Kee.V2C.enums.ProductRequestStatus;
import com.Kee.V2C.exception.ResourceAlreadyExistsException;
import com.Kee.V2C.exception.ResourceNotFoundException;
import com.Kee.V2C.mapper.ProductMapper;
import com.Kee.V2C.mapper.ProductModelMapper;
import com.Kee.V2C.mapper.ShopMapper;
import com.Kee.V2C.mapper.VendorMapper;
import com.Kee.V2C.specifications.ProductModelSpecs;
import com.Kee.V2C.utils.SecurityUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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
    private final ProductMapper productMapper;
    public VendorServiceImpl(SecurityUtil securityUtil, VendorRepository vendorRepository,
                             SubCategoryRepository subCategoryRepository, ProductRepository productRepository,
                             ShopRepository shopRepository,ProductModelRepository productModelRepository,
                             StockRepository stockRepository,ShopMapper shopMapper,
                             ProductRequestRepository productRequestRepository,VendorMapper vendorMapper,
                             ProductMapper productMapper){
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
        this.productMapper=productMapper;
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
    public Page<ProductModelResponse> searchGlobalProductModel(Long brandId, Long subCategoryId, String description, Pageable page){
        Specification<ProductModel> spec=Specification.where((from, cb) -> cb.conjunction() );
        if(brandId!=null)spec=spec.and(ProductModelSpecs.hasBrand(brandId));
        if(subCategoryId!=null)spec=spec.and(ProductModelSpecs.hasSubCategory(subCategoryId));
        if(description!=null)spec=spec.and(ProductModelSpecs.hasDescription(description));
        spec=spec.and(ProductModelSpecs.hasStatus(ProductModelStatus.ACTIVE));
        spec=spec.and(ProductModelSpecs.isGlobal(true));
        Page<ProductModel> productModels=productModelRepository.findAll(spec,page);
        return productModels.map(this::convertProductModelToDto);
    }

    @Override
    public Page<ProductModelResponse> myProductModels(Pageable page) {
        Long vendorId=getCurrentVendor().getId();
        Page<ProductModel> productModels=productModelRepository.findByOwnerId(vendorId,page);
        return productModels.map(this::convertProductModelToDto);
    }
    @Override
    @Transactional
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

    @Override
    @Transactional
    public ProductResponse addProductToStock(ProductAddToStockRequest productAddToStockRequest){
        return convertProductToDto(addProductFromRequest(productAddToStockRequest));
    }
    @Override
    public Page<ProductResponse> showMyProducts(Pageable page){
        Vendor vendor=getCurrentVendor();
        Page<Product> products=productRepository.findAllByVendorId(vendor.getId(), page);
        return products.map(this::convertProductToDto);
    }


    @Override
    @Transactional
    public ProductResponse updateProductInfo(Long id,ProductUpdateRequest productUpdateRequest){
        Product product=productRepository.findById(id).orElseThrow(
                ()->new ResourceNotFoundException("no product with id: "+id)
        );
        productMapper.updateProductFromDto(productUpdateRequest,product);
        return convertProductToDto(product);
    }

    @Override
    @Transactional
    public ProductResponse addStock(Long id,Integer quantity){
        Product product=productRepository.findById(id).orElseThrow(
                ()->new ResourceNotFoundException("no product with id: "+id)
        );

        stockRepository.incrementProductStock(quantity,product.getId(),product.getStock().getShop().getId());
        return convertProductToDto(product);
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

    private Product addProductFromRequest(ProductAddToStockRequest productAddToStockRequest){
        ProductModel productModel=productModelRepository.findById(productAddToStockRequest.modelId())
                .orElseThrow(()->new ResourceNotFoundException("there is no model for this product"));
        Long vendorId=securityUtil.getCurrentUserId();
        Vendor vendor=vendorRepository.findByIdWithShopWithStock(vendorId).//getting the shop info to prevent n+1
                orElseThrow(()->new ResourceNotFoundException("no vendor with id: "+vendorId));

        Product product=new Product(vendor,productModel,productAddToStockRequest.name(), productAddToStockRequest.description(),
                productAddToStockRequest.price(), productAddToStockRequest.imageUrl());
        Stock stock=new Stock(productAddToStockRequest.stock(),product,vendor.getShop());
        stock.setActive(true);
        product.setStock(stock);
        vendor.addProduct(product);
        vendor.getShop().getStocks().add(stock);
        product.setActive(productAddToStockRequest.status());
        productRepository.save(product);
        return product;
    }
    private ProductModelResponse convertProductModelToDto(ProductModel productModel){
        return new ProductModelResponse(
                productModel.getId(),
                (productModel.getBrand()==null?null:productModel.getBrand().getId()),
                productModel.getSubCategory().getId(),
                (productModel.getVendor()==null)?null:productModel.getVendor().getId(),
                productModel.isGlobal(),
                productModel.getName(),
                productModel.getDescription(),
                productModel.getImageUrl(),
                productModel.getStatus()
        );
    }


}