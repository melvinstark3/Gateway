package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class savedCardPayment extends Main {

    public static void orderflow() throws InterruptedException {
        driver.navigate().to("https://gateway.demo-ordering.online/");
        wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("guest_user")));
        driver.findElement(By.id("guest_user")).click();
        driver.findElement(By.id("email")).sendKeys("testing123qazw@gmail.com");
        driver.findElement(By.id("password")).sendKeys("12345678");
        driver.findElement(By.xpath("//button[@data-testid=\"login\"]")).click();
        Thread.sleep(3000);
        driver.findElement(By.xpath("//button[@data-testid=\"modeSelect2\"]")).click();
        createCart("First Location","Mama-Mia");
        wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@data-testid=\"paymentMode1\"]")));
        //Select Payment method (paymentMode0 = 1st - COD , paymentMode1 = 2nd - Online)
        driver.findElement(By.xpath("//input[@data-testid=\"paymentMode1\"]")).click();
        driver.findElement(By.xpath("(//button[@data-testid=\"placeOrder\"])[2]")).click();
        System.out.print("For Logged In Order: ");
    }

    public static void checkSavedOrNew(String cardNumber, boolean loggedIn) throws InterruptedException {
        try {
            Thread.sleep(5000);
            System.out.println("Checking Saved Cards");
            List<WebElement> elements = driver.findElements(By.id("new-card"));
            if (!elements.isEmpty()) {
                savedCardPayment();
            } else {
                newCardPayment(cardNumber, loggedIn);
                Thread.sleep(10000);
                orderflow();
                savedCardPayment();
            }
            Thread.sleep(10000);

        } catch (NoSuchElementException e) {
            newCardPayment(cardNumber, loggedIn);
            Thread.sleep(10000);
            orderflow();
            savedCardPayment();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        orderflow();
        checkSavedOrNew("4242424242424242",true);
    }
}
