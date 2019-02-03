package com.robintegg.testcontainersdemo.routing;

/**
 * NotifcationListener listens for notifications
 */
public interface NotificationListener {

    /**
     * Called when {@link Notification} objects are received by the application
     */
    void onNotification( Notification notification );

}