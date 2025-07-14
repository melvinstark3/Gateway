package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class loginOrder extends browserSetup{

    public loginOrder() throws InterruptedException {
        wait = new WebDriverWait(driver, 30);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        boolean loggedIn = true;
        driver.navigate().to(readProperty("loginURL"));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("guest_user")));
        driver.findElement(By.id("guest_user")).click();
        driver.findElement(By.id("email")).sendKeys(readProperty("loginUserEmail"));
        driver.findElement(By.id("password")).sendKeys(readProperty("loginUserPassword"));
        driver.findElement(By.xpath("//button[@data-testid=\"login\"]")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@aria-label=\"Pick Up\"]")));
        driver.findElement(By.xpath("//button[@aria-label=\"Pick Up\"]")).click();
        System.out.println("Creating Cart");
        new createCart(readProperty("loginLocation"),readProperty("loginOrderItem"),loggedIn);
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
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//button[@data-testid=\"placeOrder\"])[2]"))).click();
        // There no Dynamic Xpath for the Saved Successfully Container hence using Thread.sleep
        Thread.sleep(5000);
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//button[@data-testid=\"closeBillAddressDialog\"])[2]"))).click();
        }catch (NoSuchElementException | TimeoutException e) {
            System.out.println("Pre-Saved Billing Dialog wasn't Displayed. Proceeding to Gateway");
        }
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@data-testid=\"placeOrderStripe\"]"))).click();
        System.out.print("For Logged In Order: ");
        new paymentPageCancellation();
        new paymentNavigation(loggedIn);
        new gatewayNameInURL();
        System.out.println("Checking Hypertext Protocol for Payment Page");
        new checkHttps();
        new paymentNavigation(loggedIn);
        new checkSavedOrNew(readProperty("loginNewCardNumber"), loggedIn);

        new restartOrderWithData(loggedIn);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@class='pl-1']")));
        String orderIWithHash = driver.findElement(By.xpath("//span[@class='pl-1']")).getText();
        String OrderID = orderIWithHash.replace("#", "");
        System.out.println("TC_06: PASS - Order placed by Logged In User.");
        System.out.println("TC_20: PASS - Payment Gateway is working for a Single Location");

        new checkTransactionID(OrderID);
    }

}
