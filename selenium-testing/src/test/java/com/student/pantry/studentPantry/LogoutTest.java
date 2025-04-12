import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

public class LogoutTest {

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

        // Step 6: redirected to products page
        wait.until(ExpectedConditions.urlContains("/products"));
        String currentUrl = driver.getCurrentUrl();
        assertTrue(currentUrl.contains("/products"), "User should be redirected to products after login.");

        // Step 7: Click on logout button (assuming there's a logout button with id or text)
    WebElement logoutButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(text(), 'Logout')]")));
    logoutButton.click();

try {
    Alert logoutAlert = wait.until(ExpectedConditions.alertIsPresent());
    String logoutAlertText = logoutAlert.getText().trim();
    assertEquals("You have been logged out", logoutAlertText, "Logout alert message should be 'You have been logged out'");
    logoutAlert.accept();
} catch (TimeoutException e) {
    fail("Expected logout alert was not shown.");
}


    // Step 8: Verify redirection back to login page after logout
    wait.until(ExpectedConditions.urlContains("/login"));
    String logoutUrl = driver.getCurrentUrl();
    assertTrue(logoutUrl.contains("/login"), "Student should be redirected to login page after logout.");
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
