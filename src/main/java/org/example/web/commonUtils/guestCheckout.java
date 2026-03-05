package org.example.web.commonUtils;

import org.example.core.browserSetup;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class guestCheckout extends browserSetup {
    public guestCheckout() throws InterruptedException {
        wait = new WebDriverWait(driver, 30);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        try {
            //Xpath for Modal Sheet Popup
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class=\"modal-content max-h-full overflow-hidden flex flex-col bg-white shadow-sm rounded-t-3xl rounded-b-none md:rounded-xl\"]")));
            System.out.println("Entering Customer Details");
            try {
                driver.findElement(By.xpath("//input[@data-testid=\"first_name\"]")).clear();
                driver.findElement(By.xpath("//input[@data-testid=\"first_name\"]")).sendKeys(readProperty("GuestFirstName"));
            } catch (ElementNotInteractableException e){
                System.out.println("Skipping First Name Field as it was not Displayed!");
            }
            try {
                driver.findElement(By.xpath("//input[@data-testid=\"last_name\"]")).clear();
                driver.findElement(By.xpath("//input[@data-testid=\"last_name\"]")).sendKeys(readProperty("GuestLastName"));
            } catch (ElementNotInteractableException e){
                System.out.println("Skipping Last Name Field as it was not Displayed!");
            }
            try {
                driver.findElement(By.xpath("//input[@data-testid=\"phone\"]")).clear();
                driver.findElement(By.xpath("//input[@data-testid=\"phone\"]")).sendKeys(readProperty("GuestPhoneNumber"));
            } catch (ElementNotInteractableException e){
                System.out.println("Skipping Phone Number Field as it was not Displayed!");
            }
            try {
                driver.findElement(By.name("email")).clear();
                driver.findElement(By.name("email")).sendKeys(readProperty("GuestEmail"));
            } catch (ElementNotInteractableException e){
                System.out.println("Skipping Email Field as it was not Displayed!");
            }
            driver.findElement(By.xpath("//button[@class=\"primary_button w-full text-base font-semibold p-3 px-5 mr-3 rounded-2xl border capitalize text-white ng-star-inserted\"]")).click();
        }
        catch (NoSuchElementException | TimeoutException e){
            System.out.println("Customer Details are Pre-filled. Continuing with Guest Order!");
        }
        try{
            driver.findElement(By.xpath("//button[@class=\"primary_button w-full text-base font-semibold p-3 px-5 mr-3 rounded-2xl border capitalize text-white ng-star-inserted\"]")).click();
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//button[@class=\"primary_button w-full text-base font-semibold p-3 px-5 mr-3 rounded-2xl border capitalize text-white ng-star-inserted\"]")));
        } catch (NoSuchElementException | TimeoutException ignored){}
        Thread.sleep(2000);
        //Home Delivery Address Save Container Check!
        try{
            wait = new WebDriverWait(driver, 10);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@data-testid=\"continueAddAddress\"]")));
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@data-testid=\"continueAddAddress\"]"))).click();
        } catch (TimeoutException ignored){}
        wait = new WebDriverWait(driver, 30);
        // Stale Element Exception if Trying Implicit Wait
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("\"//button[@class=\\\"primary_button w-full text-base font-semibold p-3 px-5 mr-3 rounded-2xl border capitalize text-white ng-star-inserted\\\"]\"")));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h5[@data-testid=\"orderTotal\"]")));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@data-testid=\"continue_order\"]")));
        try{
            driver.findElement(By.xpath("//span[@class=\"cursor-pointer text-red-600 ng-star-inserted\"]")).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@data-testid=\"continueAddAddress\"]")));
            System.out.println("Filling Necessary Checkout Details");
            try{
                driver.findElement(By.xpath("//input[@data-testid=\"company\"]")).clear();
                driver.findElement(By.xpath("//input[@data-testid=\"company\"]")).sendKeys(readProperty("checkoutCompany"));
            } catch (NoSuchElementException e){
                System.out.println("Skipping Company Field as it's not Displayed");
            }
            try{
                driver.findElement(By.xpath("//input[@data-testid=\"street1\"]")).clear();
                driver.findElement(By.xpath("//input[@data-testid=\"street1\"]")).sendKeys(readProperty("checkoutStreet1"));
            } catch (NoSuchElementException e){
                System.out.println("Skipping Street 1 Field as it's not Displayed");
            }
            try{
                driver.findElement(By.xpath("//input[@data-testid=\"street2\"]")).clear();
                driver.findElement(By.xpath("//input[@data-testid=\"street2\"]")).sendKeys(readProperty("checkoutStreet2"));
            } catch (NoSuchElementException e){
                System.out.println("Skipping Street 2 Field as it's not Displayed");
            }
            try{
                driver.findElement(By.xpath("//input[@data-testid=\"city\"]")).clear();
                driver.findElement(By.xpath("//input[@data-testid=\"city\"]")).sendKeys(readProperty("checkoutCity"));
            } catch (NoSuchElementException e){
                System.out.println("Skipping City Field as it's not Displayed");
            }
            try{
                driver.findElement(By.xpath("//input[@data-testid=\"postal_code\"]")).clear();
                driver.findElement(By.xpath("//input[@data-testid=\"postal_code\"]")).sendKeys(readProperty("checkoutPostalCode"));
            } catch (NoSuchElementException e){
                System.out.println("Skipping Postal Code Field as it's not Displayed");
            }
            try{
                driver.findElement(By.name("email")).clear();
                driver.findElement(By.name("email")).sendKeys(readProperty("GuestEmail"));
            } catch (NoSuchElementException e){
                System.out.println("Skipping Email Field as it's not Displayed");
            }
            driver.findElement(By.xpath("//button[@data-testid=\"continueAddAddress\"]")).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h5[@data-testid=\"orderTotal\"]")));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@data-testid=\"continue_order\"]")));
        } catch (NoSuchElementException | TimeoutException e){
            System.out.println("Checkout Details are not required!");
        }
        js.executeScript("window.scrollBy(0,2000)", "");
        Thread.sleep(2000);
        // The System automatically selects back the PaymentMode0 regardless of click, We can remove sleep once dev team fixes this
        try{
            wait = new WebDriverWait(driver, 3);
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
        wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h5[@data-testid=\"orderTotal\"]")));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@data-testid=\"continue_order\"]")));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@data-testid=\""+readProperty("CODPaymentMode")+"\"]")));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@data-testid=\""+readProperty("CODPaymentMode")+"\"]"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@data-testid=\"continue_order\"]")));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[@data-testid=\"continue_order\"]")));
        String selectedPaymentMode = driver.findElement(By.xpath("//input[contains(@aria-label,'Selected')]")).getAttribute("data-testid");
        System.out.println("Selected Payment Mode is " + selectedPaymentMode);
    }
}
