package com.tss.app;

import java.util.Scanner;

public class TeacherManagement implements MenuHandler {

    private Scanner scanner = new Scanner(System.in);

    @Override
    public void showMenu() {
        System.out.println("\n+------------------------------+");
        System.out.println("|       TEACHER MANAGEMENT     |");
        System.out.println("+------------------------------+");
        System.out.println("| 1. View All Teachers         |");
        System.out.println("| 2. Add New Teacher           |");
        System.out.println("| 3. Assign Subjects           |");
        System.out.println("| 4. Remove A Subject          |");
        System.out.println("| 5. Search A Teacher          |");
        System.out.println("| 6. Delete A Teacher          |");
        System.out.println("| 7. Go Back                   |");
        System.out.println("+------------------------------+");
        System.out.print("Enter your choice: ");
    }

    @Override
    public void chooseMenu() {
        int choice;

        while (true) {
            showMenu();
            choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    System.out.println(">> Viewing all teachers...");
                    // TODO: implement viewAllTeachers()
                    break;
                case 2:
                    System.out.println(">> Adding a new teacher...");
                    // TODO: implement addNewTeacher()
                    break;
                case 3:
                    System.out.println(">> Assigning subjects to teacher...");
                    // TODO: implement assignSubjects()
                    break;
                case 4:
                    System.out.println(">> Removing a subject from teacher...");
                    // TODO: implement removeSubject()
                    break;
                case 5:
                    System.out.println(">> Searching for a teacher...");
                    // TODO: implement searchTeacher()
                    break;
                case 6:
                    System.out.println(">> Soft deleting a teacher...");
                    // TODO: implement softDeleteTeacher()
                    break;
                case 7:
                    System.out.println(">> Returning to main menu...");
                    return;
                default:
                    System.out.println(">> Invalid choice. Please select from 1 to 7.");
            }
        }
    }
}
