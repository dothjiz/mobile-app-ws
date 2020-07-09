package com.doth.app.ws;

import com.doth.app.ws.security.AppProperty;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class MobileAppWsApplication {

    public static void main(String[] args) {
        SpringApplication.run(MobileAppWsApplication.class, args);
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return  new BCryptPasswordEncoder();
    }

    @Bean
    public SpringApplicationContext springApplicationContext(){
        return  new SpringApplicationContext();
    }

    @Bean//(name="appProperty")
    public AppProperty getAppProperty(){
        return  new AppProperty();
    }
}
