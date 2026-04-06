package com.Kee.Ecommerce.entity;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "seller")
@EntityListeners(AuditingEntityListener.class)
public class SellerProfile {
    @Id
    @Column(name = "seller_id")
    private Long id;

    @Column(name = "shop_name")
    private String shopName;

    @Column(name = "shop_address")
    private String shopAddress;

    @Column(name="rating")
    private float rating;

    @Column(name="image_url")
    private String imageUrl;

    @Column(name = "created_at")
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @LastModifiedDate
    private LocalDateTime updatedAt;

    //when searching for a seller we most probably want all the seller info
    @OneToOne(fetch = FetchType.EAGER)
    @MapsId //maps the id of the seller to the id of the customer
    @JoinColumn(name="seller_id")
    private User user;

    //when searching for a seller we don't want the list of products unless stated
    @OneToMany(mappedBy = "sellerProfile",fetch = FetchType.LAZY)
    private List<Product> products=new ArrayList<>();

    //when searching for a seller we don't want the list of inventories unless stated
    @OneToMany(mappedBy = "seller",fetch = FetchType.LAZY)
    private List<Inventory> inventories=new ArrayList<>();

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

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
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


    public String getShopAddress() {
        return shopAddress;
    }

    public void setShopAddress(String shopAddress) {
        this.shopAddress = shopAddress;
    }

    public List<Inventory> getInventories() {
        return inventories;
    }

    public void setInventories(List<Inventory> inventories) {
        this.inventories = inventories;
    }

    public void addProduct(Product product){
        products.add(product);
        product.setSellerProfile(this);//the bi-directional link
    }

    public void addInventory(Inventory inventory){
        inventories.add(inventory);
        inventory.setSeller(this);
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
