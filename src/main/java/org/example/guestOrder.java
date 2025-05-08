package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class guestOrder extends Main{

    public static void orderFlow() throws InterruptedException {
        driver.navigate().to("https://gateway.demo-ordering.online/");
        driver.findElement(By.xpath("//button[@data-testid=\"modeSelect2\"]")).click();
        createCart("First Location");
        driver.findElement(By.xpath("//input[@data-testid=\"first_name\"]")).sendKeys("Test First Name");
        driver.findElement(By.xpath("//input[@data-testid=\"last_name\"]")).sendKeys("Test Last Name");
        driver.findElement(By.xpath("//input[@data-testid=\"phone\"]")).sendKeys("Test number");
        driver.findElement(By.xpath("//button[@class=\"primary_button w-full text-base font-semibold p-3 px-5 mr-3 rounded-2xl border capitalize text-white ng-star-inserted\"]")).click();
        Thread.sleep(5000);
        driver.findElement(By.xpath("//span[@class='cursor-pointer text-red-600 ng-star-inserted']")).click();
        Thread.sleep(3000);
        driver.findElement(By.name("email")).sendKeys("testing123qazw@gmail.com");
        driver.findElement(By.xpath("//button[@data-testid=\"continueAddAddress\"]")).click();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,2000)", "");
        Thread.sleep(10000);
        wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@data-testid=\"paymentMode1\"]")));
        System.out.print("TC_07: For Guest Order: ");
    }

    public static void main(String[] args) throws InterruptedException {
        invokeBrowser();
        orderFlow();
        //Select Payment method (paymentMode0 = COD, paymentMode1=Online)
        driver.findElement(By.xpath("//input[@data-testid=\"paymentMode1\"]")).click();
        driver.findElement(By.xpath("(//button[@data-testid=\"placeOrder\"])[2]")).click();
    }
}
