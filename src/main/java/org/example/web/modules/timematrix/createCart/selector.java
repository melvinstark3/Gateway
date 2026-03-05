package org.example.web.modules.timematrix.createCart;

import org.example.core.browserSetup;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class selector extends browserSetup {

    public String handleSelector(String orderMode, String Location, String orderTime, String orderSlot) throws InterruptedException {
        // I have Assigned Order Slot in a way that it should be parsed either as Null to Use Last Slot of the First Day by Default,
        // Or a Number parsed as String for Slot Number, For example: Third Slot should be parsed as "3"
        // Or a Specific Valid Slot with proper Time AM/PM defined, For Example: 4:15 AM
        // Or a Window including the "Hyphen Sign" that is used to validate a Window being Used. For Example: 00:00 AM-11:59 PM
        // Apart from this, We are also returning the Selected Slot for Buffer Time Calculation
        wait = new WebDriverWait(driver, 30);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");
        String finalOrderSlot = null;
        if (orderMode.equalsIgnoreCase("Home Delivery")) {
            try {
                // The Button Class keeps changing, Use Contains method on Second Button if displayed
                WebElement editAddressButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//button[contains(@class, 'mode__change__button transition-all duration-300 hover:scale-100 scale-[0.85]')])[2]")));
                //Use JS Executor for Edit Address SVG till Dev Team doesn't add a Proper CssSelector
                js.executeScript("arguments[0].click();", editAddressButton);
                System.out.println("Editing the Locked Home Delivery Field");
            } catch (TimeoutException e) {
                System.out.println("Home Delivery Field is Empty!");
            }
            String homeDeliveryTextField = "typeahead-prevent-manual-entry";
            driver.findElement(By.id(homeDeliveryTextField)).clear();
            Thread.sleep(3000);
            driver.findElement(By.id(homeDeliveryTextField)).sendKeys(readProperty("homeDeliveryAddress"));
            //We Need to Clear some Letters if Copy&Pasting/SendingKeys etc. to the Search Field for
            //Location Suggestions to Show up. We can remove this logic, Once Dev team fixes this up.
            for (int backspaceLetters = 0; backspaceLetters < 4; backspaceLetters++) {
                driver.findElement(By.id(homeDeliveryTextField)).sendKeys(Keys.BACK_SPACE);
                Thread.sleep(500);
            }
            wait = new WebDriverWait(driver, 60);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ngb-typeahead-window[@class=\"dropdown-menu show ng-star-inserted\"]")));
            Thread.sleep(5000);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@class=\"dropdown-item active ng-star-inserted\"]"))).click();
