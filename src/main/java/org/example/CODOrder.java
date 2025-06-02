package org.example;

import org.openqa.selenium.By;

public class CODOrder extends guestOrder{
    public static void main(String[] args) throws InterruptedException {
        invokeBrowser();
        orderFlow();
        driver.findElement(By.xpath("//input[@data-testid=\"paymentMode0\"]")).click();
        driver.findElement(By.xpath("(//button[@data-testid=\"placeOrder\"])[2]")).click();
    }
}
