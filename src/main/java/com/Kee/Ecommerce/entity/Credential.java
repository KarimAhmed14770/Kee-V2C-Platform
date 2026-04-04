package com.Kee.Ecommerce.entity;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "users_credentials")
@EntityListeners(AuditingEntityListener.class)
public class Credential {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "password")
    private String password;

    @Column(name = "created_at")
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Column(name = "enabled")
    private boolean enabled;

    //when we search for a credential we don't want to fetch the user info unless stated
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name="user_id")
    private User user;

    public Credential(){}

    public Credential(String userName, String password, boolean enabled) {
        this.userName = userName;
        this.password = password;
        this.enabled = enabled;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Credential{" +
                "userName='" + userName + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", enabled=" + enabled +
                '}';
    }
}


/*
CREATE TABLE `users_credentials` (
        `id` int NOT NULL AUTO_INCREMENT,-- this id is the address for the database search
  `user_name` varchar(45) NOT NULL,
  `password` varchar(255) NOT NULL,-- bcrypt just needs 68 char, but we make it 255 to give ourself a space for more complex algo in future
  `created_at` datetime NOT NULL,
        `updated_at` datetime,
        `enabled` boolean NOT NULL default 1,
        `user_id` int NOT NULL,
PRIMARY KEY (`id`),
key `user_name_idx` (`user_name`),
constraint `Fk_credentials_user` FOREIGN KEY (`user_id`) REFERENCES `users`(`id`),
constraint `unique_user_name` UNIQUE (`user_name`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;


 */