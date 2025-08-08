package com.tss.app;

import java.util.Scanner;

import com.tss.controller.DashboardController;

public class DashboardManagement implements MenuHandler{

	 Scanner scanner = new Scanner(System.in);
	 
	@Override
	public void showMenu() {
		final String RESET = "\u001B[0m";
		final String PURPLE = "\u001B[35m";
		final String BOLD = "\u001B[1m";

		System.out.println(PURPLE + BOLD + "╔═══════════════════════════════╗" + RESET);
		System.out.println(PURPLE + BOLD + "║      DASHBOARD MANAGEMENT     ║" + RESET);
		System.out.println(PURPLE + BOLD + "╠═══════════════════════════════╣" + RESET);
		System.out.println(PURPLE + "║ 1. Show Dashboard Data        ║" + RESET);
		System.out.println(PURPLE + "║ 2. Show Graphical Data of Fees║" + RESET);
		System.out.println(PURPLE + "║ 3. Go Back                    ║" + RESET);
		System.out.println(PURPLE + "╚═══════════════════════════════╝" + RESET);
		System.out.print("Enter your choice: ");
		
	}

	@Override
	public void chooseMenu() {
		 int choice;
		 DashboardController dashboardController = new DashboardController();
		 
	    while (true) {
		showMenu();
        choice = scanner.nextInt();
        scanner.nextLine();
        switch (choice) {
            case 1:
            	dashboardController.showDashboard();
                break;
            
            case 2 :
            	dashboardController.showFeesBarChart();
            	break;
            
            case 3:
            	System.out.println("Returning to Main Menu ...");
            	return;
            	
            default :
            	System.out.println("Invalid Choice!!");
	}

}
	}
}
