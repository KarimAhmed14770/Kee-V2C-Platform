package com.Kee.Ecommerce.Repository;

import com.Kee.Ecommerce.entity.Credential;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CredentialRepository extends JpaRepository<Credential,Long> {
    boolean existsByEmail(String email);
    boolean existsByUserName(String userName);


    Optional<Credential> findByUserName(String userName);
}
