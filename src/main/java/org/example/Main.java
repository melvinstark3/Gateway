package org.example;
import java.time.LocalTime;

public class Main extends browserSetup {

    public static void main(String[] args) throws InterruptedException {
        //invokeBrowser();
        //new guestOrder();
        //quitBrowser();
        invokeBrowser();
        new loginOrder();
        quitBrowser();
    }
}
