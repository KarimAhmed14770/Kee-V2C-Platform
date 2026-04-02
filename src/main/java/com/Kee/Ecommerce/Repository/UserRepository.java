package com.Kee.Ecommerce.Repository;

import com.Kee.Ecommerce.entity.User;
import com.Kee.Ecommerce.enums.UserRoles;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByEmail(String email);

    @Query("SELECT u FROM User u "+
    "JOIN FETCH u.credential "+
    "JOIN FETCH u.roles "
    +"WHERE u.credential.userName= :username")
    Optional<User> findByUserNameWithRoles(@Param("username") String userName);
    boolean existsByEmail(String email);

    boolean existsByCredentialUserName(String userName);

    @Query("SELECT DISTINCT u FROM User u " +
            "JOIN FETCH u.roles r " +
            "WHERE r.role = :userRole")
    Page<User> findUsersByRole(@Param("userRole") UserRoles userRole, Pageable pageable);


}
