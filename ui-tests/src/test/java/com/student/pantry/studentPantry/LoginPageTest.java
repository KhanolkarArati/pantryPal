import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

public class LoginPageTest {

    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test
    public void testLoginWithValidCredentials() {
        // Step 1: Open login page
        driver.get("http://localhost:4200/login");

        // Step 2: Enter email and password
        driver.findElement(By.cssSelector("input[placeholder='Enter your email']")).sendKeys("test@umich.edu");
        driver.findElement(By.cssSelector("input[placeholder='Enter password']")).sendKeys("qwertY");

        // Step 3: Select role from dropdown
        Select roleDropdown = new Select(driver.findElement(By.tagName("select")));
        roleDropdown.selectByVisibleText("Student");

        // Step 4: Wait for the login button to be clickable and then click
        WebDriverWait wait = new WebDriverWait(driver, 30);
        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("input[type='submit']")));
        loginButton.click();

        // Step 5: Handle Alert (Authenticated Successfully)
         Alert alert = wait.until(ExpectedConditions.alertIsPresent());
         String alertText = alert.getText().trim();
         assertEquals("Authenticated Successfully.", alertText, "Alert message should be 'Authenticated Successfully.'");
        alert.accept(); // Click OK on alert

        wait.until(ExpectedConditions.urlContains("/products"));
        String currentUrl = driver.getCurrentUrl();
        assertTrue(currentUrl.contains("/products"), "User should be redirected to products after login.");
    }  

    @Test
    public void testLoginWithCredentials() {
        // Step 1: Open login page
        driver.get("http://localhost:4200/login");

        // Step 2: Enter email and password
        driver.findElement(By.cssSelector("input[placeholder='Enter your email']")).sendKeys("test@umich.edu");
        driver.findElement(By.cssSelector("input[placeholder='Enter password']")).sendKeys("qwerty");

        // Step 3: Select role from dropdown
        Select roleDropdown = new Select(driver.findElement(By.tagName("select")));
        roleDropdown.selectByVisibleText("Student");

        // Step 4: Wait for the login button to be clickable and then click
        WebDriverWait wait = new WebDriverWait(driver, 30);
        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("input[type='submit']")));
        loginButton.click();

        // Step 5: Handle Alert (Authenticated Successfully)
         Alert alert = wait.until(ExpectedConditions.alertIsPresent());
         String alertText = alert.getText().trim();
         assertEquals("INVALID USERNAME OR PASSWORD", alertText, "Alert message should be 'INVALID USERNAME OR PASSWORD'");
        alert.accept(); // Click OK on alert

        wait.until(ExpectedConditions.urlContains("/login"));
        String currentUrl = driver.getCurrentUrl();
        assertTrue(currentUrl.contains("/login"), "User should be redirected to products after login.");
    }  
   
@Test
    public void testLoginWithInvalidCredentials() {
        // Step 1: Open login page
        driver.get("http://localhost:4200/login");

        // Step 2: Enter invalid email and password
        driver.findElement(By.cssSelector("input[placeholder='Enter your email']")).sendKeys("wrong@gmail.com");
        driver.findElement(By.cssSelector("input[placeholder='Enter password']")).sendKeys("wrongpassword");

        // Step 3: Select role
        Select roleDropdown = new Select(driver.findElement(By.tagName("select")));
        roleDropdown.selectByVisibleText("Student");

        // Step 4: Wait for the login button to be clickable and then click
        WebDriverWait wait = new WebDriverWait(driver, 30);
        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("input[type='submit']")));
        loginButton.click();

        // Step 5: Handle Alert (Authenticated Successfully)
         Alert alert = wait.until(ExpectedConditions.alertIsPresent());
         String alertText = alert.getText().trim();
         assertEquals("Please fill in all required fields and make sure the conditions are met.", alertText, "Alert message should be 'Please fill in all required fields and make sure the conditions are met.'");
        alert.accept(); // Click OK on alert

        wait.until(ExpectedConditions.urlContains("/login"));
        String currentUrl = driver.getCurrentUrl();
        assertTrue(currentUrl.contains("/login"), "User should be redirected to products after login.");
    }  

    @Test
    public void testLoginWithAdminCredentials() {
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
        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("input[type='submit']")));
        loginButton.click();

        // Step 5: Handle Alert (Authenticated Successfully)
         Alert alert = wait.until(ExpectedConditions.alertIsPresent());
         String alertText = alert.getText().trim();
           
            assertTrue(alertText.equals("Authenticated Successfully.") || 
               alertText.equals("Only one admin login allowed at a time"),
               "Unexpected alert message: " + alertText);        
               
               alert.accept(); // Click OK on alert

       if (alertText.equals("Authenticated Successfully.")) {
        wait.until(ExpectedConditions.urlContains("/products"));
        String currentUrl = driver.getCurrentUrl();
        assertTrue(currentUrl.contains("/products"), "User should be redirected to products after login.");
        } 
        else if (alertText.equals("Only one admin login allowed at a time")) {
        String currentUrl = driver.getCurrentUrl();
        assertTrue(currentUrl.contains("/login"), "User should remain on login page if another admin is logged in.");
        }
    }


    private void handleAlert() {
        try {
            Alert alert = driver.switchTo().alert();
            System.out.println("Alert text: " + alert.getText());
            alert.accept(); // Accept the alert (use alert.dismiss() if you want to dismiss it)
        } catch (NoAlertPresentException e) {
            // No alert present, continue with test
            System.out.println("No alert present.");
        }
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
