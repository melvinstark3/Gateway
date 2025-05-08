package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class checkHttps extends Main{
    public static void main(String[] args) throws InterruptedException {
        invokeBrowser();
        driver.navigate().to("https://gateway.demo-ordering.online/");
        driver.findElement(By.xpath("//button[@data-testid=\"modeSelect2\"]")).click();
        createCart("First Location");
        wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@data-testid=\"paymentMode1\"]")));
        //Select Payment method (paymentMode0 = 1st - COD , paymentMode1 = 2nd - Online)
        driver.findElement(By.xpath("//input[@data-testid=\"paymentMode1\"]")).click();
        driver.findElement(By.xpath("(//button[@data-testid=\"placeOrder\"])[2]")).click();
        checkHttps();
    }
}
