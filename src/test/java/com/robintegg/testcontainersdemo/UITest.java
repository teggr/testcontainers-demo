package com.robintegg.testcontainersdemo;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testcontainers.containers.BrowserWebDriverContainer;
import org.testcontainers.containers.BrowserWebDriverContainer.VncRecordingMode;

public class UITest {

	// @formatter:off
	@Rule
	public BrowserWebDriverContainer chrome = new BrowserWebDriverContainer()
			.withRecordingMode(VncRecordingMode.RECORD_FAILING, new File("./target/"))
			.withCapabilities(new ChromeOptions());
	// @formatter:on

	@Test
	public void shouldSuccessfullyPassThisTestUsingTheRemoteDriver() throws InterruptedException {

		RemoteWebDriver driver = chrome.getWebDriver();

		System.out.println("Selenium remote URL is: " + chrome.getSeleniumAddress());
		System.out.println("VNC URL is: " + chrome.getVncAddress());

		driver.get("http://host.docker.internal:8080/");

		List<WebElement> results = new WebDriverWait(driver, 15)
				.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.tagName("h1")));

		assertThat(results.size(), is(1));
		assertThat(results.get(0).getText(), containsString("Notifications"));

	}

	@Test
	public void shouldFailThisTestUsingTheRemoteDriverAndGenerateAVideoRecording() throws InterruptedException {

		RemoteWebDriver driver = chrome.getWebDriver();

		System.out.println("Selenium remote URL is: " + chrome.getSeleniumAddress());
		System.out.println("VNC URL is: " + chrome.getVncAddress());

		driver.get("http://host.docker.internal:8080/");

		// added for effect when viewing the video
		Thread.currentThread().sleep(1000);

		List<WebElement> results = new WebDriverWait(driver, 15)
				.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.tagName("h1")));

		assertThat(results.size(), is(2));

	}

}
