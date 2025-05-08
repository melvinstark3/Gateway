package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static org.example.Main.driver;

public class defaultSaveCardCheckbox extends loginOrder{
    public static void main(String[] args) throws InterruptedException {
        invokeBrowser();
        loginOrderFlow();
        defaultSaveCardCheckbox();
    }
}