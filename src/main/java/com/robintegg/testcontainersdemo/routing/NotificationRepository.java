package com.robintegg.testcontainersdemo.routing;

import com.robintegg.testcontainersdemo.routing.Notification;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * NotificationRepository
 */
public interface NotificationRepository extends JpaRepository<Notification,Long>  {

    
}