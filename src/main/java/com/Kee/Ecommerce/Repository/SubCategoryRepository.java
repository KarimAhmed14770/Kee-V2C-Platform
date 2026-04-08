package com.Kee.Ecommerce.Repository;


import com.Kee.Ecommerce.entity.Category;
import com.Kee.Ecommerce.entity.SubCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubCategoryRepository extends JpaRepository<SubCategory,Long> {
    Optional<SubCategory> findByName(String Name);

    Optional<SubCategory> findById(Long id);

    Page<SubCategory> findByParentCategoryId(Long id,Pageable pageable);
    Page<SubCategory> findAllByActiveTrue(Pageable pageable);

    Page<SubCategory> findAllByActiveFalse(Pageable pageable);

    // 3. Search for categories
    Page<SubCategory> findByNameContainingIgnoreCase(String name, Pageable pageable);

    Page<SubCategory> findByDescriptionContainingIgnoreCase(String name, Pageable pageable);


    // 4. Quick existence check
    boolean existsByName(String name);
    boolean existsByNameIgnoreCase(String name);
}
