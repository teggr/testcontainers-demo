package com.robintegg.testcontainersdemo.routing;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class RoutingNotificationQueryService implements NotificationQueryService {

	private final NotificationRepository notificationRepository;

	public RoutingNotificationQueryService(NotificationRepository notificationRepository) {
		this.notificationRepository = notificationRepository;
	}

	@Override
	public List<Notification> getAll() {
		return notificationRepository.findAll();
	}

}
