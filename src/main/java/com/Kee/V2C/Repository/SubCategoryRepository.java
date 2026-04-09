package com.Kee.V2C.Repository;


import com.Kee.V2C.entity.SubCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubCategoryRepository extends JpaRepository<SubCategory,Long> {
    Optional<SubCategory> findById(Long id);

    @Query(value = "Select s from SubCategory s "
    +"Where s.parentCategory.id= :id")
    Page<SubCategory> findByParentId(@Param("id") Long id,Pageable page);
    boolean existsByNameIgnoreCase(String name);
}
