package org.example;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class checkHttps extends guestOrder{
    public static void main(String[] args) throws InterruptedException {
        invokeBrowser();
        orderFlow();
        checkHttps();
    }
}
