package com.Kee.Ecommerce.entity;

import com.Kee.Ecommerce.enums.UserRoles;
import jakarta.persistence.*;

@Entity
@Table(name="users_roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private UserRoles role;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;



    public Role(){}


    public Role(UserRoles role) {
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UserRoles getRole() {
        return role;
    }

    public void setRole(UserRoles role) {
        this.role = role;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Role{" +
                "role=" + role +
                ", id=" + id +
                '}';
    }
}

/*

CREATE TABLE `users_roles` (
  `id` int NOT NULL AUTO_INCREMENT,-- this id is the address for the database search
  `role` varchar(45) NOT NULL,
  `user_id` int NOT NULL,
  PRIMARY KEY (`id`),
  constraint `Fk_roles_user` FOREIGN KEY (`user_id`) REFERENCES `users`(`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;



 */