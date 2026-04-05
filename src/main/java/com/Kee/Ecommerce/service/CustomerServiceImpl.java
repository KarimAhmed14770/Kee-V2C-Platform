package com.Kee.Ecommerce.service;

import com.Kee.Ecommerce.Repository.CategoryRepository;
import com.Kee.Ecommerce.Repository.CustomerProfileRepository;
import com.Kee.Ecommerce.Repository.ProductRepository;
import com.Kee.Ecommerce.Repository.UserRepository;
import com.Kee.Ecommerce.dto.CustomerProfileRequest;
import com.Kee.Ecommerce.dto.CustomerProfileResponse;
import com.Kee.Ecommerce.entity.CustomerProfile;
import com.Kee.Ecommerce.entity.SellerProfile;
import com.Kee.Ecommerce.entity.User;
import com.Kee.Ecommerce.utils.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService{
    private final SecurityUtil securityUtil;
    private final CustomerProfileRepository customerProfileRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;


    @Autowired
    public CustomerServiceImpl(SecurityUtil securityUtil, CustomerProfileRepository customerProfileRepository,
                             CategoryRepository categoryRepository, ProductRepository productRepository
                            ,UserRepository userRepository){
        this.securityUtil=securityUtil;
        this.customerProfileRepository=customerProfileRepository;
        this.categoryRepository=categoryRepository;
        this.productRepository=productRepository;
        this.userRepository=userRepository;
    }

    @Override
    public CustomerProfileResponse updateCustomerProfile(CustomerProfileRequest customerProfileRequest){
        Long userId=securityUtil.getCurrentUserId();
        CustomerProfile customer=customerProfileRepository.findByUserId(userId)
                .orElseThrow(()->new UsernameNotFoundException("Customer with id: "
                        +userId+"does not exist"));
        User user=userRepository.findById(userId)
                .orElseThrow(()->new UsernameNotFoundException("Customer with id: "
                        +userId+"does not exist"));

        return updateCustomer(customerProfileRequest,user,customer);
    }

    @Override
    public CustomerProfileResponse myProfile(){
        Long userId=securityUtil.getCurrentUserId();
        CustomerProfile customer=customerProfileRepository.findByUserId(userId)
                .orElseThrow(()->new UsernameNotFoundException("Customer with id: "
                        +userId+"does not exist"));
        User user=userRepository.findById(userId)
                .orElseThrow(()->new UsernameNotFoundException("Customer with id: "
                        +userId+"does not exist"));
        return new CustomerProfileResponse(user.getFirstName(),user.getLastName(),user.getPhoneNumber()
                ,customer.getImageUrl(),customer.getAddress(),customer.getUpdatedAt());
    }

    private CustomerProfileResponse updateCustomer(CustomerProfileRequest request,User user,CustomerProfile customerProfile){
        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        user.setPhoneNumber(request.phoneNumber());
        customerProfile.setAddress(request.address());
        customerProfile.setImageUrl(request.imageUrl());
        user.setCustomerProfile(customerProfile);
        customerProfile.setUser(user);
        userRepository.save(user);
        return new CustomerProfileResponse(user.getFirstName(),user.getLastName(),user.getPhoneNumber()
        ,customerProfile.getImageUrl(),customerProfile.getAddress(),customerProfile.getUpdatedAt());
    }


}
