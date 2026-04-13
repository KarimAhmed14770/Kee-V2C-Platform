package com.Kee.V2C.service;

import com.Kee.V2C.Repository.*;
import com.Kee.V2C.dto.*;
import com.Kee.V2C.dto.brand.BrandRequest;
import com.Kee.V2C.dto.brand.BrandResponse;
import com.Kee.V2C.dto.category.*;
import com.Kee.V2C.dto.customer.CustomerProfileResponse;
import com.Kee.V2C.dto.product.ProductModelRequest;
import com.Kee.V2C.dto.product.ProductModelResponse;
import com.Kee.V2C.dto.product.ProductRequestResponse;
import com.Kee.V2C.dto.vendor.VendorProfileResponse;
import com.Kee.V2C.entity.*;
import com.Kee.V2C.enums.PathFolder;
import com.Kee.V2C.enums.ProductModelStatus;
import com.Kee.V2C.enums.ProductRequestStatus;
import com.Kee.V2C.enums.UserStatus;
import com.Kee.V2C.exception.CategoryNotFoundException;
import com.Kee.V2C.exception.ResourceAlreadyExistsException;
import com.Kee.V2C.exception.ResourceNotFoundException;
import com.Kee.V2C.exception.UserNotFoundException;
import com.Kee.V2C.mapper.BrandMapper;
import com.Kee.V2C.mapper.CategoryMapper;
import com.Kee.V2C.mapper.ProductModelMapper;
import com.Kee.V2C.mapper.SubCategoryMapper;
import com.Kee.V2C.specifications.CustomerSpecs;
import com.Kee.V2C.specifications.ProductModelSpecs;
import com.Kee.V2C.specifications.VendorSpecs;
import com.Kee.V2C.utils.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdminServiceImpl implements AdminService {
    private final CustomerRepository customerRepository;
    private final VendorRepository vendorRepository;
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final CategoryService categoryService;
    private final SubCategoryRepository subCategoryRepository;
    private final SubCategoryMapper subCategoryMapper;
    private final BrandRepository brandRepository;
    private final BrandMapper brandMapper;
    private final ProductModelRepository productModelRepository;
    private final ProductModelMapper productModelMapper;
    private final ProductRequestRepository productRequestRepository;
    private final ImageService imageService;



    @Autowired
    public AdminServiceImpl(CustomerRepository customerRepository, VendorRepository vendorRepository,
                            CategoryRepository categoryRepository,CategoryMapper categoryMapper,
                            CategoryService categoryService,SubCategoryRepository subCategoryRepository
                            ,SubCategoryMapper subCategoryMapper,BrandRepository brandRepository,
                            BrandMapper brandMapper,ProductModelRepository productModelRepository,
                            ProductModelMapper productModelMapper,ProductRequestRepository productRequestRepository,
                            ImageService imageService){
        this.customerRepository = customerRepository;
        this.vendorRepository = vendorRepository;
        this.categoryRepository=categoryRepository;
        this.categoryMapper=categoryMapper;
        this.categoryService=categoryService;
        this.subCategoryRepository=subCategoryRepository;
        this.subCategoryMapper=subCategoryMapper;
        this.brandRepository=brandRepository;
        this.brandMapper=brandMapper;
        this.productModelRepository=productModelRepository;
        this.productModelMapper=productModelMapper;
        this.productRequestRepository=productRequestRepository;
        this.imageService=imageService;
    }

    @Override
    public Page<CustomerProfileResponse> getAllCustomers(Pageable page){
        Specification<Customer> spec=Specification.where(CustomerSpecs.fetchCredential());
        return customerRepository.findAll(spec,page).map(this::convertCustomerToDto);

    }
    @Override
    public CustomerProfileResponse getCustomerProfileById(Long id){
        Customer customer= customerRepository.findByIdWithCredentials(id)
                .orElseThrow(()->new UserNotFoundException("user with id: "+ id +" not  found"));
        return convertCustomerToDto(customer);
    }

    @Override
    public Page<CustomerProfileResponse> searchForCustomer(String userName, String email, String firstName,
                                                           String lastName, String shippingAddress,
                                                           UserStatus status,Pageable pageable){
        // 1. Start with an empty "where true" (conjunction) to avoid null issues
        Specification<Customer> spec = (root, query, cb) -> cb.conjunction();        if(userName!=null) spec=spec.and(CustomerSpecs.hasUserName(userName));

        if(email!=null) spec=spec.and(CustomerSpecs.hasEmail(email));

        if(firstName!=null) spec=spec.and(CustomerSpecs.hasFirstName(firstName));

        if(lastName!=null) spec=spec.and(CustomerSpecs.hasLastName(lastName));

        if(shippingAddress!=null) spec=spec.and(CustomerSpecs.hasAddress(shippingAddress));

        if(status!=null) spec=spec.and(CustomerSpecs.hasStatus(status));
        spec=spec.and(CustomerSpecs.fetchCredential());

        Page<Customer> customers=customerRepository.findAll(spec,pageable);

        return customers.map(this::convertCustomerToDto);
    }


    @Override
    public Page<VendorProfileResponse> getAllVendors(Pageable page){
        Page<Vendor> vendors= vendorRepository.findAll(page);
        return vendors.map(this::convertVendorToDto);
    }

    @Override
    public VendorProfileResponse getVendorProfileById(Long id){
        Vendor vendor=vendorRepository.findByIdWithCredentials(id)
                .orElseThrow(()->new UserNotFoundException("user with id: "+ id +" not  found"));
        return convertVendorToDto(vendor);
    }

    @Override
    public Page<VendorProfileResponse> searchForVendor(String name, String description,
                                                String address, UserStatus status,Float lowerRating,
                                                       Float higherRating,Pageable page){
        Specification<Vendor> spec=(root,query,cb)->cb.conjunction();
        if(name!=null)spec=spec.and(VendorSpecs.hasName(name));
        if(description!=null)spec=spec.and(VendorSpecs.hasDescription(description));
        if(address!=null)spec=spec.and(VendorSpecs.hasAddress(address));
        if(status!=null)spec=spec.and(VendorSpecs.hasStatus(status));
        if(lowerRating!=null || higherRating!=null)spec=spec.and(VendorSpecs.hasRatingBetween(lowerRating,higherRating));
        spec=spec.and(VendorSpecs.fetchCredentials());

        Page<Vendor> vendors=vendorRepository.findAll(spec,page);
        return vendors.map(this::convertVendorToDto);
    }

    @Override
    @Transactional
    public CustomerProfileResponse  modifyCustomerStatus(Long id, StatusUpdateDto status){
        Customer customer=getCustomerById(id);

        customer.getCredential().setUserStatus(status.status());
        customerRepository.save(customer);

        return convertCustomerToDto(customer);

    }

    @Override
    @Transactional
    public VendorProfileResponse  modifyVendorStatus(Long id,StatusUpdateDto status){
        Vendor vendor=getVendorById(id);
        vendor.getCredential().setUserStatus(status.status());
        vendorRepository.save(vendor);

        return convertVendorToDto(vendor);
    }



    @Override
    @Transactional
    public CategoryResponse addCategory(CategoryRegisterRequest categoryRegisterRequest) {
        if(categoryRepository.existsByNameIgnoreCase(categoryRegisterRequest.name())){
            throw new ResourceAlreadyExistsException("category Already exists ");
        }
        Category category=new Category(categoryRegisterRequest.name(), categoryRegisterRequest.description(),
                imageService.saveImage(categoryRegisterRequest.imageFile(),PathFolder.CATEGORIES),
                categoryRegisterRequest.active());
        categoryRepository.save(category);

        return convertCategoryToDto(category);
    }

    @Override
    public CategoryResponse getCategoryProfileById(Long id){
        return  convertCategoryToDto(categoryRepository.findById(id).orElseThrow(
                ()->new CategoryNotFoundException("Category with id: "+id+" Not found."))
        );
    }

    @Override
    public Page<CategoryResponse> getAllCategories(Pageable page){
        Page<Category> categories=categoryRepository.findAll(page);
        return categories.map(this::convertCategoryToDto);
    }
    @Override
    @Transactional
    public CategoryResponse updateCategory(Long id, CategoryUpdateRequest categoryRequest){
        Category updatedCategory=getCategoryById(id);
        categoryMapper.updateCategoryFromDto(categoryRequest,updatedCategory);
        categoryRepository.save(updatedCategory);

        return convertCategoryToDto(updatedCategory);
    }

    @Override
    @Transactional
    public CategoryResponse softDeleteCategory(Long id){
        Category category=getCategoryById(id);
        category.setActive(false);
        categoryRepository.save(category);
        return convertCategoryToDto(category);
    }



    @Override
    public Page<CategoryResponse> searchCategory(String name,String description,Boolean active,Pageable page){
        return categoryService.getCategoryByAttribute(name,description,active,page);
    }


    @Override
    @Transactional
    public SubCategoryResponse addSubCategory(Long parentId, SubCategoryRegisterRequest subCategoryRegisterRequest){
        if(subCategoryRepository.existsByNameIgnoreCase(subCategoryRegisterRequest.name())){
            throw new ResourceAlreadyExistsException("category Already exists ");
        }

        Category parentCategory=getCategoryById(parentId);
        SubCategory subCategory=new SubCategory(
                parentCategory,
                subCategoryRegisterRequest.name(),
                subCategoryRegisterRequest.description(),
                imageService.saveImage(subCategoryRegisterRequest.imageFile(),PathFolder.SUBCATEGORIES),
                subCategoryRegisterRequest.active());
        parentCategory.addSubcategory(subCategory);//linking parent to sub
        subCategoryRepository.save(subCategory);
        return convertSubCategoryToDto(subCategory);
    }


    @Override
    public SubCategoryResponse getSubCategoryProfileById(Long id){
        return convertSubCategoryToDto(getSubCategoryById(id));
    }


    @Override
    public Page<SubCategoryResponse> getSubCategoriesOfParent(Long parentId,Pageable page){
        Page<SubCategory> subCategories=subCategoryRepository.findByParentId(parentId,page);
        return subCategories.map(this::convertSubCategoryToDto);
    }

    @Override
    @Transactional
    public SubCategoryResponse updateSubCategory(Long id, SubCategoryUpdateRequest subCategoryRequest){
        SubCategory updatedCategory=getSubCategoryById(id);
        subCategoryMapper.updateSubCategoryFromDto(subCategoryRequest,updatedCategory);
        subCategoryRepository.save(updatedCategory);
        return convertSubCategoryToDto(updatedCategory);
    }


    @Override
    @Transactional
    public SubCategoryResponse softDeleteSubCategory(Long id){
        SubCategory updatedCategory=getSubCategoryById(id);
        updatedCategory.setActive(false);
        subCategoryRepository.save(updatedCategory);
        return convertSubCategoryToDto(updatedCategory);
    }

    @Override
    @Transactional
    public BrandResponse addBrand(BrandRequest brandRequest){
        if(brandRepository.existsByNameIgnoreCase(brandRequest.name())){
            throw new ResourceAlreadyExistsException("Brand already exists");
        }
        Brand brand=new Brand(brandRequest.name(),brandRequest.description()
                ,imageService.saveImage(brandRequest.imageFile(),PathFolder.BRANDS),brandRequest.active());
        brandRepository.save(brand);
        return convertBrandToDto(brand);
    }

    @Override
    public BrandResponse getBrandById(Long id){
        return convertBrandToDto(brandRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Brand not found")));
    }

    @Override
    public Page<BrandResponse> getAllBrands(Pageable page){
        Page<Brand> brands=brandRepository.findAll(page);
        return brands.map(this::convertBrandToDto);
    }

    @Override
    @Transactional
    public BrandResponse updateBrand(Long id, BrandRequest brandRequest){
        Brand brand=brandRepository.findById(id).orElseThrow(
                ()->new ResourceNotFoundException("Brand not found")
        );
        brandMapper.updateBrandFromDto(brandRequest,brand);
        brandRepository.save(brand);
        return convertBrandToDto(brand);
    }


    @Override
    @Transactional
    public BrandResponse softDeleteBrand(Long id){
        Brand brand=brandRepository.findById(id).orElseThrow(
                ()->new ResourceNotFoundException("Brand not found")
        );
        brand.setActive(false);
        brandRepository.save(brand);
        return convertBrandToDto(brand);
    }

    @Override
    @Transactional
    public ProductModelResponse addProductModel(ProductModelRequest productModelRequest){
        ProductModel productModel=convertProductModelRequestToProductModel(productModelRequest);
        productModelRepository.save(productModel);
        return convertProductModelToDto(productModel);
    }

    @Override
    public ProductModelResponse getProductModelById(Long id) {
        return convertProductModelToDto(productModelRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("product model with id: "+id+" not found")));
    }

    @Override
    public Page<ProductModelResponse> getAllProductModels(Pageable page){
        Page<ProductModel> productModels=productModelRepository.findAll(page);
        return productModels.map(this::convertProductModelToDto);

    }

    @Override
    public Page<ProductModelResponse> getProductModelsByAttributes(String name, String description,
                                                                   Long ownerId,Long subCategoryId,Long brandId,
                                                                   Boolean isGlobal,ProductModelStatus status,
                                                                   Pageable page){
        Specification<ProductModel> spec=Specification
                .where((root, query, cb) -> cb.conjunction() );

        if(name!=null && !(name.isEmpty()))spec=spec.and(ProductModelSpecs.hasName(name));
        if(description!=null && !(description.isEmpty()))spec=spec.and(ProductModelSpecs.hasDescription(description));
        if(ownerId!=null)spec=spec.and(ProductModelSpecs.hasVendor(ownerId));
        if(subCategoryId!=null)spec=spec.and(ProductModelSpecs.hasSubCategory(subCategoryId));
        if(brandId!=null)spec=spec.and(ProductModelSpecs.hasBrand(brandId));
        if(isGlobal!=null)spec=spec.and(ProductModelSpecs.isGlobal(isGlobal));
        if(status!=null)spec=spec.and(ProductModelSpecs.hasStatus(status));

        Page<ProductModel> productModels=productModelRepository.findAll(spec,page);

        return productModels.map(this::convertProductModelToDto);
    }


    @Override
    @Transactional
    public ProductModelResponse updateProductModel(Long id,ProductModelRequest productModelRequest){
        ProductModel productModel=productModelRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Product Model with id: "+id+" not found"));

        productModelMapper.updateProductModelFromDto(productModelRequest,productModel);
        productModelRepository.save(productModel);

        return convertProductModelToDto(productModel);
    }

    @Override
    @Transactional
    public ProductModelResponse softDeleteProductModel(Long id){
        ProductModel productModel=productModelRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Product Model with id: "+id+" not found"));

       productModel.setStatus(ProductModelStatus.DISABLED);
        productModelRepository.save(productModel);

        return convertProductModelToDto(productModel);

    }

    @Override
    @Transactional
    public Page<ProductRequestResponse> getAllProductsRequests(Pageable page){
        Page<ProductRequest> productRequests=productRequestRepository.findAll(page);
        return productRequests.map(this::convertProductRequestToDto);
    }
    @Override
    public Page<ProductRequestResponse> getPendingVendorsProductsRequests(Pageable page){
        Page<ProductRequest> productRequests=productRequestRepository.findByStatus(ProductRequestStatus.PENDING,page);
        return productRequests.map(this::convertProductRequestToDto);
    }

    @Override
    public ProductRequestResponse viewProductAddRequest(Long id){
        ProductRequest productRequest=productRequestRepository.findById(id).
                orElseThrow(()->new ResourceNotFoundException("no product request with id: "+id));
        return convertProductRequestToDto(productRequest);
    }
    @Override
    public ProductRequestResponse rejectProductAddRequest(Long id){
        ProductRequest productRequest=productRequestRepository.findById(id).
                orElseThrow(()->new ResourceNotFoundException("no product request with id: "+id));
        productRequest.setStatus(ProductRequestStatus.REJECTED);
        productRequestRepository.save(productRequest);
        return convertProductRequestToDto(productRequest);
    }

    @Override
    @Transactional
    public ProductModelResponse processProductAddRequest(Long requestId,AdminAdditionOnProductRequest adminAdditionOnProductRequest){
        ProductRequest request=productRequestRepository.findById(requestId).
                orElseThrow(()->new ResourceNotFoundException("request with id: "+requestId+" not found"));

        if(request.getStatus()==ProductRequestStatus.APPROVED || request.getStatus()==ProductRequestStatus.REJECTED) {
            throw new ResourceAlreadyExistsException("request was already processed");
        }

            Vendor vendor=request.getVendor();
            Brand brand=null;
            if(adminAdditionOnProductRequest.brandId()!=null) {
                brand = brandRepository.findById(adminAdditionOnProductRequest.brandId()).orElseThrow(
                        () -> new ResourceNotFoundException("Brand not found")
                );
            }
            SubCategory subCategory=subCategoryRepository.findById(adminAdditionOnProductRequest.subcategoryId())
                    .orElseThrow(()->new ResourceNotFoundException("subCategory not found"));

            ProductModel productModel=new ProductModel(adminAdditionOnProductRequest.modifiedName(),
                    adminAdditionOnProductRequest.modifiedDescription(),
                    imageService.saveImage(adminAdditionOnProductRequest.modifiedImageFile(),PathFolder.MODELS), vendor,
                    adminAdditionOnProductRequest.modifiedGlobal(),ProductModelStatus.ACTIVE,brand,
                    subCategory
                    );
            subCategory.addProductModel(productModel);
            if(!adminAdditionOnProductRequest.modifiedGlobal()){
                vendor.addProductModel(productModel);
            }
            request.setStatus(ProductRequestStatus.APPROVED);
            productRequestRepository.save(request);
            productModelRepository.save(productModel);

        return convertProductModelToDto(productModel);
        }



    /*Helper methods*/

    private CustomerProfileResponse convertCustomerToDto(Customer customer) {
        CustomerProfileResponse dto = new CustomerProfileResponse(
                customer.getId(),
                customer.getFirstName(),
                customer.getLastName(),
                customer.getPhoneNumber(),
                customer.getImageUrl(),
                customer.getShippingAddress(),
                customer.getCredential().getUserStatus().name()
        );
        return dto;
    }
    private VendorProfileResponse convertVendorToDto(Vendor vendor) {
        VendorProfileResponse dto = new VendorProfileResponse(
                vendor.getId(),
              vendor.getName(),
                vendor.getAddress(),
                vendor.getImageUrl(),
                vendor.getRating(),
                vendor.getCredential().getUserStatus().name()

        );
        return dto;
    }

    private Vendor getVendorById(Long id){
        Vendor vendor=vendorRepository.findByIdWithCredentials(id).orElseThrow(
                ()->new ResourceNotFoundException("vendor with id: "+id+" not found.")
        );
        return vendor;
    }

    private Customer getCustomerById(Long id){
        Customer customer=customerRepository.findByIdWithCredentials(id).orElseThrow(
                ()->new ResourceNotFoundException("customer with id: "+id+" not found.")
        );
        return customer;
    }

    private CategoryResponse convertCategoryToDto(Category category){
        return new CategoryResponse(
                category.getId(),
                category.getName(),
                category.getDescription(),
                category.getImageUrl(),
                category.isActive()
        );
    }

    private SubCategoryResponse convertSubCategoryToDto(SubCategory subCategory){
        return new SubCategoryResponse(
                subCategory.getParentCategory().getId(),
                subCategory.getId(),
                subCategory.getName(),
                subCategory.getDescription(),
                subCategory.getImageUrl(),
                subCategory.isActive()
        );
    }

    private Category getCategoryById(Long id){
        return categoryRepository.findById(id).orElseThrow(
                ()->new CategoryNotFoundException("Category with id: "+id+" Not found.")
        );
    }
    private SubCategory getSubCategoryById(Long id){
        return subCategoryRepository.findById(id).orElseThrow(
                ()->new CategoryNotFoundException("Category with id: "+id+" Not found.")
        );
    }

    private BrandResponse convertBrandToDto(Brand brand){
        return new BrandResponse(brand.getId(), brand.getName(), brand.getDescription(), brand.getImageUrl(),brand.getActive());
    }

    private ProductModel convertProductModelRequestToProductModel(ProductModelRequest productModelRequest){
        Brand brand=brandRepository.findById(productModelRequest.brandId()).orElseThrow(
                ()-> new ResourceNotFoundException("Brand with id: "+productModelRequest.brandId()+
                        " not found.")
        );
        SubCategory category=subCategoryRepository.findById(productModelRequest.subCategoryId()).orElseThrow(
                ()->new ResourceNotFoundException("category with id: "+productModelRequest.subCategoryId()+
                        " is not found.")
        );
        Vendor vendor=null;
        if(!productModelRequest.isGlobal()){
            vendor=vendorRepository.findById(productModelRequest.vendorId()).orElseThrow(
                    ()->new ResourceNotFoundException("vendor with id: "+productModelRequest.vendorId()+
                            " is not found.")
            );
        }
        ProductModel productModel = new ProductModel(
                productModelRequest.name(),
                productModelRequest.description(),
                imageService.saveImage(productModelRequest.image(), PathFolder.MODELS),
                vendor,
                productModelRequest.isGlobal(),
                productModelRequest.status(),
                brand,
                category
        );
        brand.addProductModel(productModel);
        category.addProductModel(productModel);
        if(vendor!=null){
            vendor.addProductModel(productModel);
        }
        return productModel;
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

    private ProductRequestResponse convertProductRequestToDto(ProductRequest productRequest){
        return new ProductRequestResponse(
                productRequest.getId(), productRequest.getName(), productRequest.getDescription(),
                productRequest.getImageUrl(),productRequest.getGlobal(),productRequest.getStatus()
        );
    }
}
