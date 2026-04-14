package com.Kee.V2C.Repository;

import com.Kee.V2C.entity.Brand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandRepository extends JpaRepository<Brand,Long> {
    boolean existsByNameIgnoreCase(String name);
    Page<Brand> findByActiveTrue(Pageable page);
}
