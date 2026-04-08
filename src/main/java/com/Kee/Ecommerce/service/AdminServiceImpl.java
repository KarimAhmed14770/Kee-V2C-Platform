package com.Kee.Ecommerce.service;

import com.Kee.Ecommerce.Repository.VendorRepository;
import com.Kee.Ecommerce.Repository.CustomerRepository;
import com.Kee.Ecommerce.dto.CustomerProfileResponse;
import com.Kee.Ecommerce.dto.CustomerRegistrationResponse;
import com.Kee.Ecommerce.dto.VendorProfileResponse;
import com.Kee.Ecommerce.entity.Customer;
import com.Kee.Ecommerce.entity.Role;
import com.Kee.Ecommerce.entity.Vendor;
import com.Kee.Ecommerce.enums.UserRoles;
import com.Kee.Ecommerce.exception.UserAlreadyExistsException;
import com.Kee.Ecommerce.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
