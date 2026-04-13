package com.Kee.V2C.service;


import com.Kee.V2C.dto.cart.CartItemRequest;
import com.Kee.V2C.dto.cart.CartResponse;
import com.Kee.V2C.dto.customer.*;

public interface CustomerService {
    CustomerProfileResponse myProfile();
    CustomerProfileResponse partialUpdateCustomerProfile(CustomerUpdateProfileRequest updateRequest);
    CartResponse addToCart(CartItemRequest cartItemRequest);
    CartResponse viewMyCart();
    CheckoutResponse checkOut(CheckOutRequest checkOutRequest);
    InvoiceResponse generateInvoice(long orderId);
}
