package com.Kee.Ecommerce.Repository;

import com.Kee.Ecommerce.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category,Long> {

    Optional<Category> findByName(String Name);

    //Show only active categories with pagination
    Page<Category> findByActiveTrue(Pageable pageable);

    // 3. Search for categories
    Page<Category> findByNameContainingIgnoreCase(String name, Pageable pageable);

    // 4. Quick existence check
    boolean existsByName(String name);
}
