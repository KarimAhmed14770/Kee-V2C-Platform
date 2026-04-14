package com.Kee.V2C.Repository;

import com.Kee.V2C.entity.Order;
import com.Kee.V2C.entity.SubOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface SubOrderRepository extends JpaRepository<SubOrder,Long> {
    @Query(value="SELECT o FROM SubOrder o " +
            "JOIN FETCH o.orderItems " +
            "WHERE o.id= :id")
    Optional<Order> findByIdWithItemsDetails(Long id);
}
