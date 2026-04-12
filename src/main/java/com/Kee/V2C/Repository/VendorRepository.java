package com.Kee.V2C.Repository;



import com.Kee.V2C.entity.Customer;
import com.Kee.V2C.entity.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VendorRepository extends JpaRepository<Vendor,Long>, JpaSpecificationExecutor<Vendor> {
    Optional<Vendor> findById(Long id);

    @Query(value = "SELECT v from Vendor v "+
    "JOIN FETCH v.credential "
    +"WHERE v.id= :id")
    Optional<Vendor> findByIdWithCredentials(@Param("id")Long id);

    @Query(value = "SELECT v from Vendor v "+
            "LEFT JOIN FETCH v.shop s "
            +"LEFT JOIN FETCH s.stocks "
            +"WHERE v.id= :id")
    Optional<Vendor> findByIdWithShopWithStock(@Param("id")Long id);


    Optional<Vendor> findByCredentialUserName(String userName);
    Optional<Vendor> findByCredentialEmail(String email);
}
