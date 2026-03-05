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
import java.time.temporal.ChronoUnit;

public class bufferTime extends browserSetup {

    int minimumPrepTime = 5;
    int bufferTime = 1;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:ss a");

    public void slotCalculation() throws InterruptedException {
        String orderTime = "later";
        String orderMode = "Dine In";
        LocalTime finalOrderSlot = null;
        new orderMode(orderMode);
        System.out.println("Checking First Slot Calculation Case for Buffer Time.");
        String selectedOrderSlot = new selector().handleSelector(orderMode, "Fifth Location", orderTime, "1");
        if (selectedOrderSlot.contains("AM") || selectedOrderSlot.contains("PM")){
            selectedOrderSlot = selectedOrderSlot.replace(" ", ":00 ");
            finalOrderSlot = LocalTime.parse(selectedOrderSlot, formatter);
        } else {
            System.out.println("No Valid Slot was Selected for this Order");
        }
        LocalTime now = LocalTime.now();
        String currentTimeString = now.format(formatter);
        LocalTime currentTime = LocalTime.parse(currentTimeString, formatter);
        System.out.println("Current Time before Placing Order is " + currentTimeString);

        LocalTime thresholdTime = currentTime.plusMinutes(minimumPrepTime+bufferTime);

        int minute = thresholdTime.getMinute();
        int remainder = minute % 5;

        LocalTime expectedSlot;

        if (remainder == 0 && thresholdTime.getSecond() == 0) {
            expectedSlot = thresholdTime.plusMinutes(5);
        } else {
            int minutesToAdd = 5 - remainder;
            expectedSlot = thresholdTime.plusMinutes(minutesToAdd);
        }

        expectedSlot = expectedSlot.withSecond(0);

        System.out.println("Expected Slot:  " + expectedSlot);
        System.out.println("Displayed Slot: " + finalOrderSlot);

        if (finalOrderSlot.equals(expectedSlot)){
            System.out.println("CASE: PASS: Next Available Slot Calculated including Buffer Time is Correct");
        } else {
            System.out.println("CASE: FAIL: Next Available Slot Calculated including Buffer Time is InCorrect");
        }
    }

    public void slotExpiry() throws InterruptedException {
        String orderTime = "later";
        String orderMode = "Dine In";
        LocalTime finalOrderSlot = null;
        new orderMode(orderMode);
        System.out.println("Checking Positive Case for Buffer Time.");
        String selectedOrderSlot = new selector().handleSelector(orderMode, "Fifth Location", orderTime, "1");
        if (selectedOrderSlot.contains("AM") || selectedOrderSlot.contains("PM")){
            selectedOrderSlot = selectedOrderSlot.replace(" ", ":00 ");
            finalOrderSlot = LocalTime.parse(selectedOrderSlot, formatter);
        } else {
            System.out.println("No Valid Slot was Selected for this Order");
        }
        new addMenuItem();
        new cartHeader();
        new guestCheckout();
        LocalTime now = LocalTime.now();
        String currentTimeString = now.format(formatter);
        LocalTime currentTime = LocalTime.parse(currentTimeString, formatter);
        System.out.println("Current Time before Placing Order is " + currentTimeString);

        if (currentTime.isBefore(finalOrderSlot)) {
            long secondsToWait = ChronoUnit.SECONDS.between(currentTime, finalOrderSlot);
            System.out.println("WAITING: " + secondsToWait + " seconds are remaining to Cross Slot Time");
            Thread.sleep(secondsToWait * 1000);
        } else {
            System.out.println("WARNING! Current Time has already passed Slot Cutoff Time!");
        }
        driver.findElement(By.xpath("//span[@data-testid=\"continue_order\"]")).click();
        try{
            wait = new WebDriverWait(driver, 30);
            String validation = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@data-pc-section=\"summary\"]"))).getText();
            System.out.println("CASE PASS: Slot Expiry Order Stopped with Validation : " + validation);
        } catch (Exception undefined){
            try {
                wait = new WebDriverWait(driver, 60);
                String restartOrderButtonXpath = "//div[@class='bg-white rounded-xl border border-app-gray-300']//span[@class='border-dashed text-sm font-semibold border px-2 py-0.5 rounded-lg cursor-pointer ml-2'][normalize-space()='Click here to start order again']";
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(restartOrderButtonXpath)));
                System.out.println("CASE FAIL: Under Buffer Time Order was Placed even after Time Slot Expiry");
            } catch (NoSuchElementException | TimeoutException e) {
                System.out.println("Something Went Wrong!");
            }
        }
    }
}
