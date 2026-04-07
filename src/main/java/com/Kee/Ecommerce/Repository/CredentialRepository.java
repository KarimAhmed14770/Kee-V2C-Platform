package com.Kee.Ecommerce.Repository;

import com.Kee.Ecommerce.entity.Credential;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CredentialRepository extends JpaRepository<Credential,Long> {
}
