package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;

public class defaultSaveCardCheckbox extends browserSetup{

    public defaultSaveCardCheckbox() {
        try {
            WebElement checkbox = driver.findElement(By.id("save-card"));
            String saveCardStatement = driver.findElement(By.xpath("//label[@for=\"save-card\"]")).getText();

            if (checkbox.isEnabled()) {
                System.out.println("TC_39: PASS - Save Card is Enabled by Default");
                if (saveCardStatement.equals("Save this card for future payments")) {

                    System.out.println("Save Card Statement is '" + saveCardStatement + "'");
                }
            } else {
                System.out.println("TC_39: FAIL - Save Card is not Enabled By Default");
            }

        } catch (NoSuchElementException | TimeoutException e) {
            System.out.println("Save Card Checkbox is not Displayed");
        }
    }

}
