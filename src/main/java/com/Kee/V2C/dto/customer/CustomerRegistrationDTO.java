package com.Kee.V2C.dto.customer;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CustomerRegistrationDTO(@NotBlank(message = "Name can't be empty")
                                      @Pattern(regexp = "^[a-zA-Z\\-']+$",message = "name can contain only letters, hyphens" +
                                              "or apostrophes")
                                      @Size(min=2,max=50,message = "min is 2 characters, max is 50")
                                      String firstName,

                                      @NotBlank(message = "Name can't be empty")
                                      @Pattern(regexp = "^[a-zA-Z\\-']+$",message = "name can contain only letters, hyphens" +
                                              "or apostrophes")
                                      @Size(min=2,max=50,message = "min is 2 characters, max is 50")
                                      String lastName,

                                      @NotBlank(message = "email is required")
                                      @Email(message = "Please provide a valid email address")
                                      @Pattern(
                                              regexp = "^[A-Za-z0-9+_.-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
                                              message = "Email format is invalid"
                                      )String email,
                                      @Size(min=11,max = 11,message = "enter a valid egyptian mobile number")
                                      @Pattern(
                                              regexp = "^01[0125][0-9]{8}$",
                                              message = "Phone number must be a valid Egyptian mobile number (11 digits starting with 010, 011, 012, or 015)"
                                      )
                                      String phoneNumber,

                                      @NotBlank(message = "field required")
                                      @Size(min=5,message = "minimum is 5 characters")
                                      String userName,

                                      @NotBlank(message = "Password is required")
                                      @Size(min = 8, message = "Password must be at least 8 characters long")
                                      @Pattern(
                                              regexp = "^(?=.*[A-Z])(?=.*[@#$%^&+=!]).*$",
                                              message = "Password must contain at least one uppercase letter and one special character"
                                      )
                                      String password) {}
