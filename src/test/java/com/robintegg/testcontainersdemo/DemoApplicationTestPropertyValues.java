package com.robintegg.testcontainersdemo;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.PostgreSQLContainer;

public class DemoApplicationTestPropertyValues {

    public static void populateRegistryFromContainers(DynamicPropertyRegistry registry,
                                                             PostgreSQLContainer<?> postgreSQLContainer,
                                                             GenericContainer<?> activeMQContainer, GenericContainer<?> rabbitMQContainer) {

        populateRegistryFromPostgresContainer(registry, postgreSQLContainer);

        // activemq
        registry.add("spring.activemq.broker-url", () -> "tcp://localhost:" + activeMQContainer.getMappedPort(61616));

        // rabbitmq
        registry.add("spring.rabbitmq.port", () -> rabbitMQContainer.getMappedPort(5672));

    }

    public static void populateRegistryFromPostgresContainer(DynamicPropertyRegistry registry, PostgreSQLContainer<?> postgreSQLContainer) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
    }
}
