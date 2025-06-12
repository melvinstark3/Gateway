package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class loginOrder extends browserSetup{

    public loginOrder() throws InterruptedException {
        boolean loggedIn = true;
        driver.navigate().to(readProperty("loginURL"));
        wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("guest_user")));
        driver.findElement(By.id("guest_user")).click();
        driver.findElement(By.id("email")).sendKeys(readProperty("loginUserEmail"));
        driver.findElement(By.id("password")).sendKeys(readProperty("loginUserPassword"));
        driver.findElement(By.xpath("//button[@data-testid=\"login\"]")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@data-testid=\"modeSelect2\"]")));
        driver.findElement(By.xpath("//button[@data-testid=\"modeSelect2\"]")).click();
        System.out.print("Creating Cart");
        new createCart(readProperty("loginLocation"),readProperty("loginOrderItem"));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@data-testid=\"paymentMode1\"]")));
        driver.findElement(By.xpath("//textarea[@placeholder='Note here...']")).sendKeys(readProperty("loginOrderComment"));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@data-testid=\"paymentMode1\"]")));
        //Select Payment method (paymentMode0 = 1st - COD , paymentMode1 = 2nd - Online)
        driver.findElement(By.xpath("//input[@data-testid=\"paymentMode1\"]")).click();
        driver.findElement(By.xpath("(//button[@data-testid=\"placeOrder\"])[2]")).click();
        System.out.print("For Logged In Order: ");
        new paymentPageCancellation();
        new paymentNavigation();
        new gatewayNameInURL();
        System.out.println("Checking Hypertext Protocol for Payment Page");
        new checkHttps();
        new paymentNavigation();
        new sharedURLPayment();
        driver.navigate().to(readProperty("loginURL"));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@data-testid=\"modeSelect2\"]")));
        driver.findElement(By.xpath("//button[@data-testid=\"modeSelect2\"]")).click();
        String locationXpath = "//h5[normalize-space()='" + readProperty("loginLocation") + "']";
        driver.findElement(By.xpath(locationXpath)).click();
        try {
            wait = new WebDriverWait(driver, 2);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@data-testid=\"Yes\"]")));
            //For Some reason even after Completed, We get Cart reset Popup, Handle it with Yes for now.
            driver.findElement(By.xpath("//button[@data-testid=\"Yes\"]")).click();
        } catch (NoSuchElementException | TimeoutException e) {
            System.out.println("Continuing to Menu");
        }
        driver.findElement(By.xpath("(//button[@data-testid=\"chooserContinue\"])[2]")).click();

        //This could be optimized by directly choosing Cart, if Cart inconsistency Issues are solved by Dev Team
        wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[normalize-space()='"+readProperty("loginSecondItemCategory")+"']")));
        driver.findElement(By.xpath("//span[normalize-space()='"+readProperty("loginSecondItemCategory")+"']")).click();
        //h5 is being used for Superb List View & h4 is being used for Superb
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h4[normalize-space()='"+readProperty("loginSecondItem")+"']")));
        driver.findElement(By.xpath("//h4[normalize-space()='"+readProperty("loginSecondItem")+"']")).click();
        driver.findElement(By.xpath("//a[@id=\"cart-header\"]")).click();
        driver.findElement(By.xpath("//button[@data-testid=\"goToCheckout_desktop\"]")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@data-testid=\"paymentMode1\"]")));
        driver.findElement(By.xpath("//input[@data-testid=\"paymentMode1\"]")).click();
        try{
            if (driver.findElement(By.id("policy")).isEnabled()) {
                System.out.println("Privacy Policy and Terms & Conditions are Already Accepted");
            } else {
                driver.findElement(By.id("policy")).click();
                System.out.println("Privacy Policy and Terms & Conditions Accepted"); // As per your requirement
            }
        }
        catch (NoSuchElementException | TimeoutException e){
            System.out.println("Privacy Policy and Terms and Conditions Checkbox is Not Displayed");
        }
        driver.findElement(By.xpath("(//button[@data-testid=\"placeOrder\"])[2]")).click();

        new checkSavedOrNew(readProperty("loginNewCardNumber"), loggedIn);

        new restartOrderWithData(loggedIn);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@class='pl-1']")));
        String orderIWithHash = driver.findElement(By.xpath("//span[@class='pl-1']")).getText();
        String OrderID = orderIWithHash.replace("#", "");
        System.out.println("TC_06: PASS - Order placed by Logged In User.");
        System.out.println("TC_20: PASS - Payment Gateway is working for a Single Location");

        // Check Transaction only in either Guest/Login for now. I can make it dynamic later to check
        // if user is already logged in and skip the login process in such case
        //new checkTransactionID(OrderID);
    }

}
