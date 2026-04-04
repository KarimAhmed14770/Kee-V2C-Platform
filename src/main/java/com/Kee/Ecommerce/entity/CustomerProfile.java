package com.Kee.Ecommerce.entity;


import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "customer")
@EntityListeners(AuditingEntityListener.class)
public class CustomerProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name="image_url")
    private String imageUrl;

    @Column(name = "created_at")
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Column(name="active")
    private boolean active;

    @OneToOne(fetch=FetchType.EAGER)//when searching for a customer yes we want the rest of customer
    //info which will be stored inside this user object
    @JoinColumn(name="user_id")
    private User user;

    //when we search for a customer we don't want the list of orders for that customer unless stated
    @OneToMany(mappedBy = "customerProfile",fetch = FetchType.LAZY)
    private List<Order> orders=new ArrayList<>();


    public CustomerProfile(){}

    public CustomerProfile(User user, List<Order> orders,boolean active) {
        this.user = user;
        this.orders = orders;
        this.active=active;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "CustomerProfile{" +
                "id=" + id +
                '}';
    }
}



/*

CREATE TABLE `customer` (
	`id` int NOT NULL AUTO_INCREMENT,-- this id is the address for the database search
    `user_id` int NOT NULL,
	PRIMARY KEY (`id`),
	constraint `Fk_customer_user` FOREIGN KEY (`user_id`) REFERENCES `users`(`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;



 */