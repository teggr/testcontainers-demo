package com.robintegg.testcontainersdemo.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.robintegg.testcontainersdemo.routing.NotificationQueryService;

@Controller
@RequestMapping("/")
public class NotificationController {

	private final NotificationQueryService notificationQueryService;

	public NotificationController(NotificationQueryService notificationQueryService) {
		this.notificationQueryService = notificationQueryService;
	}

	@GetMapping
	public String get(Model model) {
		model.addAttribute("notifications", notificationQueryService.getAll());
		return "notifications";
	}

}
