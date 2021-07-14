package org.baat.channel.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("org.baat.channel.repository")
@EntityScan("org.baat.channel.repository.entity")
@ComponentScan("org.baat")
public class ChannelApplication {
    public static void main(String[] args) {
        SpringApplication.run(ChannelApplication.class, args);
    }
}
