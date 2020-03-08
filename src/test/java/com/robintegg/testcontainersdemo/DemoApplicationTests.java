package com.robintegg.testcontainersdemo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@ContextConfiguration(initializers = DemoApplicationTests.Initializer.class)
@Testcontainers
class DemoApplicationTests {

	@Container
	static GenericContainer postgres = new GenericContainer("postgres")
			.withEnv("POSTGRES_PASSWORD", "mysecretpassword").withExposedPorts(5432);

	@Container
	static GenericContainer activemq = new GenericContainer("rmohr/activemq").withExposedPorts(61616);

	@Container
	static GenericContainer rabbitmq = new GenericContainer("rabbitmq").withExposedPorts(5672);

	@Test
	void contextLoads() {
	}

	static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

		@Override
		public void initialize(ConfigurableApplicationContext configurableApplicationContext) {

			TestPropertyValues.of(

					"spring.datasource.url=jdbc:postgresql://localhost:" + postgres.getMappedPort(5432) + "/postgres",
					"spring.rabbitmq.port=" + rabbitmq.getMappedPort(5672),
					"spring.activemq.broker-url=tcp://localhost:" + activemq.getMappedPort(61616)

			).applyTo(configurableApplicationContext.getEnvironment());

		}

	}

}
