package com.Kee.Ecommerce.entity;


import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "customer")
public class CustomerProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne(fetch=FetchType.EAGER)//when searching for a customer yes we want the rest of customer
    //info which will be stored inside this user object
    @JoinColumn(name="user_id")
    private User user;

    //when we search for a customer we don't want the list of orders for that customer unless stated
    @OneToMany(mappedBy = "customerProfile",fetch = FetchType.LAZY)
    private List<Order> orders=new ArrayList<>();


    public CustomerProfile(){}

    public CustomerProfile(User user, List<Order> orders) {
        this.user = user;
        this.orders = orders;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUserId() {
        return user;
    }

    public void setUserId(User user) {
        this.user = user;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    @Override
    public String toString() {
        return "CustomerProfile{" +
                "id=" + id +
                ", user=" + user +
                ", orders=" + orders +
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