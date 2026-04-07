package com.Kee.Ecommerce.dto;



public record CustomerRegistrationDTO(String firstName,
                                      String lastName,
                                      String email,
                                      String phoneNumber,
                                      String userName,
                                      String password) {}
