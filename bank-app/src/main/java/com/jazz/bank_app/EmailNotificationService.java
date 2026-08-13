package com.jazz.bank_app;

import org.springframework.stereotype.Component;

@Component
public class EmailNotificationService implements NotificationService {
    @Override
    public void sendNotification(String accountHolder, String message) {
        System.out.println("Sending email to " + accountHolder + ": " + message);
    }
}