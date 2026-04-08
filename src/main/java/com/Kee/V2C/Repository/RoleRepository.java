package com.Kee.V2C.Repository;

import com.Kee.V2C.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Long> {

    Optional<Role> findByCredentialUserName(String userName);
}
