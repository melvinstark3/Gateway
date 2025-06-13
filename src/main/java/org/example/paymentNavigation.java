package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class paymentNavigation extends browserSetup{

    public paymentNavigation(){
        wait = new WebDriverWait(driver, 30);
        driver.navigate().to("https://gateway.demo-ordering.online/");
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
            if (driver.findElement(By.id("policy")).isSelected()) {
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
    }

}
