package com.Kee.Ecommerce.Repository;



import com.Kee.Ecommerce.entity.SellerProfile;
import com.Kee.Ecommerce.entity.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VendorRepository extends JpaRepository<Vendor,Long> {
    Optional<Vendor> findByUserId(Long id);
}
