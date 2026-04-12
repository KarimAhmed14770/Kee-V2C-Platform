package com.Kee.V2C.Repository;

import com.Kee.V2C.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface StockRepository extends JpaRepository<Stock,Long> {
    Optional<Stock> findByProductIdAndShopId(Long productId,Long inventoryId);
    @Modifying // REQUIRED for UPDATE/DELETE queries
    @Transactional // Ensures the update is committed correctly
    @Query(value =
            "UPDATE stock "+
            "SET quantity= quantity- :requiredQty "+
                    "WHERE quantity>= :requiredQty AND product_id= :productId "+
                    "AND shop_id= :shopId",
            nativeQuery = true /*basically it tells spring to use raw SQL
            so that SQL uses table names (stock) and column names (product_id).
            JPQL (default) uses Class names (Stock) and Variable names (productId).
            For an atomic math operation like quantity = quantity - X, it's much safer to use Native SQL.
            */
    )
    int decrementProductStock(int requiredQty,Long productId,Long shopId);

    @Modifying // REQUIRED for UPDATE/DELETE queries
    @Transactional // Ensures the update is committed correctly
    @Query(value =
            "UPDATE stock "+
                    "SET quantity= quantity+ :requiredQty "+
                    "WHERE  product_id= :productId "+
                    "AND shop_id= :shopId",
            nativeQuery = true /*basically it tells spring to use raw SQL
            so that SQL uses table names (stock) and column names (product_id).
            JPQL (default) uses Class names (Stock) and Variable names (productId).
            For an atomic math operation like quantity = quantity - X, it's much safer to use Native SQL.
            */
    )
    int incrementProductStock(int requiredQty,Long productId,Long shopId);
}
