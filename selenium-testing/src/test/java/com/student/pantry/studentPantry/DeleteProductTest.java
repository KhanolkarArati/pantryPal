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

public class DeleteProductTest {
	private WebDriver driver;

	@BeforeEach
	public void setUp() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().window().maximize();
	}

	@Test
	public void testDeleteProduct() {

		// Step 1: Open login page as session needs to be validated
		driver.get("http://localhost:4200/login");

		// Step 2: Enter email and password
		driver.findElement(By.cssSelector("input[placeholder='Enter your email']")).sendKeys("admin@umich.edu");
		driver.findElement(By.cssSelector("input[placeholder='Enter password']")).sendKeys("Admin@1234");

		// Step 3: Select role from dropdown
		Select roleDropdown = new Select(driver.findElement(By.tagName("select")));
		roleDropdown.selectByVisibleText("Admin");

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

		// Step 7: Click Admin link to go to /admin
		WebElement adminButton = wait
				.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[href='/admin']")));
		adminButton.click();

		// Step 8: Wait for /admin page
		wait.until(ExpectedConditions.urlContains("/admin"));

		/* Code for delete begins */

		// Step 1: Locate and click the Delete button for the first product
		WebElement deleteButton = wait.until(ExpectedConditions.elementToBeClickable(
				By.xpath("//div[@class='product-item'][1]//button[contains(@class,'delete-button')]")));
		deleteButton.click();

		// Step 2: Wait for the alert to appear and handle it
		Alert alert1 = wait.until(ExpectedConditions.alertIsPresent());

		// Step 3: Capture the alert message and validate it
		String alertMessage = alert1.getText();
		System.out.println("Alert message: " + alertMessage);
		assert (alertMessage.equals("Product removed !"));

		// Step 4: Accept the alert (close the alert)
		alert.accept();

		// logout for admin and end session
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
