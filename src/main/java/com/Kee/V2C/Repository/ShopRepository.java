package com.Kee.V2C.Repository;


import com.Kee.V2C.entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShopRepository extends JpaRepository<Shop,Long> {

    Optional<Shop> findByVendorId(Long id);
    Boolean existsByVendorId(Long vendorId);
}
