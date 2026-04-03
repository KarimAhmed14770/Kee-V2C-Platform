package com.Kee.Ecommerce.Repository;

import com.Kee.Ecommerce.entity.Role;
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
    Optional<User> findByCredentialUserName(String userName);

    @Query("SELECT u FROM User u "+
            "JOIN FETCH u.roles "
            +"WHERE u.id= :id")
    Optional<User> findByIdWithRoles(@Param("id") Long id);

    @Query("SELECT u FROM User u "+
    "JOIN FETCH u.credential "+
    "JOIN FETCH u.roles "
    +"WHERE u.credential.userName= :username")
    Optional<User> findByUserNameWithRoles(@Param("username") String userName);

    @Query("SELECT u FROM User u JOIN u.roles r WHERE r.role = :roleName")
    Page<User> findAllByRoleName(@Param("roleName") UserRoles roleName, Pageable pageable);

    //return all users


    boolean existsByEmail(String email);

    boolean existsByCredentialUserName(String userName);

    /*searches for a user with this id and this role*/
    boolean existsByIdAndRoles_Role(Long id, UserRoles role);

    @Query("SELECT u FROM User u " +
            "JOIN FETCH u.roles " +
            "WHERE u.id IN (SELECT r2.user.id FROM Role r2 WHERE r2.role = :userRole)")
    Page<User> findUsersByRole(@Param("userRole") UserRoles userRole, Pageable pageable);

}
