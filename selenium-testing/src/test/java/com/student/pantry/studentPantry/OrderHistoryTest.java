package com.student.pantry.studentPantry;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class OrderHistoryTest {

	private WebDriver driver;

	@BeforeEach
	public void setUp() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().window().maximize();

		// Step 1: Open login page
		driver.get("http://localhost:4200/login");

		// Step 2: Enter email and password
		driver.findElement(By.cssSelector("input[placeholder='Enter your email']")).sendKeys("student@umich.edu");
		driver.findElement(By.cssSelector("input[placeholder='Enter password']")).sendKeys("Abcd@12345");

		// Step 3: Select role from dropdown
		Select roleDropdown = new Select(driver.findElement(By.tagName("select")));
		roleDropdown.selectByVisibleText("Student");

		// Step 4: Wait for the login button to be clickable and then click
		WebDriverWait wait = new WebDriverWait(driver, 30);
		WebElement loginButton = wait
				.until(ExpectedConditions.elementToBeClickable(By.cssSelector("input[type='submit']")));
		loginButton.click();

		// Step 5: Handle Alert (Authenticated Successfully)
		Alert alert = wait.until(ExpectedConditions.alertIsPresent());
		String alertText = alert.getText().trim();

		assertTrue(
				alertText.equals("Authenticated Successfully.")
						|| alertText.equals("Only one admin login allowed at a time"),
				"Unexpected alert message: " + alertText);

		alert.accept();

		// Step 6: Wait for /products page
		wait.until(ExpectedConditions.urlContains("/products"));
	}
	
	@Test
	public void testOrderHistory() {

		WebDriverWait wait = new WebDriverWait(driver, 30);

		WebElement adminButton = wait
				.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[href='/orders']")));
		adminButton.click();

		// Step 1: Wait for /sale page
		wait.until(ExpectedConditions.urlContains("/orders"));

		// Now click logout
		WebElement logoutButton = wait
				.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[href='/login']")));
		logoutButton.click();

	}
	
	@AfterEach
	public void tearDown() {
		if (driver != null) {
			driver.quit();
		}
	}
}
