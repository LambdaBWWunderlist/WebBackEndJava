package com.e94.wunderlist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

//@EnableJpaAuditing
@SpringBootApplication
public class WunderlistApplication {


    public static void main(String[] args) {
        SpringApplication.run(WunderlistApplication.class, args);

    }

}
