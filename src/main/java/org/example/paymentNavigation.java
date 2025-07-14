package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class paymentNavigation extends browserSetup{

    public paymentNavigation() throws InterruptedException {
        wait = new WebDriverWait(driver, 30);
        driver.navigate().to(readProperty("paymentNavigationURL"));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@aria-label=\"Pick Up\"]")));
        driver.findElement(By.xpath("//button[@aria-label=\"Pick Up\"]")).click();

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
        wait = new WebDriverWait(driver, 20);
        //h5 is being used for Superb List View & h4 is being used for Superb
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h4[normalize-space()='"+readProperty("loginSecondItem")+"']")));
        driver.findElement(By.xpath("//h4[normalize-space()='"+readProperty("loginSecondItem")+"']")).click();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,100)", "");
        try{
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@data-testid=\"goToCheckout_desktop\"]")));
            driver.findElement(By.xpath("//button[@data-testid=\"goToCheckout_desktop\"]")).click();
        }
        catch (NoSuchElementException | TimeoutException e){
            driver.findElement(By.xpath("//a[@id=\"cart-header\"]")).click();
            driver.findElement(By.xpath("//button[@data-testid=\"goToCheckout_desktop\"]")).click();
        }
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
        catch (NoSuchElementException | TimeoutException e){
            System.out.println("Privacy Policy and Terms and Conditions Checkbox is Not Displayed");
        }
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h5[@data-testid=\"orderTotal\"]")));
        driver.findElement(By.xpath("//input[@data-testid=\""+readProperty("OnlinePaymentMode")+"\"]")).click();
        String checkoutOrderTotal = driver.findElement(By.xpath("//h5[@data-testid=\"orderTotal\"]")).getText();
        driver.findElement(By.xpath("(//button[@data-testid=\"placeOrder\"])[2]")).click();
        // There no Dynamic Xpath for the Saved Successfully Container hence using Thread.sleep
        Thread.sleep(5000);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@data-testid=\"placeOrderStripe\"]"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("back-button")));
    }

}
