package com.robintegg.testcontainersdemo.routing;

import java.util.List;

import org.springframework.stereotype.Component;

/**
 * NotificationRouter will route all inbound {@link Notification} objects to a set of Endpoint
 */
@Component
public class NotificationRouter implements NotificationListener {

    private final List<Endpoint> endpoints;
    private final NotificationRepository notificationRepository;

    public NotificationRouter(List<Endpoint> endpoints, NotificationRepository notificationRepository) {
        super();
        this.endpoints = endpoints;
        this.notificationRepository = notificationRepository;
    }

    @Override
    public void onNotification(Notification notification) {

        // save before forwarding
        notificationRepository.save(notification);

        // forward event to registered endpoints
        for (Endpoint endpoint : endpoints) {
            endpoint.send(notification);
        }

    }

}