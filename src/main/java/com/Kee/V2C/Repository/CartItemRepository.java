package com.Kee.V2C.Repository;

import com.Kee.V2C.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem,Long> {
    Optional<CartItem> findByCustomerIdAndProductId(Long userId, Long productId);

    boolean existsByCustomerIdAndProductId(Long userId, Long productId);

    List<CartItem> findAllByUserId(Long userId);

    @Query("SELECT ci FROM CartItem ci " +
            "JOIN FETCH ci.product p " +
            "JOIN FETCH p.vendor v " +
            "JOIN FETCH v.shop " +
            "WHERE ci.customer.id = :customerId")
    List<CartItem> findByCustomerIdWithDetails(@Param("customerId") Long customerId);
}
