package com.Kee.V2C.Repository;

import com.Kee.V2C.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category,Long> {

    Optional<Category> findByName(String Name);

    Optional<Category> findById(Long id);

    Page<Category> findAllByActiveTrue(Pageable pageable);

    Page<Category> findAllByActiveFalse(Pageable pageable);

    // 3. Search for categories
    Page<Category> findByNameContainingIgnoreCase(String name, Pageable pageable);

    Page<Category> findByDescriptionContainingIgnoreCase(String name, Pageable pageable);


    // 4. Quick existence check
    boolean existsByName(String name);
    boolean existsByNameIgnoreCase(String name);
}
