package com.Kee.Ecommerce.entity;

import com.Kee.Ecommerce.enums.OrderStatus;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="orders")
@EntityListeners(AuditingEntityListener.class)
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "shipping_address")
    private String ShippingAddress;

    @Column(name="status")
    @Enumerated(EnumType.STRING)
    private OrderStatus status;


    @Column(name = "total_price")
    private BigDecimal totalPrice;


    @Column(name = "ordered_at")
    @CreatedDate
    private LocalDateTime orderedAt;

    @Column(name = "delivered_at")
    private LocalDateTime delivered_at;

    //we don't want Customer details when searching for an order unless stated
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    //also when we search for an order we don't  want the order items unless stated
    //when we save or delete an order we want order items to be saved or deleted automatically
    //so cacade is all
    @OneToMany(mappedBy = "order",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    List<OrderItem> orderItems=new ArrayList<>();


    public Order(){}

    public Order(String shippingAddress, OrderStatus status) {
        ShippingAddress = shippingAddress;
        this.status = status;
    }
    public Order(String shippingAddress, OrderStatus status, BigDecimal totalPrice, Customer customer, List<OrderItem> orderItems) {
        ShippingAddress = shippingAddress;
        this.status = status;
        this.totalPrice = totalPrice;
        this.customer = customer;
        this.orderItems = orderItems;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getShippingAddress() {
        return ShippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        ShippingAddress = shippingAddress;
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

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public void addOrderItem(OrderItem orderItem){
        this.orderItems.add(orderItem);
        orderItem.setOrder(this);
    }
    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", ShippingAddress='" + ShippingAddress + '\'' +
                ", status=" + status +
                ", totalPrice=" + totalPrice +
                ", orderedAt=" + orderedAt +
                ", delivered_at=" + delivered_at +
                '}';
    }
}


/*

CREATE TABLE `orders` (
	`id` int NOT NULL AUTO_INCREMENT,-- this id is the address for the database search
	`customer_id` int NOT NULL,
    `shipping_address` varchar(128) NOT NULL,
    `status` varchar(20) default 'Pending',  -- CANCELED,PENDING,SHIPPED,DELIVERED
    `total_price` DECIMAL(10,2) NOT NULL,
    `ordered_at` datetime  not null default current_timestamp,
    `delivered_at` datetime default null,

	PRIMARY KEY (`id`),
    Key `customer_idx` (`customer_id`),
    Constraint `customer_order_fk` Foreign key (`customer_id`) REFERENCES customer(`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

 */