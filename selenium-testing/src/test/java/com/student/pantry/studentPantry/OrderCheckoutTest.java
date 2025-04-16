package com.student.pantry.studentPantry;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

public class OrderCheckoutTest {

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
		driver.findElement(By.cssSelector("input[placeholder='Enter password']")).sendKeys("qwertY");

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
		
		 WebElement addToCart = wait.until(ExpectedConditions.elementToBeClickable(
			        //By.xpath("(//div[@class='col-lg-3 pt-1']//button[contains(text(),'Add To Cart')])[1]")
			        By.xpath("(//button[contains(@class,'btn-success') and text()=' Add To Cart'])[1]")
			        ));
			        addToCart.click(); 
					// Step 2: Wait for the alert to appear and handle it
					Alert alert1 = wait.until(ExpectedConditions.alertIsPresent());

					// Step 3: Capture the alert message and validate it
					String alertMessage = alert1.getText();
					System.out.println("Alert message: " + alertMessage);
					assert (alertMessage.equals("Product Added To Cart"));

					// Step 4: Accept the alert (close the alert)
					alert.accept();
	}
	
	@Test
	public void testOrderCheckout() {

		WebDriverWait wait = new WebDriverWait(driver, 30);

		WebElement adminButton = wait
				.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[href='/sale']")));
		adminButton.click();

		// Step 1: Wait for /sale page
		wait.until(ExpectedConditions.urlContains("/sale"));
		
		// Step 2: Click the "Place order" button to order
		WebElement placeOrderButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Place Order']")));
		placeOrderButton.click();

		Alert alert1 = wait.until(ExpectedConditions.alertIsPresent());

		// Step 5: Handle success alert
		String alertText1 = alert1.getText().trim();
		assertEquals("Order placed successfully", alertText1, "Alert message should be 'Order placed successfully'");
		alert1.accept();

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
