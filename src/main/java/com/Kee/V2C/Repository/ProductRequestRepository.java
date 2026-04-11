package com.Kee.V2C.Repository;

import com.Kee.V2C.entity.ProductRequest;
import com.Kee.V2C.enums.ProductRequestStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRequestRepository extends JpaRepository<ProductRequest,Long> {
    Page<ProductRequest> findByStatus(ProductRequestStatus status,Pageable page);
}
