package com.robintegg.testcontainersdemo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.robintegg.testcontainersdemo.inbound.JMSNotification;
import com.robintegg.testcontainersdemo.routing.Notification;
import com.robintegg.testcontainersdemo.routing.NotificationRepository;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ContextConfiguration(classes = RabbitMqTestConfiguration.class)
@Testcontainers
class RoutingTest {

    @Container
    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:latest");

    @Container
    static GenericContainer<?> activeMQContainer = new GenericContainer<>("rmohr/activemq:latest")
            .withExposedPorts(61616);

    @Container
    static GenericContainer<?> rabbitMQContainer = new GenericContainer<>("rabbitmq:management")
            .withExposedPorts(5672);

    @Autowired
    JmsTemplate jmsTemplate;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    NotificationRepository notificationRepository;

    @DynamicPropertySource
    static void registerDynamicProperties(DynamicPropertyRegistry registry) {
        DemoApplicationTestPropertyValues.populateRegistryFromContainers(registry, postgreSQLContainer, activeMQContainer, rabbitMQContainer);
    }

    @Test
    void shouldStoreANotifcationFromTheJmsQueueAndForwardToTheRabbitMQExchange() throws Exception {

        // given
        String message = "TestContainers are great";
        JMSNotification jmsNotification = new JMSNotification(message);

        // when
        sendNotificationToJmsQueue(jmsNotification);

        // then
        assertThatNotificationIsForwardedToRabbitMq(message);
        assertThatNotificationIsStoredInTheDatabase(message);

    }

    private void assertThatNotificationIsStoredInTheDatabase(String message) {
        Notification notification = notificationRepository.findAll().get(0);
        assertThat(notification.getMessage(), is(message));
        assertThat(notification.getSource(), is("JMS"));
        assertThat(notification.getId(), notNullValue());
    }

    private void assertThatNotificationIsForwardedToRabbitMq(String message) {
        Notification notification = readNotificationFromRabbitMqQueue();
        assertThat(notification.getMessage(), is(message));
        assertThat(notification.getSource(), is("JMS"));
        assertThat(notification.getId(), notNullValue());
    }

    private void sendNotificationToJmsQueue(JMSNotification jmsNotification) throws Exception {
        jmsTemplate.convertAndSend("jms.events", objectMapper.writeValueAsString(jmsNotification));
    }

    private Notification readNotificationFromRabbitMqQueue() {
        ParameterizedTypeReference<Notification> notificationTypeRef = new ParameterizedTypeReference<Notification>() {
        };

        Notification notification = rabbitTemplate.receiveAndConvert("testcontainers.test.queue", 1000,
                notificationTypeRef);
        return notification;
    }

}
