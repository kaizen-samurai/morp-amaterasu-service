package org.ks.trial.morp.amaterasu;

import org.ks.trial.morp.amaterasu.constants.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.http.converter.protobuf.ProtobufHttpMessageConverter;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.net.InetAddress;
import java.net.UnknownHostException;

@EnableEurekaClient
@EnableFeignClients
@SpringBootApplication
public class MorpAmaterasuServer {
    private static final Logger LOGGER = LoggerFactory.getLogger(MorpAmaterasuServer.class);
    @Autowired
    private Environment environment;

    public static void main(String[] args) {
        SpringApplication.run(MorpAmaterasuServer.class, args);
    }

    @Bean
    ProtobufHttpMessageConverter protobufHttpMessageConverter() {
        return new ProtobufHttpMessageConverter();
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("org.ks.trial.morp.amaterasu"))
                .build();
    }

    @EventListener(ApplicationReadyEvent.class)
    public void firedUpAllCylinders() {
        String host = Constants.LOCALHOST;
        try {
            host = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            LOGGER.error("Failed to obtain ip address. ", e);
        }
        String port = environment.getProperty(Constants.LOCAL_SPRING_PORT);
        LOGGER.info("Amaterasu activation is complete, be ready to get burnt! Swagger running on: {}",
                String.format(Constants.SWAGGER_UI_URL, host, port));
    }
}
