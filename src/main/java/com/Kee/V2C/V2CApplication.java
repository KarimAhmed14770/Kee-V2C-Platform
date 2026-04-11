package com.Kee.V2C;

import com.Kee.V2C.utils.ImageUtil;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class V2CApplication {

	public static void main(String[] args) {
		SpringApplication.run(V2CApplication.class, args);
	}

    @Bean
    public CommandLineRunner commandLineRunner(ImageUtil imageUtil) {
        return runner ->{
            imageUtil.convertRelativeImageToBase64("ProductRequest/iphone 13.jpg");

    };


    }
}
