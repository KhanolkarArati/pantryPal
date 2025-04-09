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

public class UpdateProductTest {

	private WebDriver driver;

	@BeforeEach
	public void setUp() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().window().maximize();
	}

	@Test
	public void testUpdateProduct() {
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

		/* 
		 * Code for update begins 
		 * */

		// Step 1: Locate and click the Update button for the first product
		WebElement updateButton = wait.until(ExpectedConditions.elementToBeClickable(
				By.xpath("//div[@class='product-item'][1]//button[contains(@class,'update-button')]")));
		updateButton.click();

		// Step 2: Wait for the modal to appear and check if the data is pre-filled
		WebElement modal = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("modal")));

		WebElement productNameField = modal.findElement(By.id("productName"));
		WebElement productQuantityField = modal.findElement(By.id("productQuantity"));
		WebElement productExpiryDateField = modal.findElement(By.id("productExpiryDate"));
		WebElement productImageURLField = modal.findElement(By.id("productImageURL"));

		// Step 3: Assert the pre-filled data 
		String initialProductName = productNameField.getAttribute("value");
		String initialProductQuantity = productQuantityField.getAttribute("value");
		String initialProductExpiryDate = productExpiryDateField.getAttribute("value");
		String initialProductImageURL = productImageURLField.getAttribute("value");

		System.out.println("Initial Product Name: " + initialProductName);
		System.out.println("Initial Product Quantity: " + initialProductQuantity);
		System.out.println("Initial Product Expiry Date: " + initialProductExpiryDate);
		System.out.println("Initial Product Image URL: " + initialProductImageURL);

		productQuantityField.clear();
		productQuantityField.sendKeys("15");

		// Step 5: Click the Save button to update the product
		WebElement saveButton = modal.findElement(By.xpath("//button[text()='Save']"));
		saveButton.click();

		// Step 6: Wait for the alert to appear and handle it
		Alert alert1 = wait.until(ExpectedConditions.alertIsPresent());

		// Step 7: Capture the alert message and validate
		String alertMessage = alert1.getText();
		System.out.println("Alert message: " + alertMessage);
		assert (alertMessage.equals("Product updated successfully...!!!"));

		// Step 8: Accept the alert
		alert1.accept();
		
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
