package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class gatewayPageCancellation extends browserSetup{

    public gatewayPageCancellation() throws InterruptedException {
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
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("back-button")));

        try {
            List<WebElement> elements = driver.findElements(By.id("new-card"));
            if (!elements.isEmpty()) {
                driver.findElement(By.xpath("//label[@class=\"saved__payment__card add_new_card_btn\"]")).click();
            }
        } catch (NoSuchElementException | TimeoutException e) {
            driver.findElement(By.id("submit-button")).click();
        } finally {
            driver.findElement(By.id("submit-button")).click();
        }

        System.out.println("Attempting Cancellation on WorldPay Gateway Page");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ctl00_mainPage_btn_Cancel")));
        driver.findElement(By.id("ctl00_mainPage_btn_Cancel")).click();
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("cart-header"))).isDisplayed();
            System.out.println("User is redirected to Menu");
        } catch (NoSuchElementException | TimeoutException e) {
            System.out.println("User is not redirected to Menu");
        }
        System.out.println("Cancellation of Payment takes User to the URL: " + driver.getCurrentUrl());
    }

}
