package com.Kee.V2C.entity;


import com.Kee.V2C.enums.ProductModelStatus;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="product_models")
public class ProductModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "image_url")
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "owner_vendor_id")
    private Vendor vendor;

    @Column(name = "is_global")
    private boolean isGlobal;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private ProductModelStatus status;

    @ManyToOne
    @JoinColumn(name = "Brand_id")
    private Brand brand;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private SubCategory category;

    @OneToMany(mappedBy = "productModel")
    private List<Product> products=new ArrayList<>();

    public ProductModel(){}

    public ProductModel(String name, String description, String imageUrl, Vendor vendor,
                        boolean isGlobal, ProductModelStatus status, Brand brand, SubCategory category) {
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.vendor = vendor;
        this.isGlobal = isGlobal;
        this.status = status;
        this.brand = brand;
        this.category = category;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Vendor getVendor() {
        return vendor;
    }

    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
    }

    public boolean isGlobal() {
        return isGlobal;
    }

    public void setGlobal(boolean global) {
        isGlobal = global;
    }

    public ProductModelStatus getStatus() {
        return status;
    }

    public void setStatus(ProductModelStatus status) {
        this.status = status;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public SubCategory getCategory() {
        return category;
    }

    public void setCategory(SubCategory category) {
        this.category = category;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return "ProductModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", status=" + status +
                '}';
    }
}
