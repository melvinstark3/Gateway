package org.example;
import java.time.LocalTime;

public class Main extends browserSetup {

    public static void main(String[] args) throws InterruptedException {
        invokeBrowser();
        LocalTime myObj = LocalTime.now();
        System.out.println(myObj);
        new guestOrder();
        quitBrowser();
        invokeBrowser();
        new loginOrder();
        LocalTime myObjb = LocalTime.now();
        System.out.println(myObjb);
        quitBrowser();
    }
}
