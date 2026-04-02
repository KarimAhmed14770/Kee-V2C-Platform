package com.Kee.Ecommerce.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "seller")
public class SellerProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    //when searching for a seller we most probably want all the seller info
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="user_id")
    private User user;

    //when searching for a seller we don't want the list of products unless stated
    @OneToMany(mappedBy = "sellerProfile",fetch = FetchType.LAZY)
    private List<Product> products=new ArrayList<>();

    public SellerProfile(){}

    public SellerProfile(User user, List<Product> products) {
        this.user = user;
        this.products = products;
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

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return "SellerProfile{" +
                "id=" + id +
                ", user=" + user +
                ", products=" + products +
                '}';
    }
}
