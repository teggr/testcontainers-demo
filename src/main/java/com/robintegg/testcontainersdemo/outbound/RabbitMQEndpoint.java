package com.robintegg.testcontainersdemo.outbound;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import com.robintegg.testcontainersdemo.routing.Endpoint;
import com.robintegg.testcontainersdemo.routing.Notification;

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