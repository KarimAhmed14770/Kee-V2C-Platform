package com.Kee.Ecommerce.Repository;

import com.Kee.Ecommerce.entity.Customer;
import com.Kee.Ecommerce.enums.UserRoles;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> {


    Optional<Customer> findByCredentialUserName(String userName);
    Optional<Customer> findByCredentialEmail(String email);


    //return all users


    boolean existsByCredentialEmail(String email);

    boolean existsByCredentialUserName(String userName);


}
