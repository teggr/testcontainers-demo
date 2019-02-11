package com.robintegg.testcontainersdemo.routing;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.PostgreSQLContainer;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ContextConfiguration(initializers = { NotificationRepositoryTest.Initializer.class })
public class NotificationRepositoryTest {

	@ClassRule
	public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:latest");

	@Autowired
	private NotificationRepository repository;

	@Test
	public void shouldStoreEachNotification() {

		// given
		repository.save(new Notification("message1", "test"));
		repository.save(new Notification("message2", "test"));

		// when
		long count = repository.count();

		// then
		assertThat(count, is(2L));

	}

	@Test
	public void shouldStoreEachNotificationWithAUniqueIdentifier() {

		// given
		Notification n1 = repository.save(new Notification("message3", "test"));
		Notification n2 = repository.save(new Notification("message4", "test"));

		// when
		Notification persistedNotification1 = repository.getOne(n1.getId());
		Notification persistedNotification2 = repository.getOne(n2.getId());

		// then
		assertThat(persistedNotification1, equalTo(n1));
		assertThat(persistedNotification2, equalTo(n2));

	}

	static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

		@Override
		public void initialize(ConfigurableApplicationContext configurableApplicationContext) {

			DemoApplicationTestPropertyValues.using(postgreSQLContainer)
					.applyTo(configurableApplicationContext.getEnvironment());

		}

	}

}
