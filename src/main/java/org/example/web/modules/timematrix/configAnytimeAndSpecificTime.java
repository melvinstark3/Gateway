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
import java.util.Set;

public class configAnytimeAndSpecificTime extends browserSetup {
    public void negativeCase() {
        String orderMode = "Dine In";
        new orderMode(orderMode);
        Set<String> enabledWeekDays = new selector().checkDates("Ninth Location");
        Set<String> requiredDays = Set.of("MONDAY", "WEDNESDAY", "THURSDAY");

        if (enabledWeekDays.containsAll(requiredDays)) {
            System.out.println("CASE: PASS: Store is Open as per Specific Time, Weekly Fixed Days");
        } else {
            System.out.println("CASE: FAIL: Store is Open but not as per Specific Time, Weekly Fixed Days");
        }
    }

    public void positiveCase() throws InterruptedException {
        String orderTime = "later";
        String orderMode = "Dine In";
        new orderMode(orderMode);
        new selector().handleSelector(orderMode, "Ninth Location", orderTime, null);
        new addMenuItem();
        new cartHeader();
        new guestCheckout();
        LocalTime now = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:ss a");
        String formattedTime = now.format(formatter);
        System.out.println("Current Time while Placing Order is " + formattedTime);
        driver.findElement(By.xpath("//span[@data-testid=\"continue_order\"]")).click();
        System.out.println("Schedule Logic CONFIG:\nPlacing Order Between: Anytime, 24x7");
        System.out.println("Allow pickup / delivery Between: Specific Time, Weekly Fixed");
        try {
            wait = new WebDriverWait(driver, 60);
            String restartOrderButtonXpath = "//div[@class='bg-white rounded-xl border border-app-gray-300']//span[@class='border-dashed text-sm font-semibold border px-2 py-0.5 rounded-lg cursor-pointer ml-2'][normalize-space()='Click here to start order again']";
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(restartOrderButtonXpath)));
            String estimatedFulfillmentTime = driver.findElement(By.xpath("//span[@class=\"pl-2\"]")).getText();
            System.out.println("Estimated Fulfillment Time For This Order is " + estimatedFulfillmentTime);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(restartOrderButtonXpath))).click();
            System.out.println("Order was Placed Successfully");
        } catch (NoSuchElementException | TimeoutException e) {
            System.out.println("Order FAILED!");
        }
    }
}
