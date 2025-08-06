package com.tss.controller;

import com.tss.service.NotificationService;
import com.tss.util.InputValidator;
import com.tss.exception.ValidationException;

public class NotificationController {

    private NotificationService notificationService;

    public NotificationController() {
        this.notificationService = new NotificationService();
    }

    public boolean manageStudentNotification(int studentId) {
        try {
            String currentPreference = notificationService.getNotificationPreference(studentId);

            if (currentPreference == null) {
                System.out.println("No notification preference found for this student.");
                return false;
            }

            System.out.println("\n>>> Current Notification Preference: " + currentPreference);
            System.out.println("1. Update Notification");
            System.out.println("2. Remove Notification");
            System.out.println("3. Cancel");
            int actionChoice = InputValidator.readChoice("Enter your choice: ");

            String newPreference = currentPreference;

            if (actionChoice == 1) {
                System.out.println("\nChoose new Notification Preference:");
                System.out.println("1. SMS");
                System.out.println("2. Email");
                System.out.println("3. Both");
                System.out.println("4. Cancel");

                int updateChoice = InputValidator.readChoice("Enter your choice: ");
                switch (updateChoice) {
                    case 1:
                        newPreference = "SMS";
                        break;
                    case 2:
                        newPreference = "Email";
                        break;
                    case 3:
                        newPreference = "Both";
                        break;
                    case 4:
                        System.out.println("Update canceled.");
                        return false;
                }

            } else if (actionChoice == 2) {
                System.out.println("\nWhich notification do you want to remove?");
                System.out.println("1. SMS");
                System.out.println("2. Email");
                System.out.println("3. Both");
                System.out.println("4. Cancel");

                int removeChoice = InputValidator.readChoice("Enter your choice: ");
                switch (removeChoice) {
                    case 1:
                        newPreference = currentPreference.equals("Both") ? "Email" : "None";
                        break;
                    case 2:
                        newPreference = currentPreference.equals("Both") ? "SMS" : "None";
                        break;
                    case 3:
                        newPreference = "None";
                        break;
                    case 4:
                        System.out.println("Remove canceled.");
                        return false;
                }

            } else {
                System.out.println("Canceled.");
                return false;
            }

            boolean updated = notificationService.updateNotificationPreference(studentId, newPreference);
            if (updated) {
                System.out.println("Notification preference changed to: " + newPreference);
            } else {
                System.out.println("Failed to update notification preference.");
            }
            return updated;

        } catch (ValidationException e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }
}
