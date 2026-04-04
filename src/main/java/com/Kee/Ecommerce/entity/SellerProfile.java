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
    private Long id;

    @Column(name = "shop_name")
    private String shopName;

    @Column(name="rating")
    private float rating;

    @Column(name="image_url")
    private String imageUrl;
    //when searching for a seller we most probably want all the seller info
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="user_id")
    private User user;

    //when searching for a seller we don't want the list of products unless stated
    @OneToMany(mappedBy = "sellerProfile",fetch = FetchType.LAZY)
    private List<Product> products=new ArrayList<>();

    public SellerProfile(){}

    public SellerProfile(String shopName, User user) {
        this.shopName = shopName;
        this.user=user;
    }

    public SellerProfile(String shopName, float rating, User user) {
        this.shopName = shopName;
        this.rating = rating;
        this.user = user;
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

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public void addProduct(Product product){
        products.add(product);
        product.setSellerProfile(this);//the bi-directional link
    }


    @Override
    public String toString() {
        return "SellerProfile{" +
                "id=" + id +
                ", shopName='" + shopName + '\'' +
                ", rating=" + rating +
                '}';
    }
}
