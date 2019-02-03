package com.robintegg.testcontainersdemo;

import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.GenericContainer;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(initializers = DemoApplicationTests.Initializer.class)
public class DemoApplicationTests {

	@ClassRule
	public static GenericContainer postgres = new GenericContainer("postgres")
			.withEnv("POSTGRES_PASSWORD", "mysecretpassword").withExposedPorts(5432);

	@ClassRule
	public static GenericContainer activemq = new GenericContainer("rmohr/activemq").withExposedPorts(61616);

	@ClassRule
	public static GenericContainer rabbitmq = new GenericContainer("rabbitmq").withExposedPorts(5672);

	@Test
	public void contextLoads() {
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
