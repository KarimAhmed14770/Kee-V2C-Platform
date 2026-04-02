package com.Kee.Ecommerce.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="category")
public class Category {

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

    @Column(name = "active")
    private boolean active;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "category")
    List<Product> products=new ArrayList<>();

    public Category(){}

    public Category(String name, String description, String imageUrl, boolean active, List<Product> products) {
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.active = active;
        this.products = products;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", active=" + active +
                ", products=" + products +
                '}';
    }
}

/*
CREATE TABLE `category` (
	`id` int NOT NULL AUTO_INCREMENT,-- this id is the address for the database search
	`name` varchar(45) NOT NULL,
    `description` varchar(1000) default Null,
    `image_url` varchar(1000) default Null,
    `active` boolean Not Null DEFAULT TRUE,
	PRIMARY KEY (`id`),
    constraint `unique_category_name` UNIQUE (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;
 */
