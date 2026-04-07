package com.Kee.Ecommerce.service;

import com.Kee.Ecommerce.Repository.*;
import com.Kee.Ecommerce.dto.*;
import com.Kee.Ecommerce.entity.*;
import com.Kee.Ecommerce.enums.OrderStatus;
import com.Kee.Ecommerce.enums.UserRoles;
import com.Kee.Ecommerce.exception.*;
import com.Kee.Ecommerce.mapper.UserMapper;
import com.Kee.Ecommerce.security.UserDetailsImpl;
import com.Kee.Ecommerce.utils.SecurityUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final PasswordEncoder passwordEncoder;
    private final CustomerRepository customerRepository;
    private final CredentialRepository credentialRepository;
    private final ProductRepository productRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final SecurityUtil securityUtil;
    private final CartItemRepository cartItemRepository;
    private final StockRepository stockRepository;
    private final OrderRepository orderRepository;
    private final UserMapper userMapper;


    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, PasswordEncoder passwordEncoder,
                               AuthenticationManager authenticationManager, JwtService jwtService,
                               ProductRepository productRepository, SecurityUtil securityUtil
                            , CartItemRepository cartItemRepository, StockRepository stockRepository,
                               OrderRepository orderRepository, UserMapper userMapper,
                               CredentialRepository credentialRepository){
        this.customerRepository = customerRepository;
        this.passwordEncoder=passwordEncoder;
        this.credentialRepository=credentialRepository;
        this.authenticationManager=authenticationManager;
        this.jwtService=jwtService;
        this.securityUtil=securityUtil;
        this.productRepository=productRepository;
        this.cartItemRepository=cartItemRepository;
        this.stockRepository=stockRepository;
        this.orderRepository=orderRepository;
        this.userMapper=userMapper;
    }

    @Override
    @Transactional
    public CustomerRegistrationResponse registerCustomer(CustomerRegistrationDTO customerRegistrationDTO){
        if(credentialRepository.existsByEmail(customerRegistrationDTO.email())){
            //throw a runtime exception, this mail is already listed
            throw new UserAlreadyExistsException("Email Already exists");
        }
        if(credentialRepository.existsByUserName(customerRegistrationDTO.userName())
        {
            //throw a runtime exception, this username is already listed
            throw new UserAlreadyExistsException("User name Already exists");
        }

        Customer customer=convertToCustomer(customerRegistrationDTO);

        //hash the password using the password encoder
        String encodedPassword=passwordEncoder.encode(customerRegistrationDTO.password());
        customer.getCredential().setPassword(encodedPassword);


        customerRepository.save(customer);

        return convertToDto(customer);
    }


    public AuthenticationResponse logIn(LoginRequest loginRequest){
        //first we create an unauthenticated token based on request body
        UsernamePasswordAuthenticationToken unauthenticatedToken=
                new UsernamePasswordAuthenticationToken(loginRequest.getUserName(),loginRequest.getPassword());

        //we call authentication manager to authenticate the login
        Authentication authentication=authenticationManager.authenticate(unauthenticatedToken);

        //if authentication succeeded and didn't throw an exception, we want to get the
        //data inside the principal UserDetails object and cast it to a UserDetails Impl then
        //pass it to the jwtService
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        String jwt=jwtService.generateToken(userDetails);
        boolean isProfileComplete=isProfileComplete(loginRequest.getUserName());
        return new AuthenticationResponse(jwt,isProfileComplete);
    }


    private boolean isProfileComplete(String userName) {
        User user= customerRepository.findByUserNameWithRoles(userName)
                .orElseThrow(()->new UserNotFoundException("user not found"));
        if (user.getRoles().stream().anyMatch(r -> r.getRole() == UserRoles.ROLE_CUSTOMER)) {
            return user != null && user.getAddress() != null;
        }
        return true; // Admins or other roles might not need this check
    }

    private Customer convertToCustomer(CustomerRegistrationDTO customerRegistrationDTO){
        Customer registeredCustomer=new Customer(customerRegistrationDTO.firstName(), customerRegistrationDTO.lastName(),
                 customerRegistrationDTO.phoneNumber());
        Credential credential=new Credential(customerRegistrationDTO.userName(),
                customerRegistrationDTO.password(),customerRegistrationDTO.email(),true);

        //attaching the credential to the customer
        registeredCustomer.setCredential(credential);
        //hardcoding the role to be customer
        Role customerRole=new Role(UserRoles.ROLE_CUSTOMER);
        credential.setRole(customerRole );
        customerRole.setCredential(credential);//bi directional link between role and credential
        return registeredCustomer;
    }


    @Override
    public UserProfileResponse myProfile(){
        Long userId=securityUtil.getCurrentUserId();
        User user= customerRepository.findById(userId)
                .orElseThrow(()->new UsernameNotFoundException("Customer with id: "
                        +userId+"does not exist"));
        return new UserProfileResponse(user.getFirstName(),user.getLastName(),user.getPhoneNumber()
                ,user.getImageUrl(),user.getAddress(),user.getUpdatedAt());
    }

    @Override
    @Transactional
    public UserProfileResponse partialUpdateCustomerProfile(UserProfileRequest updateRequest){
        User user=getCurrentUser();
        userMapper.updateProductFromDto(updateRequest,user);
        customerRepository.save(user);
        return new UserProfileResponse(
                user.getFirstName(),user.getLastName(),user.getPhoneNumber(),user.getImageUrl(),
                user.getAddress(),user.getUpdatedAt()
        );
    }


    @Transactional
    public CartResponse addToCart(CartItemRequest cartItemRequest){
        User user=getCurrentUser();
        Product product=getProductById(cartItemRequest.productId());
        int requiredQuantity=0;

        if(cartItemRepository.existsByUserIdAndProductId(user.getId(),product.getId())){
            CartItem cartItem=cartItemRepository.findByUserIdAndProductId(user.getId(),product.getId())
                    .orElseThrow(()->new CartItemNotFoundException("cartItem not found"));
            requiredQuantity=cartItem.getQuantity()+cartItemRequest.quantity();
            cartItemHandling(product,cartItem,requiredQuantity);
        }
        else{
            requiredQuantity=cartItemRequest.quantity();
            CartItem newcartItem=new CartItem(requiredQuantity,user,product);
            cartItemHandling(product,newcartItem,requiredQuantity);

        }

        return viewMyCart();
    }

    @Override
    public CartResponse viewMyCart(){
        List<CartItem> cartItems=cartItemRepository.findAllByUserId(getCurrentUser().getId());
        if(cartItems.isEmpty()){
            throw new CartEmptyException("your shopping cart is currently empty");
        }
        return convertCartListToDto(cartItems);
    }

    @Override
    @Transactional //to roll back if anything occurs
    public CheckoutResponse checkOut(CheckOutRequest checkOutRequest){
        //Retrieve: Fetch the Cart from the database using the userId.
        List<CartItem> cart=getUserCart();
        //Validate: Check if every item in that cart is still in stock (The Atomic Shield).
        cartStockValidationAndUpdate(cart);
        //Convert: Transform the Cart items into Order items and Order
        Order order=convertCartToOrder(checkOutRequest,cart);
        orderRepository.save(order);

        //empty the cart of the user on the db , this is better than deleting 1 by 1 in loop
        cartItemRepository.deleteAllInBatch(cart);

        //Respond: Return an OrderResponse.
        return new CheckoutResponse(order.getId(),order.getTotalPrice(),order.getStatus().name(),
                order.getOrderedAt(),order.getShippingAddress());
    }

    @Override
    @Transactional(readOnly = true)
    public InvoiceResponse generateInvoice(long orderId){
        User user=getCurrentUser();
        Order order=orderRepository.findByIdWithItemsDetails(orderId).orElseThrow(
                ()->new OrderNotFoundException("you don't have an order with id: "+orderId)
        );
        if(!(order.getUser().equals(user))){
            throw new UserAccessDeniedException ("you can't access this order");
        }
        List<OrderItemResponse> orderItemsResponse=new ArrayList<>();
        for(OrderItem orderItem:order.getOrderItems()){
            OrderItemResponse response=new OrderItemResponse(
                    orderItem.getProduct().getId(),
                    orderItem.getProduct().getName(),
                    orderItem.getProduct().getImageUrl(),
                    orderItem.getQuantity(),
                    orderItem.getPriceAtPurchase(),
                    orderItem.getPriceAtPurchase().multiply(BigDecimal.valueOf(orderItem.getQuantity()))
            );
            orderItemsResponse.add(response);
        }

        return new InvoiceResponse(
                order.getId(),
                order.getOrderedAt(),
                orderItemsResponse,
                order.getTotalPrice()
        );
    }

    private UserProfileResponse updateCustomer(UserProfileRequest request, User user){
        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        user.setPhoneNumber(request.phoneNumber());
        user.setAddress(request.address());
        user.setImageUrl(request.imageUrl());
        customerRepository.save(user);
        return new UserProfileResponse(user.getFirstName(),user.getLastName(),user.getPhoneNumber()
                ,user.getImageUrl(),user.getAddress(),user.getUpdatedAt());
    }


    private CustomerRegistrationResponse convertToDto(Customer customer){
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

    private User getCurrentUser(){
        Long id=securityUtil.getCurrentUserId();
         return customerRepository.findById(id).
                orElseThrow(()->new UserNotFoundException("customer not found"));
    }

    private Product getProductById(Long id){
        return productRepository.findById(id)
                .orElseThrow(()->new ProductNotFoundException("product with id: "+id+" not found."));
    }

    private void cartItemHandling(Product product,CartItem cartItem,int requiredQuantity){
        if(requiredQuantity>0){
            //this if condition is hardcoded now for testing only 1 inventory
            //in future updates there will be multiple inventories and the inventory choice will
            //be dependent on user location ,aldo is stock isn't sufficient at one inventory we can
            //use the stock from another inventory
            if(requiredQuantity<=product.getStock().get(0).getQuantity()){
                cartItem.setQuantity(requiredQuantity);
                cartItemRepository.save(cartItem);
            }
            else{
                throw new InsufficientStockException("Insufficient stock for product with id: " +
                        product.getId() +" ." +
                        "Requested quantity= "+requiredQuantity+
                        "          Available Stock= "
                        +product.getStock().get(0).getQuantity());
            }
        }
        else{
            cartItemRepository.delete(cartItem);
            cartItemRepository.flush(); // Forces the delete to happen immediatly
        }

    }

    private CartItemResponse convertCartItemtoDto(CartItem cartItem){
        CartItemResponse cartItemResponse=new CartItemResponse(
                cartItem.getProduct().getId(),
                cartItem.getProduct().getName(),
                cartItem.getQuantity(),
                cartItem.getProduct().getPrice(),
                cartItem.getProduct().getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity()))
        );
        return cartItemResponse;
    }

    private CartResponse convertCartListToDto(List<CartItem> cartItems){
        BigDecimal totalPrice=new BigDecimal(0);
        List<CartItemResponse> cartItemResponses=new ArrayList<>();
        for(CartItem cartItem:cartItems){
            CartItemResponse cartItemResponse=convertCartItemtoDto(cartItem);
            cartItemResponses.add(cartItemResponse);
            totalPrice=totalPrice.add(cartItemResponse.subtotal());
        }
        return new CartResponse(cartItemResponses,totalPrice);
    }

    private List<CartItem> getUserCart(){
        List<CartItem> cart=cartItemRepository.findByUserIdWithDetails(getCurrentUser().getId());
        if(cart.isEmpty()){
            throw new CartEmptyException("your shopping cart is currently empty");
        }
       return cart;
    }

    private void cartStockValidationAndUpdate(List<CartItem> cart){
        int rowsUpdated=0;
        for(CartItem cartItem:cart){
            rowsUpdated= stockRepository.decrementProductStock(cartItem.getQuantity(),
                    cartItem.getProduct().getId(),
                    cartItem.getProduct().getSellerProfile().getInventories().get(0).getId());
            if(rowsUpdated==0){
                throw new InsufficientStockException("Product with id: "+cartItem.getProduct().getId()+
                        " is out of stock");
            }
        }
    }
    private Order convertCartToOrder(CheckOutRequest checkOutRequest,List<CartItem> cart){
        Order order=new Order(checkOutRequest.shippingAddress(), OrderStatus.PENDING);//order first status is pending
        order.setUser(getCurrentUser());
        BigDecimal totalPrice=new BigDecimal(0);
        for(CartItem cartItem:cart){
            OrderItem orderItem=new OrderItem(order,cartItem.getProduct(),
                    cartItem.getQuantity(),cartItem.getProduct().getPrice());
            order.addOrderItem(orderItem);
            totalPrice=totalPrice.add(cartItem.getProduct().getPrice().
                    multiply(BigDecimal.valueOf(cartItem.getQuantity())));
        }
        order.setTotalPrice(totalPrice);
        return order;
    }
}
