package com.Kee.V2C.Repository;

import com.Kee.V2C.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    //returning only active products  by description containing
    Page<Product> findByDescriptionContainingIgnoreCaseAndActiveTrue(String name, Pageable pageable);

}
