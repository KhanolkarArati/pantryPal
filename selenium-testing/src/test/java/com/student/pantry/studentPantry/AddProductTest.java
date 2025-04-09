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

public class AddProductTest {

	private WebDriver driver;

	@BeforeEach
	public void setUp() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().window().maximize();

		// Step 1: Open login page
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
	}

	@Test
	public void testAddProductWithValidData() {

		/*
		 * Add product code starts
		 */

		WebDriverWait wait = new WebDriverWait(driver, 30);

		// Step 1: Click the "Add Product" button
		WebElement addProductButton = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Add Product']")));
		addProductButton.click();

		// Step 2: Wait for the modal to appear and check if the data is pre-filled
		WebElement modal = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("addProductModal")));

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

		productNameField.sendKeys("Corn");
		productQuantityField.clear();
		productQuantityField.sendKeys("15");
		productExpiryDateField.sendKeys("2025-12-31");
		productImageURLField.sendKeys("assets/corn.png");

		// Step 4: Click the "Add" button to submit the form
		WebElement addButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Add']")));
		addButton.click();

		Alert alert1 = wait.until(ExpectedConditions.alertIsPresent());

		// Step 5: Handle success alert
		String alertText1 = alert1.getText().trim();
		assertEquals("Product added successfully", alertText1, "Alert message should be 'Product added successfully'");
		alert1.accept();

		// Close the modal if it's open
		WebElement closeButton = driver.findElement(By.cssSelector("#addProductModal > div > span"));
		closeButton.click();

		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("addProductModal")));

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
