package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class restartOrderWithData extends browserSetup{

    public restartOrderWithData(boolean loggedIn) throws InterruptedException {
        String restartOrderButtonXpath = "//div[@class='bg-white rounded-xl border border-app-gray-300']//span[@class='border-dashed text-sm font-semibold border px-2 py-0.5 rounded-lg cursor-pointer ml-2'][normalize-space()='Click here to start order again']";
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(restartOrderButtonXpath)));
        driver.findElement(By.xpath(restartOrderButtonXpath)).click();
        new createCart(readProperty("restartOrderLocation"),readProperty("restartOrderItem"),loggedIn);
        driver.findElement(By.xpath("//input[@data-testid=\""+readProperty("OnlinePaymentMode")+"\"]")).click();
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
        System.out.print("For 2nd Restart Order: ");
        new secondNewCardPayment(readProperty("restartNewCardNumber"), loggedIn);
    }

}
