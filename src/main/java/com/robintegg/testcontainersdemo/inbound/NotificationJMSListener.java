package com.robintegg.testcontainersdemo.inbound;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.robintegg.testcontainersdemo.routing.Notification;
import com.robintegg.testcontainersdemo.routing.NotificationListener;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * NotificationJMSListener
 */
@Component
public class NotificationJMSListener {

    private final NotificationListener listener;
    private final ObjectMapper objectMapper;

    public NotificationJMSListener(NotificationListener listener, ObjectMapper objectMapper) {
        super();
        this.listener = listener;
        this.objectMapper = objectMapper;
    }

    @JmsListener(destination = "jms.events")
    public void receiveMessage(String message) throws JsonParseException, JsonMappingException, IOException {

        // deserialise
        JMSNotification jmsNotification = objectMapper.readValue(message, JMSNotification.class);

        // translate
        Notification notification = translate(jmsNotification);

        // forward to domain
        listener.onNotification(notification);

    }

    private Notification translate(JMSNotification jmsNotification) {
        return new Notification(jmsNotification.getMessage(), "JMS");
    }

}