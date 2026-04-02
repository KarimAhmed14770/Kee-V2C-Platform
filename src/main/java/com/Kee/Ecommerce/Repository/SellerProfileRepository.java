package com.Kee.Ecommerce.Repository;



import com.Kee.Ecommerce.entity.SellerProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SellerProfileRepository extends JpaRepository<SellerProfile,Long> {
    Optional<SellerProfile> findByUserId(Long id);
}
