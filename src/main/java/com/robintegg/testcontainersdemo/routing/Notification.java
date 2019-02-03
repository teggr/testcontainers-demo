package com.robintegg.testcontainersdemo.routing;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * Notification representing an event in the ecosystem
 */
@Entity
@Table(name="notification")
public class Notification {

    @Id
    @SequenceGenerator(name = "notification_id_generator", sequenceName = "notification_id_sequence", allocationSize = 1)
    @GeneratedValue(generator = "notification_id_generator")
    private Long id;
	private String message;
    private String source;

    public Notification(String message, String source) {
        this.message = message;
        this.source = source;
    }
    
    public Notification(){ /* support for jpa */ }

    public Long getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public String getSource() {
        return source;
    }
    
}