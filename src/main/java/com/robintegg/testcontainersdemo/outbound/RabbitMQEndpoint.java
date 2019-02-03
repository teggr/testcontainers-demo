package com.robintegg.testcontainersdemo.outbound;

import com.robintegg.testcontainersdemo.routing.Endpoint;
import com.robintegg.testcontainersdemo.routing.Notification;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

/**
 * RabbitMQEndpoint
 */
@Component
public class RabbitMQEndpoint implements Endpoint {

    private RabbitTemplate rabbitTemplate;

    public RabbitMQEndpoint(RabbitTemplate rabbitTemplate) {
        super();
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void send(Notification notification) {

        rabbitTemplate.convertAndSend(notification);

    }

}