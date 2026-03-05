package org.example.web.modules.timematrix;

import org.example.core.browserSetup;

public class runTimematrix extends browserSetup {
    public runTimematrix() throws InterruptedException {
        invokeBrowser();
        new asapDineinETA();
        new pickupSameDayPreOrder();
        new deliveryLaterWindow();
        new asapDateScheduleHoursETA().negativeCase();
        new asapDateScheduleHoursETA().positiveCase();
        new bufferTime().slotCalculation();
        new bufferTime().slotExpiry();
        new configAnytimeAndSpecificTime().negativeCase();
        new configAnytimeAndSpecificTime().positiveCase();
        new configAnytimeAndCustomTime().negativeCase();
        new configAnytimeAndCustomTime().positiveCase();
        new configSpecificTimeAndAnytime().negativeCase();
        new configSpecificTimeAndAnytime().positiveCase();
        new configSpecificTimeAndSpecificTime().negativeCase();
        new configSpecificTimeAndSpecificTime().checkAvailableDays();
        new configSpecificTimeAndSpecificTime().positiveCase();
        new configSpecificTimeAndCustom().negativeCase();
        new configSpecificTimeAndCustom().checkAvailableDays();
        new configSpecificTimeAndCustom().positiveCase();
        new configCustomAndAnytime().negativeCase();
        new configCustomAndAnytime().positiveCase();
        new configCustomAndSpecificTime().negativeCase();
        new configCustomAndSpecificTime().checkAvailableDays();
        new configCustomAndSpecificTime().positiveCase();
        new configCustomAndCustom().negativeCase();
        new configCustomAndCustom().checkAvailableDays();
        new configCustomAndCustom().positiveCase();
        quitBrowser();
    }
}
