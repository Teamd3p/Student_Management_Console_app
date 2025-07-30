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
	    List<Subject> subjects = subjectService.readAllSubjects();

	    System.out.println("\n+--------------------------------------------------------------+");
	    System.out.println("|                        SUBJECT RECORDS                       |");
	    System.out.println("+--------------------------------------------------------------+");
	    System.out.printf("| %-10s | %-25s | %-20s |\n", "Subject ID", "Subject Name", "Description");
	    System.out.println("+--------------------------------------------------------------+");

	    for (Subject subject : subjects) {
	        System.out.printf("| %-10d | %-25s | %-20s |\n",
	                subject.getSubjectId(),
	                subject.getSubjectName(),
	                subject.getSubjectDescription());
	    }

	    System.out.println("+--------------------------------------------------------------+");
	}

	
	public void addSubject() {
	    System.out.println("\n+------------------------------+");
	    System.out.println("|        ADD NEW SUBJECT       |");
	    System.out.println("+------------------------------+");

	    System.out.print("Enter Subject Name: ");
	    String name = scanner.nextLine();

	    System.out.print("Enter Subject Description: ");
	    String desc = scanner.nextLine();

	    Subject subject = new Subject(name, desc); // No ID taken from user

	    Subject addedSubject = subjectService.addSubject(subject); // Get back subject with generated ID

	    if (addedSubject != null) {
	        System.out.println("\n+------------------------------------------------------+");
	        System.out.println("|                   ✅ SUBJECT ADDED                   |");
	        System.out.println("+------------------------------------------------------+");
	        System.out.printf("| %-15s : %-32d |\n", "Subject ID", addedSubject.getSubjectId());
	        System.out.printf("| %-15s : %-32s |\n", "Subject Name", addedSubject.getSubjectName());
	        System.out.printf("| %-15s : %-32s |\n", "Description", addedSubject.getSubjectDescription());
	        System.out.println("+------------------------------------------------------+\n");
	    } else {
	        System.out.println("❌ Failed to add subject.");
	    }
	}
	
	 
	 public void updateSubject() {
		 
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
	        	 System.out.println("\n+------------------------------------------------------+");
	             System.out.println("|                 🔄 SUBJECT UPDATED                   |");
	             System.out.println("+------------------------------------------------------+");
	             System.out.printf("| %-15s : %-32d |\n", "Subject ID", subject.getSubjectId());
	             System.out.printf("| %-15s : %-32s |\n", "Subject Name", subject.getSubjectName());
	             System.out.printf("| %-15s : %-32s |\n", "Description", subject.getSubjectDescription());
	             System.out.println("+------------------------------------------------------+\n");
	        } else {
	            System.out.println(" Failed to update subject.");
	        }
	    }
	 
	 public void deleteSubjectById() {
		 	
		 	System.out.println("\n+-------------------------------+");
	        System.out.println("|        DELETE SUBJECT         |");
	        System.out.println("+-------------------------------+");

	        System.out.print("Enter Subject ID to Delete: ");
	        int subjectId = Integer.parseInt(scanner.nextLine());
		 
	        Subject subject = subjectService.getSubjectById(subjectId);

	        if (subject == null) {
	            System.out.println(" Subject with ID " + subjectId + " not found.");
	            return;
	        }

	        boolean deleted = subjectService.deleteSubjectById(subjectId);

	        if (deleted) {
	        	System.out.println("\n+------------------------------------------------------+");
	            System.out.println("|                🗑️ SUBJECT DELETED                    |");
	            System.out.println("+------------------------------------------------------+");
	            System.out.printf("| %-15s : %-32d |\n", "Subject ID", subject.getSubjectId());
	            System.out.printf("| %-15s : %-32s |\n", "Subject Name", subject.getSubjectName());
	            System.out.printf("| %-15s : %-32s |\n", "Description", subject.getSubjectDescription());
	            System.out.println("+------------------------------------------------------+\n");
	        } else {
	            System.out.println(" Failed to delete subject with ID " + subjectId + ".");
	        }
	    }
}

