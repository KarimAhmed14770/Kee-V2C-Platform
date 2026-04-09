package com.Kee.V2C.service;

import com.Kee.V2C.Repository.CategoryRepository;
import com.Kee.V2C.Repository.VendorRepository;
import com.Kee.V2C.Repository.CustomerRepository;
import com.Kee.V2C.dto.*;
import com.Kee.V2C.entity.Category;
import com.Kee.V2C.entity.Customer;
import com.Kee.V2C.entity.Vendor;
import com.Kee.V2C.enums.UserStatus;
import com.Kee.V2C.exception.CategoryNotFoundException;
import com.Kee.V2C.exception.ResourceAlreadyExistsException;
import com.Kee.V2C.exception.ResourceNotFoundException;
import com.Kee.V2C.exception.UserNotFoundException;
import com.Kee.V2C.mapper.CategoryMapper;
import com.Kee.V2C.specifications.CustomerSpecs;
import com.Kee.V2C.specifications.VendorSpecs;
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

    @Autowired
    public AdminServiceImpl(CustomerRepository customerRepository, VendorRepository vendorRepository,
                            CategoryRepository categoryRepository,CategoryMapper categoryMapper,
                            CategoryService categoryService){
        this.customerRepository = customerRepository;
        this.vendorRepository = vendorRepository;
        this.categoryRepository=categoryRepository;
        this.categoryMapper=categoryMapper;
        this.categoryService=categoryService;
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
    public Page<VendorProfileResponse> getAllVendors( Pageable page){
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
    public CategoryResponse addCategory(CategoryRequest categoryRequest) {
        if(categoryRepository.existsByNameIgnoreCase(categoryRequest.name())){
            throw new ResourceAlreadyExistsException("category Already exists ");
        }
        Category category=new Category(categoryRequest.name(), categoryRequest.description(), categoryRequest.imageUrl(),
                categoryRequest.active());
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

    private Category getCategoryById(Long id){
        return categoryRepository.findById(id).orElseThrow(
                ()->new CategoryNotFoundException("Category with id: "+id+" Not found.")
        );
    }
}
