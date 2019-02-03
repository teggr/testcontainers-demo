package com.robintegg.testcontainersdemo.routing;

/**
 * A {@link NotificationRouter} will forward {@link Notification} to registered {@link Endpoint}s to manage
 */
public interface Endpoint {

    void send( Notification notification );

}