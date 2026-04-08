package com.Kee.V2C.service;


import com.Kee.V2C.dto.*;

public interface CustomerService {
    CustomerProfileResponse myProfile();
    CustomerProfileResponse partialUpdateCustomerProfile(CustomerProfileRequest updateRequest);
    CartResponse addToCart(CartItemRequest cartItemRequest);
    CartResponse viewMyCart();
    CheckoutResponse checkOut(CheckOutRequest checkOutRequest);
    InvoiceResponse generateInvoice(long orderId);
}
