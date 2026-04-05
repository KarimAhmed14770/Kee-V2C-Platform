package com.Kee.Ecommerce.entity;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Table(name="cart_item")
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "added_at")
    @LastModifiedDate
    private LocalDateTime addedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="customer_id")
    private CustomerProfile customerProfile;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    public CartItem(){}
    public CartItem(int quantity, CustomerProfile customerProfile, Product product) {
        this.quantity = quantity;
        this.customerProfile = customerProfile;
        this.product = product;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public LocalDateTime getAddedAt() {
        return addedAt;
    }

    public void setAddedAt(LocalDateTime addedAt) {
        this.addedAt = addedAt;
    }

    public CustomerProfile getCustomerProfile() {
        return customerProfile;
    }

    public void setCustomerProfile(CustomerProfile customerProfile) {
        this.customerProfile = customerProfile;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}

/*

CREATE TABLE `cart_item` (
	`id` int NOT NULL AUTO_INCREMENT,-- this id is the address for the database search
    `user_id` int NOT NULL,
    `product_id` int NOT NULL,
    `quantity` int NOT NULL,
    `added_at` datetime Not null default current_timestamp On update current_timestamp,
	PRIMARY KEY (`id`),
	constraint `Fk_customer_cart` FOREIGN KEY (`user_id`) REFERENCES `users`(`id`),
	constraint `Fk_product_cart` FOREIGN KEY (`product_id`) REFERENCES `products`(`id`)

) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

CREATE INDEX idx_customerCart_id ON cart_item(user_id);

 */
