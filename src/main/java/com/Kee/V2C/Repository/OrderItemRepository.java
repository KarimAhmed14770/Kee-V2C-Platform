package com.Kee.V2C.Repository;

import com.Kee.V2C.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem,Long> {
    List<OrderItem> findBySubOrderId(Long id);
}
