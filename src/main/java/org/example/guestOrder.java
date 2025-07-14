package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class guestOrder extends browserSetup {
    public guestOrder() throws InterruptedException {
        boolean loggedIn = false;
        driver.navigate().to(readProperty("GuestURL"));
        wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@aria-label=\"Pick Up\"]")));
        driver.findElement(By.xpath("//button[@aria-label=\"Pick Up\"]")).click();
        new createCart(readProperty("GuestLocation"),readProperty("guestOrderItem"),loggedIn);
        wait = new WebDriverWait(driver, 30);
        System.out.println("Entering Customer Details");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@data-testid=\"first_name\"]")));
        driver.findElement(By.xpath("//input[@data-testid=\"first_name\"]")).sendKeys(readProperty("GuestFirstName"));
        driver.findElement(By.xpath("//input[@data-testid=\"last_name\"]")).sendKeys(readProperty("GuestLastName"));
        driver.findElement(By.xpath("//input[@data-testid=\"phone\"]")).sendKeys(readProperty("GuestPhoneNumber"));
        driver.findElement(By.name("email")).sendKeys(readProperty("GuestEmail"));
        driver.findElement(By.xpath("//button[@class=\"primary_button w-full text-base font-semibold p-3 px-5 mr-3 rounded-2xl border capitalize text-white ng-star-inserted\"]")).click();
        // Stale Element Exception if Trying Implicit Wait
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("\"//button[@class=\\\"primary_button w-full text-base font-semibold p-3 px-5 mr-3 rounded-2xl border capitalize text-white ng-star-inserted\\\"]\"")));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h5[@data-testid=\"orderTotal\"]")));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,2000)", "");
        System.out.println("TC_07: For Guest Order: ");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h5[@data-testid=\"orderTotal\"]")));
        Thread.sleep(5000);
        // The System automatically selects back the PaymentMode0 regardless of click, We can remove sleep once dev team fixes this
        try{
            if (driver.findElement(By.id("policy")).isSelected()) {
                System.out.println("Privacy Policy and Terms & Conditions are Already Accepted");
            } else {
                driver.findElement(By.id("policy")).click();
                System.out.println("Privacy Policy and Terms & Conditions Accepted");
            }
        }
        catch (NoSuchElementException | TimeoutException e) {
            System.out.println("Privacy Policy and Terms and Conditions Checkbox is Not Displayed");
        }
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h5[@data-testid=\"orderTotal\"]")));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@data-testid=\""+readProperty("CODPaymentMode")+"\"]")));
        driver.findElement(By.xpath("//input[@data-testid=\""+readProperty("CODPaymentMode")+"\"]")).click();
        driver.findElement(By.xpath("(//button[@data-testid=\"placeOrder\"])[2]")).click();
        System.out.println("TC_32: Cash on Delivery Payment wih Gateway");
        new pageBackPostOrder();
        new createCart(readProperty("GuestLocation"),readProperty("guestOrderItem"),loggedIn);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h5[@data-testid=\"orderTotal\"]")));
        js.executeScript("window.scrollBy(0,2000)", "");
        driver.findElement(By.xpath("//textarea[@placeholder='Note here...']")).sendKeys("Test Order Comment");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@data-testid=\""+readProperty("OnlinePaymentMode")+"\"]")));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h5[@data-testid=\"orderTotal\"]")));
        Thread.sleep(5000);
        try{
            if (driver.findElement(By.id("policy")).isSelected()) {
                System.out.println("Privacy Policy and Terms & Conditions are Already Accepted");
            } else {
                driver.findElement(By.id("policy")).click();
                System.out.println("Privacy Policy and Terms & Conditions Accepted");
            }
        }
        catch (NoSuchElementException | TimeoutException e) {
            System.out.println("Privacy Policy and Terms and Conditions Checkbox is Not Displayed");
        }
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h5[@data-testid=\"orderTotal\"]")));
        driver.findElement(By.xpath("//input[@data-testid=\""+readProperty("OnlinePaymentMode")+"\"]")).click();
        String checkoutOrderTotal = driver.findElement(By.xpath("//h5[@data-testid=\"orderTotal\"]")).getText();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//button[@data-testid=\"placeOrder\"])[2]"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//input[@data-testid=\"BillFirst name\"])[2]")));
        driver.findElement(By.xpath("(//input[@data-testid=\"BillFirst name\"])[2]")).sendKeys(readProperty("guestBillingFirstName"));
        driver.findElement(By.xpath("(//input[@data-testid=\"BillLast name\"])[2]")).sendKeys(readProperty("guestBillingLastName"));
        driver.findElement(By.xpath("(//input[@data-testid=\"BillPhone number\"])[2]")).sendKeys(readProperty("guestBillingPhoneNumber"));
        driver.findElement(By.xpath("//button[@data-testid=\"SubmitBillingDetails\"]")).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("(//input[@data-testid=\"BillPhone number\"])[2]")));
        // There no Dynamic Xpath for the Saved Successfully Container hence using Thread.sleep
        Thread.sleep(5000);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@data-testid=\"placeOrderStripe\"]"))).click();
        new matchAmount(checkoutOrderTotal);
        new checkSavedOrNew(readProperty("guestNewCardNumber"),loggedIn);
        new browserBackPostOrder();
        System.out.println("TC_12: PASS - Payment Successful by a New Card");
        System.out.println("TC_20: PASS - Payment Gateway is working for a Single Location");
        new paymentNavigation(loggedIn);
        new spamPay();
        Thread.sleep(3000);
        new paymentNavigation(loggedIn);
        new sharedURLPayment();
    }
}
