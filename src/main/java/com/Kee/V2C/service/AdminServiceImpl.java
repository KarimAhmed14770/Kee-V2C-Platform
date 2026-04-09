package com.Kee.V2C.service;

import com.Kee.V2C.Repository.VendorRepository;
import com.Kee.V2C.Repository.CustomerRepository;
import com.Kee.V2C.dto.CustomerProfileResponse;
import com.Kee.V2C.dto.StatusUpdateDto;
import com.Kee.V2C.dto.VendorProfileResponse;
import com.Kee.V2C.entity.Customer;
import com.Kee.V2C.entity.Vendor;
import com.Kee.V2C.enums.UserStatus;
import com.Kee.V2C.exception.ResourceNotFoundException;
import com.Kee.V2C.exception.UserNotFoundException;
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

    @Autowired
    public AdminServiceImpl(CustomerRepository customerRepository, VendorRepository vendorRepository){
        this.customerRepository = customerRepository;
        this.vendorRepository = vendorRepository;
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
}
