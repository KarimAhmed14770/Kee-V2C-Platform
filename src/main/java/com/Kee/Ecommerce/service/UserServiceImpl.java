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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.nio.file.AccessDeniedException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final SecurityUtil securityUtil;
    private final CartItemRepository cartItemRepository;
    private final StockRepository stockRepository;
    private final OrderRepository orderRepository;
    private final UserMapper userMapper;


    @Autowired
    public UserServiceImpl(UserRepository userRepository,PasswordEncoder passwordEncoder,
                           AuthenticationManager authenticationManager,JwtService jwtService,
                           ProductRepository productRepository, SecurityUtil securityUtil
                            ,CartItemRepository cartItemRepository,StockRepository stockRepository,
                           OrderRepository orderRepository,UserMapper userMapper){
        this.userRepository=userRepository;
        this.passwordEncoder=passwordEncoder;
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
    public UserResponseDTO register(UserRegistrationDTO userRegistrationDTO){
        User user=convertToUser(userRegistrationDTO);
        if(userRepository.existsByEmail(user.getEmail())){
            //throw a runtime exception, this mail is already listed
            throw new UserAlreadyExistsException("Email Already exists");
        }
        if(userRepository.existsByCredentialUserName(user.getCredential().getUserName()))
        {
            //throw a runtime exception, this username is already listed
            throw new UserAlreadyExistsException("User name Already exists");
        }
        //hardcode the role, each user is set to customer regardless the request
        //the addRole method ensures the bi-directional link
        user.addRole(new Role(UserRoles.ROLE_CUSTOMER));
        //ensure the bidirectional link between user and credential
        user.getCredential().setUser(user);

        //hash the password using the password encoder
        String encodedPassword=passwordEncoder.encode(user.getCredential().getPassword());
        user.getCredential().setPassword(encodedPassword);


        userRepository.save(user);

        return convertToDto(user);
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
        User user=userRepository.findByUserNameWithRoles(userName)
                .orElseThrow(()->new UserNotFoundException("user not found"));
        if (user.getRoles().stream().anyMatch(r -> r.getRole() == UserRoles.ROLE_CUSTOMER)) {
            return user != null && user.getAddress() != null;
        }
        return true; // Admins or other roles might not need this check
    }

    private User convertToUser(UserRegistrationDTO userRegistrationDTO){
        User registeredUser=new User(userRegistrationDTO.firstName(), userRegistrationDTO.lastName(),
                userRegistrationDTO.email(),userRegistrationDTO.phoneNumber());
        Credential credential=new Credential(userRegistrationDTO.userName(),
                userRegistrationDTO.password(),true);

        registeredUser.setCredential(credential);
        return registeredUser;
    }


    @Override
    public UserProfileResponse myProfile(){
        Long userId=securityUtil.getCurrentUserId();
        User user=userRepository.findById(userId)
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
        userRepository.save(user);
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
        userRepository.save(user);
        return new UserProfileResponse(user.getFirstName(),user.getLastName(),user.getPhoneNumber()
                ,user.getImageUrl(),user.getAddress(),user.getUpdatedAt());
    }


    private UserResponseDTO convertToDto(User user){
        List<String> roles=user.getRoles().stream().map((role)->role.getRole().name()).toList();
        UserResponseDTO responseDTO=new UserResponseDTO(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getCreatedAt(),
                roles);
        return responseDTO;
    }

    private User getCurrentUser(){
        Long id=securityUtil.getCurrentUserId();
         return userRepository.findById(id).
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
