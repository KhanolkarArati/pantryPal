package com.student.pantry.studentPantry;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

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

public class ViewCart {
    private WebDriver driver;

	@BeforeEach
	public void setUp() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().window().maximize();
	}


    @Test
	public void testViewCart() {

		// Step 1: Open login page as session needs to be validated
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
        
        //WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        WebElement cartIcon = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@id='navbarDropdown']")));
        
        // Click the cart icon to open the dropdown
        cartIcon.click();

        
        WebElement dropdownMenu = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("dropdown-menu")));

        // Wait for the cart items to be visible and wait for the products to load
        List<WebElement> cartItems = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(".CartContainer .row.pt-1")));
        System.out.println("Number of items in the cart: " + cartItems.size());
	}

	@AfterEach
	public void tearDown() {

		if (driver != null) {
			driver.quit();
		}
	}
}
