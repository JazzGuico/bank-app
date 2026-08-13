package com.jazz.bank_app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Qualifier;

@Component
public class SomeService {
    private NotificationService notificationService;

    @Autowired
    public SomeService(@Qualifier("smsNotificationService") NotificationService notificationService) {
        this.notificationService = notificationService;
    }
}