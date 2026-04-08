package com.Kee.V2C.service;

import com.Kee.V2C.Repository.VendorRepository;
import com.Kee.V2C.Repository.CustomerRepository;
import com.Kee.V2C.dto.CustomerProfileResponse;
import com.Kee.V2C.dto.VendorProfileResponse;
import com.Kee.V2C.entity.Customer;
import com.Kee.V2C.entity.Vendor;
import com.Kee.V2C.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {
    private final CustomerRepository customerRepository;
    private final VendorRepository vendorRepository;

    @Autowired
    public AdminServiceImpl(CustomerRepository customerRepository, VendorRepository vendorRepository){
        this.customerRepository = customerRepository;
        this.vendorRepository = vendorRepository;
    }
    public Page<CustomerProfileResponse> searchForCustomer(String userName,String email,String firstName,
                                                    String lastName,String shippingAddress,Boolean status){
        return null;
    }

    @Override
    public CustomerProfileResponse getCustomerById(Long id){
        Customer customer= customerRepository.findById(id)
                .orElseThrow(()->new UserNotFoundException("user with id: "+ id +" not  found"));
        return convertCustomerToDto(customer);
    }

    @Override
    public CustomerProfileResponse getCustomerByUsername(String userName){
        Customer customer= customerRepository.findByCredentialUserName(userName)
                .orElseThrow(()->new UserNotFoundException("user with user_name: "+ userName +" not  found"));
        return convertCustomerToDto(customer);
    }

    @Override
    public CustomerProfileResponse getCustomerByEmail(String email){
        Customer customer=  customerRepository.findByCredentialEmail(email)
                .orElseThrow(()->new UserNotFoundException("customer with email: "+ email +" not  found"));
        return convertCustomerToDto(customer);
    }

    @Override
    public VendorProfileResponse getVendorById(Long id){
        Vendor vendor=vendorRepository.findById(id)
                .orElseThrow(()->new UserNotFoundException("user with id: "+ id +" not  found"));
        return convertVendorToDto(vendor);
    }

    @Override
    public VendorProfileResponse getVendorByUsername(String userName){
        Vendor vendor=vendorRepository.findByCredentialUserName(userName)
                .orElseThrow(()->new UserNotFoundException("user with user_name: "+ userName +" not  found"));
        return convertVendorToDto(vendor);
    }

    @Override
    public VendorProfileResponse getVendorByEmail(String email){
        Vendor vendor=vendorRepository.findByCredentialEmail(email)
                .orElseThrow(()->new UserNotFoundException("user with email: "+ email +" not  found"));
        return convertVendorToDto(vendor);
    }

    @Override
    public Page<CustomerProfileResponse> getAllCustomers(Pageable page){
        Page<Customer> customers= customerRepository.findAll(page);

        return customers.map(this::convertCustomerToDto);

    }

    @Override
    public Page<VendorProfileResponse> getAllVendors( Pageable page){
        Page<Vendor> vendors= vendorRepository.findAll(page);
        return vendors.map(this::convertVendorToDto);

    }


    /*Helper methods*/

    private CustomerProfileResponse convertCustomerToDto(Customer customer) {
        CustomerProfileResponse dto = new CustomerProfileResponse(
                customer.getId(),
                customer.getFirstName(),
                customer.getLastName(),
                customer.getPhoneNumber(),
                customer.getImageUrl(),
                customer.getShippingAddress()
        );
        return dto;
    }

    private VendorProfileResponse convertVendorToDto(Vendor vendor) {
        VendorProfileResponse dto = new VendorProfileResponse(
              vendor.getName(),
                vendor.getAddress(),
                vendor.getImageUrl(),
                vendor.getRating()
        );
        return dto;
    }
}
