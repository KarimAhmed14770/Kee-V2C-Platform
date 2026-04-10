package com.Kee.V2C.Repository;

import com.Kee.V2C.entity.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductModelRepository extends JpaRepository<ProductModel,Long>,
        JpaSpecificationExecutor<ProductModel> {
}