//            List<WebElement> addressList = driver.findElements(By.xpath("//ngb-typeahead-window[@class=\"dropdown-menu show ng-star-inserted\"]"));
//            for (WebElement e : addressList) {
//                //Right Now even with Same Text, it's choosing the Suggestion After Same Text but as Addresses might
//                // Differ from Suggestion List we are choosing the Contains method as leaving it as it is.
//                // We Can Change this later if needed or List Suggestions will be displayed better with Css Selectors from Dev Team
//                if (e.getText().contains(readProperty("homeDeliveryAddress"))) {
//                    e.click();
//                    break;
//                }
//            }
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h5[@data-testid=\"deliveryTitle\"]")));
            try {
                js.executeScript("arguments[0].scrollIntoView();", wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h5[normalize-space()='" + Location + "']"))));
                wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//h5[normalize-space()='" + Location + "']"))).click();
            } catch (NoSuchElementException | TimeoutException ignored) {
            }
        }

        if (orderMode.equalsIgnoreCase("Pick Up")) {
            try {
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("geocoder")));
                // EXPERIMENTAL: Right now there is no Robust or Sure way to find when the Current location has been fetched
                // Upon Further Investigation it was found that a green Marker with image Xpath Mentioned below
                // "//img[@src="https://raw.githubusercontent.com/pointhi/leaflet-color-markers/master/img/marker-icon-2x-green.png"]"
                // is displayed when Current Location has been fetched but as there no proper way to check for its visibility Hence for now
                // We are using 10 seconds of Sleep to Give the API time to fetch Location. We can also experiment with a JS Executor but
                // for now we are going ahead with this Experimental Approach until Further Discussion. Till Then, It's Recommended to
                // Not Use Pickup Map Feature when Running the Automation Suite
                Thread.sleep(10000);
                js.executeScript("arguments[0].scrollIntoView();", wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h5[normalize-space()='" + Location + "']"))));
                wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//h5[normalize-space()='" + Location + "']"))).click();
                driver.findElement(By.xpath("//button[@class=\"px-3 py-1 font-bold text-white border-2 text-xs md:text-sm rounded-md w-full select-button\"]")).click();
                System.out.println("Confirming Location");
            } catch (NoSuchElementException | TimeoutException e) {
                System.out.println("Selecting Pickup Location from the List");
            }
        }
        js.executeScript("arguments[0].scrollIntoView();", wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h5[normalize-space()='" + Location + "']"))));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//h5[normalize-space()='" + Location + "']"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h5[@data-testid=\"deliveryTitle\"]")));
        js.executeScript("window.scrollBy(0,2000)", "");
        try {
            wait = new WebDriverWait(driver, 3);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@data-testid=\"Yes\"]")));
            //For Some reason even after Completed, We get Cart reset Popup, Handle it with Yes for now.
            driver.findElement(By.xpath("//button[@data-testid=\"Yes\"]")).click();
        } catch (NoSuchElementException | TimeoutException ignored) {
        }
        js.executeScript("window.scrollBy(0,2000)", "");
        wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h5[@data-testid=\"deliveryTitle\"]")));

        String prepTimeInfo=null;

        List<WebElement> elements = driver.findElements(
                By.xpath("//span[@data-testid='asapInfo'] | //div[@data-testid='asapInfo']/div")
        );

        if (!elements.isEmpty()) {
            prepTimeInfo = elements.get(0).getText();
        }

        if (prepTimeInfo != null && prepTimeInfo.contains("We are not accepting online orders at the present moment, you can only explore the menu.")) {
            finalOrderSlot = "Store is Closed at the Moment! Skipping Cart Creation";
            System.out.println(finalOrderSlot);
        } else {
            if (orderTime.equalsIgnoreCase("asap")) {
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("asapbtn"))).click();
                System.out.println("Continuing with ASAP Ordering!");
                LocalTime now = LocalTime.now();
                DateTimeFormatter secondsFormatter = DateTimeFormatter.ofPattern("hh:mm:ss a");
                String formattedTime = now.format(secondsFormatter);
                System.out.println("Current Time on Selector Page for ASAP is " + formattedTime);
            } else {
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("laterbtn"))).click();
                System.out.println("Continuing with Later Ordering!");
                WebElement selectorTime = driver.findElement(By.xpath("//select[@aria-label=\"Select Time\"]"));

                Select selectorTimeDropDown = new Select(selectorTime);
                List<WebElement> timeslots = selectorTimeDropDown.getOptions();

                if (orderSlot != null) {
                    if (orderSlot.contains("AM") || orderSlot.contains("PM")) {
                        selectorTimeDropDown.selectByValue(orderSlot);
                        System.out.println("Selecting Custom Time Slot : " + orderSlot);
                        finalOrderSlot = orderSlot;
                    } else {
                        int orderIndex = Integer.parseInt(orderSlot) - 1;
                        selectorTimeDropDown.selectByIndex(orderIndex);
                        String firstTimeSlotValue = timeslots.get(orderIndex).getText().trim();
                        System.out.println("Selecting First Available Slot : " + firstTimeSlotValue);
                        finalOrderSlot = firstTimeSlotValue;
                    }
                } else {
                    int lastTimeSlotIndex = timeslots.size() - 1;
                    String lastTimeSlotValue = timeslots.get(lastTimeSlotIndex).getText().trim();
                    if (lastTimeSlotValue.contains("-")) {
                        System.out.println("Instead of Time Slots, Timing Window is being displayed");
                    } else {
                        LocalTime lastTime = LocalTime.parse(lastTimeSlotValue, formatter);
                        System.out.println("Last Available Slot: " + lastTimeSlotValue);
                        int lastSecondTimeSlotIndex = timeslots.size() - 2;
                        String lastSecondTimeSlotValue = timeslots.get(lastSecondTimeSlotIndex).getText().trim();
                        LocalTime secondLastTime = LocalTime.parse(lastSecondTimeSlotValue, formatter);
                        System.out.println("Second Last Available Slot: " + lastSecondTimeSlotValue);
                        long minutesDifference = ChronoUnit.MINUTES.between(secondLastTime, lastTime);
                        System.out.println("Difference in Minutes: " + minutesDifference);
                        finalOrderSlot = lastTimeSlotValue;
                    }

                    selectorTimeDropDown.selectByIndex(lastTimeSlotIndex);
                    System.out.println("Selected Timing : " + lastTimeSlotValue);
                }
            }

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//button[@data-testid=\"chooserContinue\"])[2]")));
            driver.findElement(By.xpath("(//button[@data-testid=\"chooserContinue\"])[2]")).click();
        }
        return finalOrderSlot;
    }

    public Set<String> checkDates(String Location){
        wait = new WebDriverWait(driver, 30);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView();", wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h5[normalize-space()='" + Location + "']"))));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//h5[normalize-space()='" + Location + "']"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h5[@data-testid=\"deliveryTitle\"]")));
        js.executeScript("window.scrollBy(0,2000)", "");
        try {
            wait = new WebDriverWait(driver, 3);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@data-testid=\"Yes\"]")));
            //For Some reason even after Completed, We get Cart reset Popup, Handle it with Yes for now.
            driver.findElement(By.xpath("//button[@data-testid=\"Yes\"]")).click();
        } catch (NoSuchElementException | TimeoutException ignored) {
        }
        js.executeScript("window.scrollBy(0,2000)", "");
        wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h5[@data-testid=\"deliveryTitle\"]")));

        String prepTimeInfo=null;

        List<WebElement> elements = driver.findElements(
                By.xpath("//span[@data-testid='asapInfo'] | //div[@data-testid='asapInfo']/div")
        );

        if (!elements.isEmpty()) {
            prepTimeInfo = elements.get(0).getText();
        }

        Set<String> enabledDaysOfWeek = new HashSet<>();

        if (prepTimeInfo != null && prepTimeInfo.contains("We are not accepting online orders at the present moment, you can only explore the menu.")) {
            System.out.println("Store is Closed at the Moment! Skipping Cart Creation");
        } else {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("laterbtn"))).click();
            System.out.println("Continuing with Later Ordering!");
            driver.findElement(By.name("selectedDate")).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("mat-calendar")));
            List<WebElement> enabledDates = driver.findElements(By.xpath("//td/button[@aria-label and not(@aria-disabled='true')]"));
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy");
            for (WebElement date : enabledDates) {
                String ariaLabel = date.getAttribute("aria-label");
                LocalDate parsedDate = LocalDate.parse(ariaLabel, formatter);
                String dayOfWeek = parsedDate.getDayOfWeek().toString();
                enabledDaysOfWeek.add(dayOfWeek);
            }
        }
        return enabledDaysOfWeek;
    }
}
