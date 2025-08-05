package com.tss.controller;

import java.util.List;
import java.util.Scanner;

import com.tss.model.Subject;
import com.tss.service.SubjectService;

public class SubjectController {

	private SubjectService subjectService;
	private Scanner scanner;
	
	public SubjectController() {
		super();
		this.subjectService = new SubjectService() ;
		this.scanner = new Scanner(System.in);
	}
	
	public void readAllSubjects() {
		 List<Subject> allSubjects = subjectService.readAllSubjects();
	        if (allSubjects.isEmpty()) {
	            System.out.println(">> No subjects found to update.");
	            return ;
	        }

	        System.out.println("+-------------------------------------------------+");
	        System.out.println("|                   SUBJECT LIST                  |");
	        System.out.println("+----+---------------------+----------------------+");
	        System.out.printf("| %-2s | %-19s | %-20s |\n", "ID", "Subject Name", "Description");
	        System.out.println("+----+---------------------+----------------------+");

	        for (Subject s : allSubjects) {
	            System.out.printf("| %-2d | %-19s | %-20s |\n",
	                    s.getSubjectId(),
	                    truncateText(s.getSubjectName(), 19),
	                    truncateText(s.getSubjectDescription(), 20));
	        }
	        System.out.println("+----+---------------------+----------------------+");
	        
	}

	
	public void addSubject() {
	    System.out.println("\n+--------------------------------+");
	    System.out.println("|        ADD NEW SUBJECT         |");
	    System.out.println("+--------------------------------+");

	    System.out.print("Enter Subject Name: ");
	    String subjectName = scanner.nextLine().trim();
	    if (subjectName.isEmpty()) {
	        System.out.println("❌ Subject name cannot be empty.");
	        return;
	    }

	    System.out.print("Enter Subject Description: ");
	    String subjectDescription = scanner.nextLine().trim();
	    if (subjectDescription.length() > 100) {
	        System.out.println("❌ Description too long (max 100 chars).");
	        return;
	    }

	    Subject subject = new Subject(subjectName, subjectDescription); 

	    Subject addedSubject = subjectService.addSubject(subject); // Get back subject with generated ID

	    if (addedSubject != null) {
	    	System.out.println("\n+-----------------------------------------------------------+");
	    	System.out.println("|                  ✅ SUBJECT ADDED SUCCESSFULLY            |");
	    	System.out.println("+-----------------------+-----------------------------------+");
	    	System.out.printf("| %-21s | %-33s |\n", "Subject ID", addedSubject.getSubjectId());
	    	System.out.printf("| %-21s | %-33s |\n", "Subject Name", addedSubject.getSubjectName());
	    	System.out.printf("| %-21s | %-33s |\n", "Description", addedSubject.getSubjectDescription());
	    	System.out.println("+-----------------------+-----------------------------------+\n");
	    } else {
	        System.out.println("❌ Failed to add subject.");
	    }
	}
	
	 
	 public void updateSubject() {
		 
		 List<Subject> allSubjects = subjectService.readAllSubjects();
	        if (allSubjects.isEmpty()) {
	            System.out.println(">> No subjects found to update.");
	            return ;
	        }

	        System.out.println("+-------------------------------------------------+");
	        System.out.println("|                   SUBJECT LIST                  |");
	        System.out.println("+----+---------------------+----------------------+");
	        System.out.printf("| %-2s | %-19s | %-20s |\n", "ID", "Subject Name", "Description");
	        System.out.println("+----+---------------------+----------------------+");

	        for (Subject s : allSubjects) {
	            System.out.printf("| %-2d | %-19s | %-20s |\n",
	                    s.getSubjectId(),
	                    truncateText(s.getSubjectName(), 19),
	                    truncateText(s.getSubjectDescription(), 20));
	        }
	        System.out.println("+----+---------------------+----------------------+");
	        
		 
		 	System.out.println("\n+-------------------------------+");
	        System.out.println("|        UPDATE SUBJECT         |");
	        System.out.println("+-------------------------------+");

	        System.out.print("Enter Subject ID to Update: ");
	        int id = Integer.parseInt(scanner.nextLine());
	        
	        Subject existing = subjectService.getSubjectById(id);

	        if (existing == null) {
	            System.out.println("❌ Subject with ID " + id+ " not found.");
	            return;
	        }
	        System.out.print("Enter New Subject Name: ");
	        String name = scanner.nextLine();

	        System.out.print("Enter New Subject Description: ");
	        String desc = scanner.nextLine();

	        Subject subject = new Subject(id, name, desc);
	        boolean updated = subjectService.updateSubject(subject);
	        

	        if (updated) {
	        	System.out.println("\n+------------------------------------------------------------+");
	        	System.out.println("|                    🔄 SUBJECT UPDATED SUCCESSFULLY         |");
	        	System.out.println("+-----------------------+------------------------------------+");
	        	System.out.printf("| %-21s | %-34d |\n", "Subject ID", subject.getSubjectId());
	        	System.out.printf("| %-21s | %-34s |\n", "Subject Name", subject.getSubjectName());
	        	System.out.printf("| %-21s | %-34s |\n", "Description", subject.getSubjectDescription());
	        	System.out.println("+-----------------------+------------------------------------+\n");
	        } else {
	            System.out.println(" Failed to update subject.");
	        }
	    }
	    private String truncateText(String text, int maxLength) {
	        if (text == null) return "";
	        return text.length() > maxLength ? text.substring(0, maxLength - 3) + "..." : text;
	    }
	    
}

