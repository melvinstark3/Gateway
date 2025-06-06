package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class loginOrder extends browserSetup{

    public loginOrder() throws InterruptedException {
        boolean loggedIn = true;
        driver.navigate().to("https://gateway.demo-ordering.online/");
        wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("guest_user")));
        driver.findElement(By.id("guest_user")).click();
        driver.findElement(By.id("email")).sendKeys("testing123qazw@gmail.com");
        driver.findElement(By.id("password")).sendKeys("12345678");
        driver.findElement(By.xpath("//button[@data-testid=\"login\"]")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@data-testid=\"modeSelect2\"]")));
        driver.findElement(By.xpath("//button[@data-testid=\"modeSelect2\"]")).click();
        new createCart("First Location","Mama-Mia");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//textarea[@placeholder='Note here...']")));
        driver.findElement(By.xpath("//textarea[@placeholder='Note here...']")).sendKeys("Test Order Comment");
        wait = new WebDriverWait(driver, 30);
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

        driver.navigate().to("https://gateway.demo-ordering.online/");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@data-testid=\"modeSelect2\"]")));
        driver.findElement(By.xpath("//button[@data-testid=\"modeSelect2\"]")).click();
        String locationXpath = "//h5[normalize-space()='" + "First Location" + "']";
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
        wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("subCategory1204469")));
        driver.findElement(By.id("subCategory1204469")).click();
        //This xpath is working for Superb Theme only for now. Will create Xpath for Superb List view if needed
        driver.findElement(By.xpath("//div[@aria-label=\"Mozzarella Sticks\"]")).click();
        driver.findElement(By.xpath("//a[@id=\"cart-header\"]")).click();
        driver.findElement(By.xpath("//button[@data-testid=\"goToCheckout_desktop\"]")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@data-testid=\"paymentMode1\"]")));
        driver.findElement(By.xpath("//input[@data-testid=\"paymentMode1\"]")).click();
        driver.findElement(By.xpath("(//button[@data-testid=\"placeOrder\"])[2]")).click();

        new checkSavedOrNew("4242424242424242", loggedIn);

        new restartOrderWithData(loggedIn);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@class='pl-1']")));
        String orderIWithHash = driver.findElement(By.xpath("//span[@class='pl-1']")).getText();
        String OrderID = orderIWithHash.replace("#", "");
        System.out.println("TC_06: PASS - Order placed by Logged In User.");
        System.out.println("TC_20: PASS - Payment Gateway is working for a Single Location");

        // Check Transaction only in either Guest/Login for now. I can make it dynamic later to check
        // if user is already logged in and skip the login process in such case
        // checkTransactionID(OrderID);
    }

}
