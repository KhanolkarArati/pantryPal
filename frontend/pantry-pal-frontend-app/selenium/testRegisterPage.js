const { Builder, By, until } = require('selenium-webdriver');
const assert = require('assert'); 

const sleep = (ms) => new Promise(resolve => setTimeout(resolve, ms));

async function testRegisterPage() {
  let driver = await new Builder().forBrowser('chrome').build();

  try {
    await driver.get('http://localhost:4200/register');

    await driver.findElement(By.css('input[formControlName="studentId"]')).sendKeys('1234511');
    await sleep(1000);
    await driver.findElement(By.css('input[formControlName="email"]')).sendKeys('teststudent11@umich.edu');
    await sleep(1000);
    await driver.findElement(By.css('input[formControlName="userPasswd"]')).sendKeys('Password1');
    await sleep(1000);
    await driver.findElement(By.css('input[formControlName="username"]')).sendKeys('teststudent11');
    await sleep(2000);

    await driver.findElement(By.css('input[type="submit"]')).click();
    await sleep(3000);

    await driver.wait(until.alertIsPresent(), 20000);
    const alert = await driver.switchTo().alert();
    const alertText = await alert.getText();
    console.log('Alert Text:', alertText);

    // Assert that the alert text contains 'Registration Successful'
    assert(alertText.includes('Registration Successful'), 'Alert message does not contain expected text');

    await alert.accept();
    await sleep(3000);

    await driver.wait(until.urlContains('/login'), 10000);
    const currentUrl = await driver.getCurrentUrl();
    console.log('Current URL:', currentUrl);

    // Assert that the URL contains '/login'
    assert(currentUrl.includes('/login'), 'URL does not contain expected value "/login"');

  } catch (err) {
    console.error('Test failed:', err);
  } finally {
    await driver.quit();
  }
}

testRegisterPage();
