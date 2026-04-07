package com.Kee.Ecommerce.entity;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "vendors")
public class Vendor {
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "vendor_name")
    private String name;

    @Column(name = "description")

    private String description;
    @Column(name="rating")
    private float rating;

    @Column(name="image_url")
    private String imageUrl;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private Credential credential;


    //when searching for a vendor we don't want the list of products unless stated
    @OneToMany(mappedBy = "vendor",fetch = FetchType.LAZY)
    private List<Product> products=new ArrayList<>();

    //when searching for a vendor we don't want the list of shops unless stated
    @OneToMany(mappedBy = "vendor",fetch = FetchType.LAZY)
    private List<Shop> inventories=new ArrayList<>();

    public Vendor(){}

    public Vendor(String name) {
        this.name = name;
    }

    public Vendor(String name, float rating){
        this.name = name;
        this.rating = rating;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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


    public List<Inventory> getInventories() {
        return inventories;
    }

    public void setInventories(List<Inventory> inventories) {
        this.inventories = inventories;
    }

    public void addProduct(Product product){
        products.add(product);
        product.setVendor(this);//the bi-directional link
    }

    public void addInventory(Inventory inventory){
        inventories.add(inventory);
        inventory.setVendor(this);
    }


    @Override
    public String toString() {
        return "Vendor{" +
                "id=" + id +
                ", vendor name='" + name + '\'' +
                ", rating=" + rating +
                '}';
    }
}
