package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

public class gatewayEnable extends browserSetup{

    public gatewayEnable() throws InterruptedException {
        driver.navigate().to("https://demo.onlineorderalert.com/en");
        driver.findElement(By.id("edit-name")).sendKeys("test@restolabs.com");
        driver.findElement(By.id("edit-pass")).sendKeys("test");
        driver.findElement(By.id("edit-submit")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("edit-select-profile")));
        driver.findElement(By.id("edit-select-profile")).clear();
        driver.findElement(By.id("edit-select-profile")).sendKeys("Gateway (1204445)");
        driver.findElement(By.id("edit-grant-access")).click();
        driver.navigate().to("https://demo.onlineorderalert.com/backend/support-executive-revoke-permission");
        driver.findElement(By.xpath("//a[normalize-space()='Masquerade']")).click();
        driver.navigate().to("https://demo.onlineorderalert.com/backend/payment-config-V2");
        driver.findElement(By.name("location_id")).click();
        WebElement location_dropdown = driver.findElement(By.name("location_id"));
        Select locations = new Select(location_dropdown);
        locations.selectByVisibleText("First Location");
        //Waiting for Location Gateway form to Load
        Thread.sleep(5000);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,750)", "");
        driver.findElement(By.name("laravel_payment_v3")).click();
        driver.findElement(By.name("StripeV2[enable]")).click();
    }

}
