package com.robintegg.testcontainersdemo.inbound;

/**
 * JMSNotification received from an external client with an identifier
 */
public class JMSNotification {

	private String message;

	public JMSNotification() {

	}

	public JMSNotification(String message) {
		super();
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}