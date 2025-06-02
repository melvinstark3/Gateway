package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class loginOrder extends Main{
    public static void loginOrderFlow() throws InterruptedException {
        boolean loggedIn = true;
        wait = new WebDriverWait(driver, 30);
        driver.navigate().to("https://gateway.demo-ordering.online/");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("guest_user")));
        driver.findElement(By.id("guest_user")).click();
        driver.findElement(By.id("email")).sendKeys("testing123qazw@gmail.com");
        driver.findElement(By.id("password")).sendKeys("12345678");
        driver.findElement(By.xpath("//button[@data-testid=\"login\"]")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@data-testid=\"modeSelect2\"]")));
        driver.findElement(By.xpath("//button[@data-testid=\"modeSelect2\"]")).click();
        createCart("First Location");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//textarea[@placeholder='Note here...']")));
        driver.findElement(By.xpath("//textarea[@placeholder='Note here...']")).sendKeys("Test Order Comment");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@data-testid=\"paymentMode1\"]")));
        //Select Payment method (paymentMode0 = 1st - COD , paymentMode1 = 2nd - Online)
        driver.findElement(By.xpath("//input[@data-testid=\"paymentMode1\"]")).click();
        driver.findElement(By.xpath("(//button[@data-testid=\"placeOrder\"])[2]")).click();
        System.out.print("For Logged In Order: ");
    }

    public static void main(String[] args) throws InterruptedException {
        invokeBrowser();
        loginOrderFlow();
        checkSavedOrNew("4242424242424242",true);
    }
}
