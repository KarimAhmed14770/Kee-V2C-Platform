package com.Kee.V2C.dto.customer;



public record CustomerRegistrationDTO(String firstName,
                                      String lastName,
                                      String email,
                                      String phoneNumber,
                                      String userName,
                                      String password) {}
