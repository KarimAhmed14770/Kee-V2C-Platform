package com.Kee.V2C.Repository;

import com.Kee.V2C.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

    //findById is already supported

    //find by strict product name
    Optional<Product> findByName(String name);

    //find by strict category name
    Page<Product> findByProductModelCategoryName(String name, Pageable pageable);

    //find by categoryID
    Page<Product> findByProductModelCategoryId(Long categoryId, Pageable pageable);

    //find by name that is not strict
    Page<Product> findByNameContainingIgnoreCase(String name, Pageable pageable);

    //finding by seller id
    Page<Product> findByVendorId(Long id,Pageable pageable);

    //returning only active products by category id
    Page<Product> findByProductModelCategoryIdAndActiveTrue(Long categoryId, Pageable pageable);

    //returning only active products  by name containing
    Page<Product> findByNameContainingIgnoreCaseAndActiveTrue(String name, Pageable pageable);

    //returning only active products  by description containing
    Page<Product> findByDescriptionContainingIgnoreCaseAndActiveTrue(String name, Pageable pageable);

}
