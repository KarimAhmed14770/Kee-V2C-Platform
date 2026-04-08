package com.Kee.V2C.Repository;

import com.Kee.V2C.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long>, JpaSpecificationExecutor<Customer> {


    Optional<Customer> findByCredentialUserName(String userName);
    Optional<Customer> findByCredentialEmail(String email);


    //return all users


    boolean existsByCredentialEmail(String email);

    boolean existsByCredentialUserName(String userName);


}
