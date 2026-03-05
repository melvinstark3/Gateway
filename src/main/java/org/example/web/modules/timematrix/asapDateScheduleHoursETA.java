package org.example.web.modules.timematrix;

import org.example.core.browserSetup;
import org.example.web.commonUtils.guestCheckout;
import org.example.web.modules.timematrix.createCart.addMenuItem;
import org.example.web.modules.timematrix.createCart.cartHeader;
import org.example.web.modules.timematrix.createCart.selector;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class asapDateScheduleHoursETA extends browserSetup {

    //For Negative Case Dates Allowed to Order and allow Delivery, both are Set from December 1, 2026, to December 31, 2026.
    //In the future, if Required, and dates are changed, Kindly make required changes over here including the comment.

    public void negativeCase() throws InterruptedException {
        String orderTime = "asap";
        String orderMode = "Dine In";
        new orderMode(orderMode);
        System.out.println("Checking Negative Case for Ordering Availability on Specific dates.");
        new selector().handleSelector(orderMode, "Eighth Location", orderTime, null);
    }


    //For Positive Case Dates Allowed to Order and allow Delivery, both are Set from Jan 1, 2026, to November 30, 2026.
    //In the future, if Required, and dates are changed, Kindly make required changes over here including the comment.

    public void positiveCase() throws InterruptedException {
        String orderTime = "asap";
        String orderMode = "Dine In";
        System.out.println("Checking Positive Case for Ordering Availability on Specific dates.");
        new selector().handleSelector(orderMode, "Seventh Location", orderTime, null);
        new addMenuItem();
        new cartHeader();
        new guestCheckout();
        LocalTime now = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:ss a");
        String formattedTime = now.format(formatter);
        System.out.println("Current Time while Placing Order is " + formattedTime);
        driver.findElement(By.xpath("//span[@data-testid=\"continue_order\"]")).click();
        System.out.println("CASE: Specific Dates Order, ASAP, Unit Hours Used for ETA");
        try {
            wait = new WebDriverWait(driver, 60);
            String restartOrderButtonXpath = "//div[@class='bg-white rounded-xl border border-app-gray-300']//span[@class='border-dashed text-sm font-semibold border px-2 py-0.5 rounded-lg cursor-pointer ml-2'][normalize-space()='Click here to start order again']";
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(restartOrderButtonXpath)));
            String estimatedFulfillmentTime = driver.findElement(By.xpath("//span[@class=\"pl-2\"]")).getText();
            System.out.println("Estimated Fulfillment Time For This Order is " + estimatedFulfillmentTime + " mins");
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(restartOrderButtonXpath))).click();
            System.out.println("Order was Placed Successfully");
        } catch (NoSuchElementException | TimeoutException e) {
            System.out.println("Order FAILED!");
        }

    }
}
