package com.Kee.Ecommerce.dto;

import com.Kee.Ecommerce.entity.Role;
import com.Kee.Ecommerce.entity.User;
import com.Kee.Ecommerce.enums.UserRoles;

import java.time.LocalDateTime;
import java.util.List;

public class UserResponseDTO {

    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private String phoneNumber;
    private LocalDateTime createdAt;
    private List<String> roles;//return only the role name not the whole object

    public UserResponseDTO(long id, String firstName, String lastName, String email, String address
            , String phoneNumber, List<String> roles,LocalDateTime createdAt) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.roles = roles;
        this.createdAt=createdAt;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
