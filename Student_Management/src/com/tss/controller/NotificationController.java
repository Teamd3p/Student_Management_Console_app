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
	        String green = "\u001B[32m"; 
	        String reset = "\u001B[0m";  
	        System.out.println("╔══════════════════════════════════╗");
	        System.out.printf ("║  Current Preference: %-14s%s       ║\n", green + currentPreference + reset, "");
	        System.out.println("╚══════════════════════════════════╝");

	        // Action menu
	        System.out.println("╔══════════════════════════════════╗");
	        System.out.println("║      WHAT DO YOU WANT TO DO?     ║");
	        System.out.println("╠══════════════════════════════════╣");
	        System.out.println("║ 1. Add/Update Notification       ║");
	        System.out.println("║ 2. Remove Notification           ║");
	        System.out.println("║ 3. Back                          ║");
	        System.out.println("╚══════════════════════════════════╝");
			int actionChoice = InputValidator.readChoice("Enter your choice: ");

			String newPreference = currentPreference;

			if (actionChoice == 1) {
				showMenu();

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

			    showMenu();

			    int removeChoice = InputValidator.readChoice("Enter your choice: ");

			    // Validation before making any changes
			    if (removeChoice == 1 && !currentPreference.contains("SMS")) {
			        System.out.println("No SMS notifications to remove.");
			        return false;
			    }
			    if (removeChoice == 2 && !currentPreference.contains("Email")) {
			        System.out.println("No Email notifications to remove.");
			        return false;
			    }
			    if (removeChoice == 3 && !currentPreference.equals("Both")) {
			        System.out.println("Current preference is " + currentPreference + ", not Both.");
			        return false;
			    }

			    // Proceed with removal
			    switch (removeChoice) {
			        case 1: // Remove SMS
			            newPreference = currentPreference.equals("Both") ? "Email" : "None";
			            break;
			        case 2: // Remove Email
			            newPreference = currentPreference.equals("Both") ? "SMS" : "None";
			            break;
			        case 3: // Remove Both
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

	private void showMenu() {
		System.out.println("╔══════════════════════════════════╗");
		System.out.println("║   CHOOSE NEW NOTIFICATION TYPE   ║");
		System.out.println("╠══════════════════════════════════╣");
		System.out.println("║ 1. SMS                           ║");
		System.out.println("║ 2. Email                         ║");
		System.out.println("║ 3. Both                          ║");
		System.out.println("║ 4. Cancel                        ║");
		System.out.println("╚══════════════════════════════════╝");

	}
}
