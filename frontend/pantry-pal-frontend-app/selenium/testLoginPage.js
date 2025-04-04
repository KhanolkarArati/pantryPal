const { Builder, By, until } = require("selenium-webdriver");
const assert = require("assert"); 

async function testLoginPage() {
    let driver = await new Builder().forBrowser("chrome").build();

    try {
        await driver.get("http://localhost:4200/login");

        await driver.wait(until.elementLocated(By.css("form")), 10000);

        const emailInput = await driver.findElement(By.css("input[formControlName='email']"));
        await emailInput.sendKeys("student@umich.edu");

        const passwordInput = await driver.findElement(By.css("input[formControlName='userPasswd']"));
        await passwordInput.sendKeys("Abcd@12345");

        const roleSelect = await driver.findElement(By.css("select[formControlName='userrole']"));
        await roleSelect.sendKeys("STUDENT");

        const loginButton = await driver.findElement(By.css("input[type='submit']"));
        await loginButton.click();

        try {
            await driver.wait(until.alertIsPresent(), 10000);
            const alert = await driver.switchTo().alert();
            const alertText = await alert.getText();
            console.log("Alert text:", alertText);
            
            // Assert the alert text (Success or error)
            assert(alertText.includes("Authenticated Successfully.") || alertText.includes("Invalid credentials"), 'Unexpected alert text');
            await new Promise(resolve => setTimeout(resolve, 5000));
            await alert.accept();
        } catch (e) {
            console.log("No alert appeared.");
        }

        await driver.wait(until.urlContains("products"), 10000);

        // Assert that the current URL contains "products" (login success)
        const currentUrl = await driver.getCurrentUrl();
        console.log("Current URL:", currentUrl);
        assert(currentUrl.includes("products"), 'Login failed: URL does not contain "products"');

        console.log("Login test passed. Navigated to products page.");

    } catch (err) {
        console.error("Test failed:", err);
    } finally {
        await new Promise(resolve => setTimeout(resolve, 5000));
        await driver.quit();
    }
}

testLoginPage();
