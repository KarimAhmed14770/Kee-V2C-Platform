package com.Kee.Ecommerce.service;

import com.Kee.Ecommerce.dto.UserResponseDTO;
import com.Kee.Ecommerce.enums.UserRoles;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AdminService {
    void promoteToSeller(Long userId);
    UserResponseDTO getUserById(Long Id);
    UserResponseDTO getUserByUsername(String userName);
    UserResponseDTO getUserByEmail(String email);
    Page<UserResponseDTO> getAllUsers(Pageable page);
    Page<UserResponseDTO> getAllUsersByRole(UserRoles role, Pageable page);

}
