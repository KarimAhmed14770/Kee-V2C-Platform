package com.Kee.V2C.Repository;

import com.Kee.V2C.entity.Order;
import com.Kee.V2C.enums.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order,Long> {

    Page<Order> findByCustomerId(Long id, Pageable pageable);

    Page<Order> findByStatus(OrderStatus status, Pageable pageable);

    @Query(value="SELECT o FROM Order o " +
            "JOIN FETCH o.orderItems " +
            "WHERE o.id= :id")
    Optional<Order> findByIdWithItemsDetails(Long id);
}
