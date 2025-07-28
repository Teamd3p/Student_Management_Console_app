# Student_Management_Console_app
...
1. Introduction
1.1 Purpose
This document outlines the requirements for a console-based application to manage students, courses, teachers, fees, and dashboard views.

1.2 Scope
The system allows an administrator to perform CRUD operations on students, courses, teachers, subjects, and fees. It uses Core Java and JDBC for backend communication with a MySQL database. Design patterns and proper exception handling will be used to ensure modular, robust, and scalable code.

1.3 Definitions, Acronyms, and Abbreviations
DAO – Data Access Object

DTO – Data Transfer Object

JDBC – Java Database Connectivity

MVC – Model-View-Controller

CRUD – Create, Read, Update, Delete (Delete will be implemented as soft delete)

2. Overall Description
2.1 Product Perspective
This is a standalone application with a menu-driven console interface. No external dependencies are required apart from the JDBC driver and a running MySQL database.

2.2 User Characteristics
The application will be used by a single admin-type user who manages all data entries and queries through the console.

2.3 Assumptions and Dependencies
Java 8+

MySQL 8+

JDBC Driver configured

Database and required tables created prior to execution

3. Functional Requirements
3.1 Student Management
View All Students

Add New Student

Assign A Course

View All Courses

Search A Student

Delete A Student (soft delete)

Go Back

3.2 Course Management
View All Courses

Add New Course

Add Subjects in A Course

View Subjects of A Course

Search A Course

Delete A Course (soft delete)

View Subjects of A Course

Go Back

3.3 Teacher Management
View All Teachers

Add New Teacher

Assign Subjects

Remove A Subject

Search A Teacher

Delete A Teacher (soft delete)

Go Back

3.4 Fees Management
View Total Paid Fees

View Total Pending Fees

View Fees By Student

View Fees By Course

Update Fees Of A Course

Total Earning

Exit

3.5 Dashboard
Tabular view of:

Sr. No.

StudentID

Name

Course

Paid Fee

Pending Fee

Total Fee

Subjects

Teachers

3.6 Exit
Graceful termination of application and resources.

4. Non-Functional Requirements
Performance: Operations should return results within 1 second.

Usability: Menu-driven interface with clear prompts.

Security: Input validation and SQL injection protection using prepared statements.

Reliability: Robust exception handling.

Portability: Platform-independent.

Maintainability: Code structured using design patterns like DAO, MVC.

5. System Design and Architecture
Architecture: MVC architecture

Design Patterns:

DAO for database operations

Singleton for DB connection

DTO for data transport

Exception Handling: Try-catch for all input/output and SQL operations

6. Use Case Example
Use Case: Add New Student
Input: Name, Course (optional)

Process: Store in student records, map with student-course relationship

Output: Confirmation message

7. Constraints
No GUI

Single user

Tables and DB setup must be done before execution

All deletes are implemented as soft deletes using an is_active or status flag

8. Future Enhancements
GUI or web-based interface

Authentication & authorization

Multi-user support
