package org.example;

import org.openqa.selenium.By;

public class paymentPageCancellation extends guestOrder{
    public static void main(String[] args) throws InterruptedException {
        orderFlow();
        driver.findElement(By.xpath("//input[@data-testid=\"paymentMode1\"]")).click();
        gatewayPageCancellation();
    }
}