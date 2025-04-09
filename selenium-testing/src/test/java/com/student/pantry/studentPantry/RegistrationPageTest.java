import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

public class RegistrationPageTest {

    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test
    public void testEmptyFormSubmission() {
        driver.get("http://localhost:4200/register");
        driver.findElement(By.cssSelector("input[type='submit']")).click();
        WebDriverWait wait = new WebDriverWait(driver, 30);
        
       

        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
         String alertText = alert.getText().trim();
         assertEquals("Please fill in all required fields and make sure the conditions are met.", alertText, "Alert message should be Please fill in all required fields and make sure the conditions are met.");
        alert.accept(); // Click OK on alert
    }

    @Test
public void testValidRegistration() {
    // Step 1: Open registration page
    driver.get("http://localhost:4200/register");

    // Step 2: Enter registration details
    driver.findElement(By.cssSelector("input[placeholder='Enter your U-Mich Id']")).sendKeys("101010");
    driver.findElement(By.cssSelector("input[placeholder='Enter your email']")).sendKeys("test10@umich.edu");
    driver.findElement(By.cssSelector("input[placeholder='Create password']")).sendKeys("qwertY");
    driver.findElement(By.cssSelector("input[placeholder='Set Username']")).sendKeys("test10");

    // Step 3: Wait for the register button to be clickable and then click
    WebDriverWait wait = new WebDriverWait(driver, 30);
    WebElement registerButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("input[type='submit']")));
    registerButton.click();

    // Step 4: Handle Alert
    Alert alert = wait.until(ExpectedConditions.alertIsPresent());
    String alertText = alert.getText().trim();

    assertTrue(alertText.equals("Registration Successful. Please login to access the portal.") || 
               alertText.equals("Registration Failed ! Please try with correct credentials."),
               "Unexpected alert message: " + alertText);

    alert.accept(); // Click OK on alert

    // Step 5: Check URL based on alert message
    if (alertText.equals("Registration Successful. Please login to access the portal.")) {
        wait.until(ExpectedConditions.urlContains("/login"));
        String currentUrl = driver.getCurrentUrl();
        assertTrue(currentUrl.contains("/login"), "User should be redirected to login after successful registration.");
    } else if (alertText.equals("Registration Failed ! Please try with correct credentials.")) {
        String currentUrl = driver.getCurrentUrl();
        assertTrue(currentUrl.contains("/register"), "User should remain on the registration page if registration fails.");
    }
}


    @Test
    public void testInvalidEmailFormat() {
        // Step 1: Open login page
        driver.get("http://localhost:4200/register");

        // Step 2: Enter invalid email and password
        driver.findElement(By.cssSelector("input[placeholder='Enter your email']")).sendKeys("wrong@gmail.com");


        // Step 4: Wait for the login button to be clickable and then click
        WebDriverWait wait = new WebDriverWait(driver, 30);
        WebElement registerButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("input[type='submit']")));
        registerButton.click();

        // Step 5: Handle Alert (Authenticated Successfully)
         Alert alert = wait.until(ExpectedConditions.alertIsPresent());
         String alertText = alert.getText().trim();
         assertEquals("Please fill in all required fields and make sure the conditions are met.", alertText, "Alert message should be 'Please fill in all required fields and make sure the conditions are met.'");
        alert.accept(); // Click OK on alert

        wait.until(ExpectedConditions.urlContains("/register"));
        String currentUrl = driver.getCurrentUrl();
        assertTrue(currentUrl.contains("/register"), "User should be redirected to login after register.");
    }  

    @Test
    public void testPasswordStrengthValidation() {
        // Step 1: Open login page
        driver.get("http://localhost:4200/register");

        // Step 2: Enter invalid email and password
        driver.findElement(By.cssSelector("input[placeholder='Create password']")).sendKeys("123");


        // Step 4: Wait for the login button to be clickable and then click
        WebDriverWait wait = new WebDriverWait(driver, 30);
        WebElement registerButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("input[type='submit']")));
        registerButton.click();

        // Step 5: Handle Alert 
         Alert alert = wait.until(ExpectedConditions.alertIsPresent());
         String alertText = alert.getText().trim();
         assertEquals("Please fill in all required fields and make sure the conditions are met.", alertText, "Alert message should be 'Please fill in all required fields and make sure the conditions are met.'");
        alert.accept(); // Click OK on alert

        wait.until(ExpectedConditions.urlContains("/register"));
        String currentUrl = driver.getCurrentUrl();
        assertTrue(currentUrl.contains("/register"), "User should be redirected to login after register.");
    }

    @Test
    public void testLoginLinkRedirect() {
        driver.get("http://localhost:4200/register");
        driver.findElement(By.linkText("Login now")).click();
        
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.urlContains("/login"));
        String currentUrl = driver.getCurrentUrl();
        assertTrue(currentUrl.contains("/login"), "Login page not opened");
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
