package com.Kee.V2C.service;

import com.Kee.V2C.Repository.*;
import com.Kee.V2C.dto.*;
import com.Kee.V2C.entity.Credential;
import com.Kee.V2C.entity.Customer;
import com.Kee.V2C.entity.Role;
import com.Kee.V2C.entity.Vendor;
import com.Kee.V2C.enums.UserRoles;
import com.Kee.V2C.enums.UserStatus;
import com.Kee.V2C.exception.RoleNotAvailableException;
import com.Kee.V2C.exception.UserAccessDeniedException;
import com.Kee.V2C.exception.UserAlreadyExistsException;
import com.Kee.V2C.exception.UserNotFoundException;
import com.Kee.V2C.security.UserDetailsImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthServiceImpl implements AuthService{
    private final PasswordEncoder passwordEncoder;
    private final CustomerRepository customerRepository;
    private final CredentialRepository credentialRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final RoleRepository roleRepository;
    private final VendorRepository vendorRepository;

    public AuthServiceImpl(PasswordEncoder passwordEncoder, CustomerRepository customerRepository
            , CredentialRepository credentialRepository, AuthenticationManager authenticationManager
            , JwtService jwtService,RoleRepository roleRepository,
                           VendorRepository vendorRepository) {
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.credentialRepository = credentialRepository;
        this.customerRepository = customerRepository;
        this.jwtService = jwtService;
        this.roleRepository=roleRepository;
        this.vendorRepository=vendorRepository;
    }

    @Override
    @Transactional
    public CustomerRegistrationResponse registerCustomer(CustomerRegistrationDTO customerRegistrationDTO) {
        if (credentialRepository.existsByEmail(customerRegistrationDTO.email())) {
            //throw a runtime exception, this mail is already listed
            throw new UserAlreadyExistsException("Email Already exists");
        }
        if (credentialRepository.existsByUserName(customerRegistrationDTO.userName())) {
            //throw a runtime exception, this username is already listed
            throw new UserAlreadyExistsException("User name Already exists");
        }

        Customer customer = convertToCustomer(customerRegistrationDTO);

        //hash the password using the password encoder
        String encodedPassword = passwordEncoder.encode(customerRegistrationDTO.password());
        customer.getCredential().setPassword(encodedPassword);


        customerRepository.save(customer);

        return convertCustomerToDto(customer);
    }

    @Override
    @Transactional
    public VendorRegistrationResponse registerVendor(VendorRegistrationDto vendorRegistrationDto){
        if (credentialRepository.existsByEmail(vendorRegistrationDto.email())) {
            //throw a runtime exception, this mail is already listed
            throw new UserAlreadyExistsException("Email Already exists");
        }
        if (credentialRepository.existsByUserName(vendorRegistrationDto.userName())) {
            //throw a runtime exception, this username is already listed
            throw new UserAlreadyExistsException("User name Already exists");
        }
        Vendor vendor = convertToVendor(vendorRegistrationDto);

        //hash the password using the password encoder
        String encodedPassword = passwordEncoder.encode(vendorRegistrationDto.password());
        vendor.getCredential().setPassword(encodedPassword);


        vendorRepository.save(vendor);

        return convertVendorToDto(vendor);
    }

    private Vendor convertToVendor(VendorRegistrationDto vendorRegistrationDto) {
        Vendor registeredVendor=new Vendor(vendorRegistrationDto.name(), vendorRegistrationDto.description(),
                vendorRegistrationDto.imageUrl(), vendorRegistrationDto.address());
        Credential credential=new Credential(vendorRegistrationDto.userName(), vendorRegistrationDto.password(), vendorRegistrationDto.email(),
                UserStatus.INACTIVE);//admins must manually confirm vendors first
        registeredVendor.setCredential(credential);
        Role vendorRole=new Role(UserRoles.ROLE_SELLER);
        credential.setRole(vendorRole );
        vendorRole.setCredential(credential);
        return registeredVendor;
    }

    @Override
    public AuthenticationResponse logIn(LoginRequest loginRequest) {
        //first we create an unauthenticated token based on request body
        UsernamePasswordAuthenticationToken unauthenticatedToken =
                new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassword());

        //we call authentication manager to authenticate the login
        Authentication authentication = authenticationManager.authenticate(unauthenticatedToken);

        //if authentication succeeded and didn't throw an exception, we want to get the
        //data inside the principal UserDetails object and cast it to a UserDetails Impl then
        //pass it to the jwtService
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        handleAccessBasedOnStatus(userDetails.getStatus());
        String jwt = jwtService.generateToken(userDetails);
        boolean  isProfileComplete = isProfileComplete(loginRequest.getUserName());

        return new AuthenticationResponse(jwt, isProfileComplete);
    }
    private CustomerRegistrationResponse convertCustomerToDto(Customer customer){
        CustomerRegistrationResponse responseDTO=new CustomerRegistrationResponse(
                customer.getId(),
                customer.getFirstName(),
                customer.getLastName(),
                customer.getCredential().getEmail(),
                customer.getPhoneNumber(),
                customer.getCredential().getCreatedAt(),
                customer.getCredential().getRole().getRole().name());
        return responseDTO;
    }

    private VendorRegistrationResponse convertVendorToDto(Vendor vendor){
        VendorRegistrationResponse responseDTO=new VendorRegistrationResponse(
                vendor.getId(),
                vendor.getName(),
                vendor.getDescription(),
                vendor.getAddress(),
                vendor.getImageUrl(),
                vendor.getCredential().getEmail(),
                vendor.getCredential().getRole().getRole().name());
        return responseDTO;
    }

    private Customer convertToCustomer(CustomerRegistrationDTO customerRegistrationDTO){
        Customer registeredCustomer=new Customer(customerRegistrationDTO.firstName(), customerRegistrationDTO.lastName(),
                customerRegistrationDTO.phoneNumber());
        Credential credential=new Credential(customerRegistrationDTO.userName(),
                customerRegistrationDTO.password(),customerRegistrationDTO.email(), UserStatus.ACTIVE);

        //attaching the credential to the customer
        registeredCustomer.setCredential(credential);
        //hardcoding the role to be customer
        Role customerRole=new Role(UserRoles.ROLE_CUSTOMER);
        credential.setRole(customerRole );

        customerRole.setCredential(credential);//bi directional link between role and credential
        return registeredCustomer;
    }

    private boolean isProfileComplete(String userName) {
        Role role=roleRepository.findByCredentialUserName(userName)
                .orElseThrow(()->new RoleNotAvailableException("role is not available"));
        boolean isProfileComplete=true;//true for admin
        if(role.getRole().equals(UserRoles.ROLE_CUSTOMER)) {
            Customer customer = customerRepository.findByCredentialUserName(userName)
                    .orElseThrow(() -> new UserNotFoundException("user not found"));
            isProfileComplete=customer.getShippingAddress()!=null;
        }
        if(role.getRole().equals(UserRoles.ROLE_SELLER)) {
            Vendor vendor = vendorRepository.findByCredentialUserName(userName)
                    .orElseThrow(() -> new UserNotFoundException("user not found"));
            isProfileComplete=true;//hardcoded true currently
        }
        return isProfileComplete;
    }

    private void handleAccessBasedOnStatus(UserStatus status){
        if(status==UserStatus.VENDOR_APPROVAL_PENDING){
            throw new UserAccessDeniedException("Please wait while an admin review your register request" +
                    "before you can sell your products on KeeConnect");
        }

        if(status==UserStatus.SUSPENDED){
            throw new UserAccessDeniedException("Your account is currently suspended, " +
                    "contact an admin for more info");
        }

    }
}


