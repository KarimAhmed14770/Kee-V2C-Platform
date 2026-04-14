package com.Kee.V2C.entity;

import com.Kee.V2C.enums.OrderStatus;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "sub_orders")
@EntityListeners(AuditingEntityListener.class)
public class SubOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name="status")
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column(name = "total_price")
    BigDecimal totalPrice;

    @Column(name = "ordered_at")
    @CreatedDate
    private LocalDateTime orderedAt;

    @Column(name = "delivered_at")
    private LocalDateTime delivered_at;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "vendor_id")
    private Vendor vendor;


    public SubOrder(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Vendor getVendor() {
        return vendor;
    }

    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public LocalDateTime getOrderedAt() {
        return orderedAt;
    }

    public void setOrderedAt(LocalDateTime orderedAt) {
        this.orderedAt = orderedAt;
    }

    public LocalDateTime getDelivered_at() {
        return delivered_at;
    }

    public void setDelivered_at(LocalDateTime delivered_at) {
        this.delivered_at = delivered_at;
    }
}
