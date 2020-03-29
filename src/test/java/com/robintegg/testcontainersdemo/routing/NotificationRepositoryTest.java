package com.robintegg.testcontainersdemo.routing;

import com.robintegg.testcontainersdemo.DemoApplicationTestPropertyValues;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Testcontainers
class NotificationRepositoryTest {

    @Container
    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:latest");

    @Autowired
    NotificationRepository repository;

    @DynamicPropertySource
    static void registerDynamicProperties(DynamicPropertyRegistry registry) {
        DemoApplicationTestPropertyValues.populateRegistryFromPostgresContainer(registry, postgreSQLContainer);
    }

    @Test
    void shouldStoreEachNotification() {

        // given
        repository.save(new Notification("message1", "test"));
        repository.save(new Notification("message2", "test"));

        // when
        long count = repository.count();

        // then
        assertThat(count, is(2L));

    }

    @Test
    void shouldStoreEachNotificationWithAUniqueIdentifier() {

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

}
