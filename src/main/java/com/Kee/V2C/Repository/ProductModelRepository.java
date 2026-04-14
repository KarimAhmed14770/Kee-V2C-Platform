package com.Kee.V2C.Repository;

import com.Kee.V2C.entity.ProductModel;
import com.Kee.V2C.enums.ProductModelStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductModelRepository extends JpaRepository<ProductModel,Long>,
        JpaSpecificationExecutor<ProductModel> {
    Page<ProductModel> findByOwnerId(Long id, Pageable page);

}
