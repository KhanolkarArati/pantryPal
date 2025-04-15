package com.student.pantry.studentPantry;

import static org.junit.jupiter.api.Assertions.*;

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
import com.student.pantry.studentPantry.ScreenshotUtil;

import io.github.bonigarcia.wdm.WebDriverManager;

public class AddProductToCartTest{

    private WebDriver driver;

    @BeforeEach
    public void setUp() {
		//test = extent.createTest("AddProductToCartTest");
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test
    public void testViewPantryItems()  {
            // Step 1: Open login page as session needs to be validated
            driver.get("http://localhost:4200/login");

            // Step 2: Enter email and password
            driver.findElement(By.cssSelector("input[placeholder='Enter your email']")).sendKeys("test@umich.edu");
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

            boolean isClickable = ExpectedConditions.elementToBeClickable(
                    By.xpath("(//button[contains(@class,'btn-success') and text()=' Add To Cart'])[1]")).apply(driver) != null;

            assertTrue(isClickable, "No items found to add to cart.");

            // logout for admin and end session
            WebElement logoutButton = wait
                    .until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[href='/login']")));
            logoutButton.click();
        
    }
    @Test
    public void testInvalidItemsInPantry() {

            // Step 1: Open login page as session needs to be validated
            driver.get("http://localhost:4200/login");

            // Step 2: Enter email and password
            driver.findElement(By.cssSelector("input[placeholder='Enter your email']")).sendKeys("test@umich.edu");
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

            List<WebElement> productCards = driver.findElements(By.cssSelector(".col-lg-3.pt-1"));
            for (WebElement card : productCards) {
                WebElement quantityElement = card.findElement(By.cssSelector(".text-success.ms-2"));
                int quantity = Integer.parseInt(quantityElement.getText().trim());

                // Check if "Add To Cart" is visible for this item
                List<WebElement> addToCartButtons = card.findElements(By.xpath(".//button[contains(text(),'Add To Cart')]"));

                // If quantity is zero or less, button should NOT be visible
                if (quantity <= 0) {
                    ScreenshotUtil.captureScreenshot(driver, "TestAddProductToCart");
                    assertTrue(addToCartButtons.isEmpty(), "Product with quantity " + quantity + " should not have 'Add To Cart' button.");
                    
                } else {
                    assertFalse(addToCartButtons.isEmpty(), "Product with quantity " + quantity + " should have 'Add To Cart' button.");
                }
            }

            // logout for admin and end session
            WebElement logoutButton = wait
                    .until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[href='/login']")));
            logoutButton.click();
        
    }
    @Test
    public void testAddToCartProduct() {
      
            // Step 1: Open login page as session needs to be validated
            driver.get("http://localhost:4200/login");

            // Step 2: Enter email and password
            driver.findElement(By.cssSelector("input[placeholder='Enter your email']")).sendKeys("test@umich.edu");
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

            // Step 1: Locate and click the add button for the first product
            WebElement addToCart = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("(//button[contains(@class,'btn-success') and text()=' Add To Cart'])[1]")));
            addToCart.click();

            // Step 2: Wait for the alert to appear and handle it
            Alert alert1 = wait.until(ExpectedConditions.alertIsPresent());

            // Step 3: Capture the alert message and validate it
            String alertMessage = alert1.getText();
            assertEquals("Product Added To Cart", alertMessage, "Unexpected alert message");

            // Step 4: Accept the alert (close the alert)
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
