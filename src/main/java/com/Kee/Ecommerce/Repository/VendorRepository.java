package com.Kee.Ecommerce.Repository;



import com.Kee.Ecommerce.entity.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VendorRepository extends JpaRepository<Vendor,Long> {
    Optional<Vendor> findById(Long id);
    Optional<Vendor> findByCredentialUserName(String userName);
    Optional<Vendor> findByCredentialEmail(String email);
}
