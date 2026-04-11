package com.Kee.V2C.entity;


import com.Kee.V2C.enums.ProductRequestStatus;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "add_product_model_requests")
public class ProductRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name="image_url")
    private String imageUrl;

    @Column(name="is_global")
    private Boolean isGlobal;//is it global, can be available for other vendors to sell, or local

    @Column(name = "added_at")
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(name="status")
    @Enumerated(EnumType.STRING)
    private ProductRequestStatus status;

    @ManyToOne
    @JoinColumn(name = "vendor_id")
    private Vendor vendor;

    public ProductRequest(){}

    public ProductRequest(String name, String description, String imageUrl, Boolean isGlobal, ProductRequestStatus status, Vendor vendor) {
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.isGlobal = isGlobal;
        this.status = status;
        this.vendor = vendor;
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

    public Boolean getGlobal() {
        return isGlobal;
    }

    public void setGlobal(Boolean global) {
        isGlobal = global;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public ProductRequestStatus getStatus() {
        return status;
    }

    public void setStatus(ProductRequestStatus status) {
        this.status = status;
    }

    public Vendor getVendor() {
        return vendor;
    }

    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
    }

    @Override
    public String toString() {
        return "ProductRequest{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", isGlobal=" + isGlobal +
                ", createdAt=" + createdAt +
                ", status=" + status+" }";
    }
}
