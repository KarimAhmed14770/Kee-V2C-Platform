package com.Kee.V2C;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class V2CApplication {

	public static void main(String[] args) {
		SpringApplication.run(V2CApplication.class, args);
	}

    /*
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
    */
}
