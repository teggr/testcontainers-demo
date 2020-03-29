package com.robintegg.testcontainersdemo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@Testcontainers
class DemoApplicationTests {

    @Container
    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:latest");

    @Container
    static GenericContainer activemq = new GenericContainer("rmohr/activemq").withExposedPorts(61616);

    @Container
    static GenericContainer rabbitmq = new GenericContainer("rabbitmq").withExposedPorts(5672);


    @DynamicPropertySource
    static void registerDynamicProperties(DynamicPropertyRegistry registry) {
        DemoApplicationTestPropertyValues.populateRegistryFromContainers(registry, postgreSQLContainer, activemq, rabbitmq);
    }


    @Test
    void contextLoads() {
    }

}
