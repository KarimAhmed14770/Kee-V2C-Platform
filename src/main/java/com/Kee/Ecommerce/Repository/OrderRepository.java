package com.Kee.Ecommerce.Repository;

import com.Kee.Ecommerce.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {

    Page<Order> findByCustomerProfileId(Long id, Pageable pageable);

    Page<Order> findByStatus(Long id, Pageable pageable);
}
