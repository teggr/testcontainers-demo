package com.robintegg.testcontainersdemo.web;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.robintegg.testcontainersdemo.routing.Notification;
import com.robintegg.testcontainersdemo.routing.NotificationQueryService;

@WebMvcTest(NotificationControllerTest.class)
class NotificationControllerTest {

    @Autowired
    WebDriver webDriver;

    @MockBean
    NotificationQueryService mockNotifcationQueryService;

    @Test
    void shouldHaveATableWithNoRowsWhenZeroNotifications() {

        // given
        when(mockNotifcationQueryService.getAll()).thenReturn(Collections.emptyList());

        // when
        webDriver.get("/");

        // then
        List<WebElement> tableRows = webDriver.findElements(By.className("row-notification"));
        assertTrue(tableRows.isEmpty());

    }

    @Test
    void shouldHaveTableWithATableRowForASingleNotification() {

        // given
        Notification notification1 = new Notification("msg1", "source1");
        when(mockNotifcationQueryService.getAll()).thenReturn(Arrays.asList(notification1));

        // when
        webDriver.get("/");

        // then
        List<WebElement> tableRows = webDriver.findElements(By.className("row-notification"));
        assertThat(tableRows.size(), is(1));
        assertThat(tableRows.get(0).getText(), allOf(containsString("msg1"), containsString("source1")));

    }

    @Test
    void shouldHaveTableWithATableRowForEachNotification() {

        // given
        Notification notification1 = new Notification("msg1", "source1");
        Notification notification2 = new Notification("msg2", "source2");
        when(mockNotifcationQueryService.getAll()).thenReturn(Arrays.asList(notification1, notification2));

        // when
        webDriver.get("/");

        // then
        List<WebElement> tableRows = webDriver.findElements(By.className("row-notification"));
        assertThat(tableRows.size(), is(2));
        assertThat(tableRows.get(0).getText(), allOf(containsString("msg1"), containsString("source1")));
        assertThat(tableRows.get(1).getText(), allOf(containsString("msg2"), containsString("source2")));

    }

}
