package com.Kee.Ecommerce.Repository;

import com.Kee.Ecommerce.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepository extends JpaRepository<Stock,Long> {

}
