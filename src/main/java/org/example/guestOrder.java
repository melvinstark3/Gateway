package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class guestOrder extends browserSetup {
    public guestOrder() throws InterruptedException {
        boolean loggedIn = false;
        wait = new WebDriverWait(driver, 30);
        driver.navigate().to(readProperty("GuestURL"));
        driver.findElement(By.xpath("//button[@data-testid=\"modeSelect2\"]")).click();
        new createCart(readProperty("GuestLocation"),readProperty("guestOrderItem"));
        System.out.println("Entering Customer Details");
        driver.findElement(By.xpath("//input[@data-testid=\"first_name\"]")).sendKeys(readProperty("GuestFirstName"));
        driver.findElement(By.xpath("//input[@data-testid=\"last_name\"]")).sendKeys(readProperty("GuestLastName"));
        driver.findElement(By.xpath("//input[@data-testid=\"phone\"]")).sendKeys(readProperty("GuestPhoneNumber"));
        driver.findElement(By.xpath("//button[@class=\"primary_button w-full text-base font-semibold p-3 px-5 mr-3 rounded-2xl border capitalize text-white ng-star-inserted\"]")).click();
//        if(driver.findElement(By.xpath("(//button[@data-testid=\"placeOrder\"])[2]")).isEnabled()){
//            driver.findElement(By.xpath("//a[@class=\"underline text-right cursor-pointer text-xs md:text-sm capitalize font-semibold text-bg-secondary hover:text-app-gray-500 hover:no-underline\"][1]")).click();
//        }
//        try {
//            //try with Details Validation First, If found stale, Edit button is tried
//            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[@class='cursor-pointer text-red-600 ng-star-inserted']")));
        Thread.sleep(5000);
        driver.findElement(By.xpath("//span[@class='cursor-pointer text-red-600 ng-star-inserted']")).click();
//        }
//        catch (StaleElementReferenceException e){
//            //Use Edit Element (Only if Edit markers are default, Change [1] to [0] if Edit Location is not allowed)
//
//        }
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("email")));
        driver.findElement(By.name("email")).sendKeys(readProperty("GuestEmail"));
        driver.findElement(By.xpath("//button[@data-testid=\"continueAddAddress\"]")).click();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,2000)", "");
        //driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@data-testid=\"paymentMode0\"]")));
        driver.findElement(By.xpath("//textarea[@placeholder='Note here...']")).sendKeys(readProperty("guestOrderComment"));
        // Stale Element Exception if Trying Implicit Wait
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@data-testid=\"paymentMode0\"]")));
        System.out.println("TC_07: For Guest Order: ");
        //Select Payment method (paymentMode0 = COD, paymentMode1=Online)
        driver.findElement(By.xpath("//input[@data-testid=\"paymentMode0\"]")).click();
        driver.findElement(By.xpath("(//button[@data-testid=\"placeOrder\"])[2]")).click();
        System.out.println("TC_32: Cash on Delivery Payment wih Gateway");
        new pageBackPostOrder();
        new createCart("First Location","Mama-Mia");
        js.executeScript("window.scrollBy(0,2000)", "");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@data-testid=\"paymentMode1\"]")));
        driver.findElement(By.xpath("//textarea[@placeholder='Note here...']")).sendKeys("Test Order Comment");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@data-testid=\"paymentMode1\"]")));
        String checkoutOrderTotal = driver.findElement(By.xpath("//h5[@data-testid=\"orderTotal\"]")).getText();
        new matchAmount(checkoutOrderTotal);
        driver.findElement(By.xpath("//input[@data-testid=\"paymentMode1\"]")).click();
        driver.findElement(By.xpath("(//button[@data-testid=\"placeOrder\"])[2]")).click();

        new checkSavedOrNew(readProperty("guestNewCardNumber"),loggedIn);
        new browserBackPostOrder();
        System.out.println("TC_12: PASS - Payment Successful by a New Card");
        System.out.println("TC_20: PASS - Payment Gateway is working for a Single Location");
    }
}
