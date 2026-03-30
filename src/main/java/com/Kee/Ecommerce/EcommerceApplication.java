package com.Kee.Ecommerce;

import com.Kee.Ecommerce.Repository.UserRepository;
import com.Kee.Ecommerce.entity.Credential;
import com.Kee.Ecommerce.entity.Role;
import com.Kee.Ecommerce.entity.User;
import com.Kee.Ecommerce.enums.UserRoles;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@EnableJpaAuditing
public class EcommerceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcommerceApplication.class, args);
	}

    @Bean
    public CommandLineRunner commandLineRunner(UserRepository userRepository) {
        return runner ->{
            User user=new User("karim","ahmed","karim.ahmed2580@gmail.com");
            Credential credential=new Credential("kimo123","abcd",true);
            List<Role> roles=new ArrayList<>();
            roles.add(new Role(UserRoles.ROLE_CUSTOMER));

            user.setCredential(credential);
            credential.setUser(user);//making the bidirectional link
            user.setRoles(roles);
            roles.get(0).setUser(user);//making the bidirectional link
            userRepository.save(user);

    };


    }
}
