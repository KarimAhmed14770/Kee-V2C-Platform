package com.Kee.Ecommerce.entity;

import com.Kee.Ecommerce.enums.UserRoles;
import jakarta.persistence.*;

@Entity
@Table(name="users_roles")
public class Role {
    @Id
    @Column(name = "user_id")
    private Long id;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private UserRoles role;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private Credential credential;

    public Role(){}


    public Role(UserRoles role) {
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserRoles getRole() {
        return role;
    }

    public void setRole(UserRoles role) {
        this.role = role;
    }

    public Credential getCredential() {
        return credential;
    }

    public void setCredential(Credential credential) {
        this.credential = credential;
    }

    @Override
    public String toString() {
        return "Role{" +
                "role=" + role +
                ", id=" + id +
                '}';
    }
}