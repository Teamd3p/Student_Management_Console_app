use defaultdb;

-- Students Values
SELECT * FROM Students;
INSERT INTO Students (student_name, is_active, admission) VALUES ('Aarav Sharma', 'True', '2023-01-15 10:30:00');
INSERT INTO Students (student_name, is_active, admission) VALUES ('Isha Verma', 'True', '2023-02-10 09:15:00');
INSERT INTO Students (student_name, is_active, admission) VALUES ('Rohit Mehta', 'False', '2022-11-20 13:45:00');
INSERT INTO Students (student_name, is_active, admission) VALUES ('Sneha Kapoor', 'True', '2023-03-12 11:20:00');
INSERT INTO Students (student_name, is_active, admission) VALUES ('Aman Singh', 'False', '2022-12-05 08:50:00');
INSERT INTO Students (student_name, is_active, admission) VALUES ('Priya Desai', 'True', '2023-04-01 14:10:00');
INSERT INTO Students (student_name, is_active, admission) VALUES ('Vikas Nair', 'True', '2023-05-07 12:30:00');
INSERT INTO Students (student_name, is_active, admission) VALUES ('Divya Sethi', 'False', '2022-10-18 15:00:00');
INSERT INTO Students (student_name, is_active, admission) VALUES ('Karan Malhotra', 'True', '2023-06-11 09:00:00');
INSERT INTO Students (student_name, is_active, admission) VALUES ('Neha Joshi', 'True', '2023-07-03 16:20:00');
INSERT INTO Students (student_name, is_active, admission) VALUES ('Manish Pandey', 'False', '2022-09-21 10:10:00');
INSERT INTO Students (student_name, is_active, admission) VALUES ('Simran Kaur', 'True', '2023-01-25 11:40:00');
INSERT INTO Students (student_name, is_active, admission) VALUES ('Aniket Rao', 'False', '2022-08-30 13:00:00');
INSERT INTO Students (student_name, is_active, admission) VALUES ('Riya Jain', 'True', '2023-02-15 10:50:00');
INSERT INTO Students (student_name, is_active, admission) VALUES ('Tarun Bansal', 'True', '2023-03-08 12:05:00');
INSERT INTO Students (student_name, is_active, admission) VALUES ('Pooja Reddy', 'False', '2022-07-11 14:30:00');
INSERT INTO Students (student_name, is_active, admission) VALUES ('Harshit Tiwari', 'True', '2023-04-22 09:45:00');
INSERT INTO Students (student_name, is_active, admission) VALUES ('Meena Yadav', 'False', '2022-06-19 15:20:00');
INSERT INTO Students (student_name, is_active, admission) VALUES ('Rajeev Kulkarni', 'True', '2023-05-30 11:35:00');
INSERT INTO Students (student_name, is_active, admission) VALUES ('Aishwarya Pillai', 'True', '2023-06-15 10:25:00');

-- Teacher Vaules
SELECT * FROM Teachers;
INSERT INTO Teachers (teacher_name, is_active, joining_date) VALUES ('Anita Sharma', 'True', '2022-04-10 09:30:00');
INSERT INTO Teachers (teacher_name, is_active, joining_date) VALUES ('Rajeev Verma', 'True', '2021-06-15 10:00:00');
INSERT INTO Teachers (teacher_name, is_active, joining_date) VALUES ('Sunita Mehra', 'False', '2020-08-05 11:45:00');
INSERT INTO Teachers (teacher_name, is_active, joining_date) VALUES ('Manoj Gupta', 'True', '2019-11-20 08:20:00');
INSERT INTO Teachers (teacher_name, is_active, joining_date) VALUES ('Kavita Desai', 'True', '2023-01-02 12:10:00');
INSERT INTO Teachers (teacher_name, is_active, joining_date) VALUES ('Deepak Joshi', 'False', '2018-07-30 13:55:00');
INSERT INTO Teachers (teacher_name, is_active, joining_date) VALUES ('Priya Nair', 'True', '2022-12-11 14:40:00');
INSERT INTO Teachers (teacher_name, is_active, joining_date) VALUES ('Amitabh Rao', 'True', '2020-03-25 09:15:00');
INSERT INTO Teachers (teacher_name, is_active, joining_date) VALUES ('Neelam Sinha', 'False', '2017-05-18 10:30:00');
INSERT INTO Teachers (teacher_name, is_active, joining_date) VALUES ('Rakesh Kumar', 'True', '2023-03-01 15:00:00');
INSERT INTO Teachers (teacher_name, is_active, joining_date) VALUES ('Divya Reddy', 'False', '2021-02-17 08:45:00');
INSERT INTO Teachers (teacher_name, is_active, joining_date) VALUES ('Sanjay Tiwari', 'True', '2022-06-10 10:20:00');
INSERT INTO Teachers (teacher_name, is_active, joining_date) VALUES ('Poonam Yadav', 'True', '2020-01-12 11:35:00');
INSERT INTO Teachers (teacher_name, is_active, joining_date) VALUES ('Arvind Kulkarni', 'False', '2019-09-28 13:25:00');
INSERT INTO Teachers (teacher_name, is_active, joining_date) VALUES ('Meera Bhatt', 'True', '2023-04-05 09:55:00');
INSERT INTO Teachers (teacher_name, is_active, joining_date) VALUES ('Jitendra Singh', 'True', '2021-11-22 12:00:00');
INSERT INTO Teachers (teacher_name, is_active, joining_date) VALUES ('Ritu Malhotra', 'False', '2018-03-09 14:15:00');
INSERT INTO Teachers (teacher_name, is_active, joining_date) VALUES ('Vinod Bansal', 'True', '2023-05-14 10:10:00');
INSERT INTO Teachers (teacher_name, is_active, joining_date) VALUES ('Sneha Pillai', 'True', '2022-02-08 11:50:00');
INSERT INTO Teachers (teacher_name, is_active, joining_date) VALUES ('Harish Patil', 'False', '2017-10-19 08:30:00');

-- Subjects Values
SELECT * FROM Subjects;
INSERT INTO Subjects (subject_name, subject_description) VALUES ('Mathematics', 'Study of numbers, equations, functions, and geometry.');
INSERT INTO Subjects (subject_name, subject_description) VALUES ('Physics', 'Study of matter, energy, motion, and forces.');
INSERT INTO Subjects (subject_name, subject_description) VALUES ('Chemistry', 'Study of substances, compounds, reactions, and properties.');
INSERT INTO Subjects (subject_name, subject_description) VALUES ('Biology', 'Study of living organisms and life processes.');
INSERT INTO Subjects (subject_name, subject_description) VALUES ('English', 'Study of English language, literature, and grammar.');
INSERT INTO Subjects (subject_name, subject_description) VALUES ('History', 'Study of past events, civilizations, and cultures.');
INSERT INTO Subjects (subject_name, subject_description) VALUES ('Geography', 'Study of Earth’s features, regions, and environment.');
INSERT INTO Subjects (subject_name, subject_description) VALUES ('Computer Science', 'Study of algorithms, programming, and computer systems.');
INSERT INTO Subjects (subject_name, subject_description) VALUES ('Economics', 'Study of production, consumption, and transfer of wealth.');
INSERT INTO Subjects (subject_name, subject_description) VALUES ('Political Science', 'Study of political systems, governance, and theory.');
INSERT INTO Subjects (subject_name, subject_description) VALUES ('Sociology', 'Study of society, social relationships, and culture.');

-- Course Values
SELECT * FROM Courses;
INSERT INTO Courses (course_name, course_fees, is_active) VALUES ('B.Sc Mathematics', 25000.00, '1');
INSERT INTO Courses (course_name, course_fees, is_active) VALUES ('B.Sc Physics', 26000.00, '1');
INSERT INTO Courses (course_name, course_fees, is_active) VALUES ('B.Sc Chemistry', 25500.00, '0');
INSERT INTO Courses (course_name, course_fees, is_active) VALUES ('B.Sc Biology', 27000.00, '1');
INSERT INTO Courses (course_name, course_fees, is_active) VALUES ('B.A English', 22000.00, '1');
INSERT INTO Courses (course_name, course_fees, is_active) VALUES ('B.A History', 21000.00, '0');
INSERT INTO Courses (course_name, course_fees, is_active) VALUES ('B.A Geography', 21500.00, '1');
INSERT INTO Courses (course_name, course_fees, is_active) VALUES ('BCA', 40000.00, '1');
INSERT INTO Courses (course_name, course_fees, is_active) VALUES ('BBA', 42000.00, '0');
INSERT INTO Courses (course_name, course_fees, is_active) VALUES ('B.Com', 30000.00, '1');
INSERT INTO Courses (course_name, course_fees, is_active) VALUES ('M.Sc Mathematics', 28000.00, '1');


-- Student Course Values
SELECT * FROM StudentCourse;
INSERT INTO StudentCourse (student_id, course_id, enrolled_at) VALUES (1, 6, '2023-01-15 10:00:00');
INSERT INTO StudentCourse (student_id, course_id, enrolled_at) VALUES (2, 7, '2023-01-20 11:15:00');
INSERT INTO StudentCourse (student_id, course_id, enrolled_at) VALUES (3, 8, '2023-02-05 09:30:00');
INSERT INTO StudentCourse (student_id, course_id, enrolled_at) VALUES (4, 9, '2023-02-18 14:45:00');
INSERT INTO StudentCourse (student_id, course_id, enrolled_at) VALUES (5, 10, '2023-03-03 08:50:00');
INSERT INTO StudentCourse (student_id, course_id, enrolled_at) VALUES (6, 11, '2023-03-12 12:00:00');
INSERT INTO StudentCourse (student_id, course_id, enrolled_at) VALUES (7, 3, '2023-03-25 10:10:00');
INSERT INTO StudentCourse (student_id, course_id, enrolled_at) VALUES (8, 4, '2023-04-01 09:20:00');
INSERT INTO StudentCourse (student_id, course_id, enrolled_at) VALUES (9, 5, '2023-04-10 13:35:00');
INSERT INTO StudentCourse (student_id, course_id, enrolled_at) VALUES (10, 6, '2023-04-18 11:00:00');
INSERT INTO StudentCourse (student_id, course_id, enrolled_at) VALUES (11, 1, '2023-05-05 15:10:00');
INSERT INTO StudentCourse (student_id, course_id, enrolled_at) VALUES (11, 2, '2023-05-09 16:25:00');
INSERT INTO StudentCourse (student_id, course_id, enrolled_at) VALUES (9, 7, '2023-05-20 10:40:00');
INSERT INTO StudentCourse (student_id, course_id, enrolled_at) VALUES (8, 6, '2023-06-01 14:00:00');
INSERT INTO StudentCourse (student_id, course_id, enrolled_at) VALUES (7, 8, '2023-06-10 09:45:00');
INSERT INTO StudentCourse (student_id, course_id, enrolled_at) VALUES (6, 9, '2023-06-20 13:10:00');
INSERT INTO StudentCourse (student_id, course_id, enrolled_at) VALUES (5, 11, '2023-07-03 12:15:00');
INSERT INTO StudentCourse (student_id, course_id, enrolled_at) VALUES (4, 10, '2023-07-11 11:35:00');
INSERT INTO StudentCourse (student_id, course_id, enrolled_at) VALUES (3, 6, '2023-07-19 08:55:00');
INSERT INTO StudentCourse (student_id, course_id, enrolled_at) VALUES (2, 1, '2023-07-25 10:05:00');

-- CourseSubject Values
SELECT * FROM CourseSubjects;
INSERT INTO CourseSubjects (course_id, subject_id) VALUES (1, 1); -- B.Sc Mathematics → Mathematics
INSERT INTO CourseSubjects (course_id, subject_id) VALUES (1, 10); -- → Political Science
INSERT INTO CourseSubjects (course_id, subject_id) VALUES (2, 2); -- B.Sc Physics → Physics
INSERT INTO CourseSubjects (course_id, subject_id) VALUES (2, 7); -- → Geography
INSERT INTO CourseSubjects (course_id, subject_id) VALUES (3, 3); -- B.Sc Chemistry → Chemistry
INSERT INTO CourseSubjects (course_id, subject_id) VALUES (3, 11); -- → Sociology
INSERT INTO CourseSubjects (course_id, subject_id) VALUES (4, 4); -- B.Sc Biology → Biology
INSERT INTO CourseSubjects (course_id, subject_id) VALUES (5, 5); -- B.A English → English
INSERT INTO CourseSubjects (course_id, subject_id) VALUES (5, 11); -- Not in first 11; skipping
INSERT INTO CourseSubjects (course_id, subject_id) VALUES (6, 6); -- B.A History → History
INSERT INTO CourseSubjects (course_id, subject_id) VALUES (6, 10);
INSERT INTO CourseSubjects (course_id, subject_id) VALUES (7, 7); -- B.A Geography → Geography
INSERT INTO CourseSubjects (course_id, subject_id) VALUES (8, 8); -- BCA → Computer Science
INSERT INTO CourseSubjects (course_id, subject_id) VALUES (9, 9); -- BBA → Economics
INSERT INTO CourseSubjects (course_id, subject_id) VALUES (10, 9); -- B.Com → Economics
INSERT INTO CourseSubjects (course_id, subject_id) VALUES (10, 1); -- → Mathematics
INSERT INTO CourseSubjects (course_id, subject_id) VALUES (11, 1); -- M.Sc Mathematics → Mathematics
INSERT INTO CourseSubjects (course_id, subject_id) VALUES (11, 8); -- → Computer Science
INSERT INTO CourseSubjects (course_id, subject_id) VALUES (2, 1); -- B.Sc Physics → Mathematics
INSERT INTO CourseSubjects (course_id, subject_id) VALUES (4, 3); -- B.Sc Biology → Chemistry
INSERT INTO CourseSubjects (course_id, subject_id) VALUES (6, 2); -- B.A History → Physics
INSERT INTO CourseSubjects (course_id, subject_id) VALUES (1, 7);  -- B.Sc Math → +Geography
INSERT INTO CourseSubjects (course_id, subject_id) VALUES (2, 8);  -- B.Sc Physics → +Computer Science
INSERT INTO CourseSubjects (course_id, subject_id) VALUES (3, 2);  -- B.Sc Chemistry → +Physics
INSERT INTO CourseSubjects (course_id, subject_id) VALUES (4, 9);  -- B.Sc Biology → +Economics
INSERT INTO CourseSubjects (course_id, subject_id) VALUES (5, 11); -- B.A English → +Sociology
INSERT INTO CourseSubjects (course_id, subject_id) VALUES (6, 1);  -- B.A History → +Mathematics
INSERT INTO CourseSubjects (course_id, subject_id) VALUES (7, 5);  -- B.A Geography → +English
INSERT INTO CourseSubjects (course_id, subject_id) VALUES (8, 1);  -- BCA → +Mathematics
INSERT INTO CourseSubjects (course_id, subject_id) VALUES (9, 10); -- BBA → +Political Science
INSERT INTO CourseSubjects (course_id, subject_id) VALUES (11, 3); -- M.Sc Math → +Chemistry


SELECT * FROM TeacherSubjects;
INSERT INTO TeacherSubjects (teacher_id, subject_id) VALUES (1, 1); -- Mathematics
INSERT INTO TeacherSubjects (teacher_id, subject_id) VALUES (2, 2); -- Physics
INSERT INTO TeacherSubjects (teacher_id, subject_id) VALUES (3, 3); -- Chemistry
INSERT INTO TeacherSubjects (teacher_id, subject_id) VALUES (4, 4); -- Biology
INSERT INTO TeacherSubjects (teacher_id, subject_id) VALUES (5, 5); -- English
INSERT INTO TeacherSubjects (teacher_id, subject_id) VALUES (6, 6); -- History
INSERT INTO TeacherSubjects (teacher_id, subject_id) VALUES (7, 7); -- Geography
INSERT INTO TeacherSubjects (teacher_id, subject_id) VALUES (8, 8); -- Computer Science
INSERT INTO TeacherSubjects (teacher_id, subject_id) VALUES (9, 9); -- Economics
INSERT INTO TeacherSubjects (teacher_id, subject_id) VALUES (10, 10); -- Political Science
INSERT INTO TeacherSubjects (teacher_id, subject_id) VALUES (11, 11); -- Sociology
INSERT INTO TeacherSubjects (teacher_id, subject_id) VALUES (12, 1);
INSERT INTO TeacherSubjects (teacher_id, subject_id) VALUES (13, 2);
INSERT INTO TeacherSubjects (teacher_id, subject_id) VALUES (14, 3);
INSERT INTO TeacherSubjects (teacher_id, subject_id) VALUES (15, 4);
INSERT INTO TeacherSubjects (teacher_id, subject_id) VALUES (16, 5);
INSERT INTO TeacherSubjects (teacher_id, subject_id) VALUES (17, 6);
INSERT INTO TeacherSubjects (teacher_id, subject_id) VALUES (18, 7);
INSERT INTO TeacherSubjects (teacher_id, subject_id) VALUES (19, 8);
INSERT INTO TeacherSubjects (teacher_id, subject_id) VALUES (20, 9);
-- Teachers with multiple subjects
INSERT INTO TeacherSubjects (teacher_id, subject_id) VALUES (1, 7);   -- Teacher 1 also teaches Geography
INSERT INTO TeacherSubjects (teacher_id, subject_id) VALUES (2, 5);   -- Teacher 2 also teaches English
INSERT INTO TeacherSubjects (teacher_id, subject_id) VALUES (3, 1);   -- Teacher 3 also teaches Math
INSERT INTO TeacherSubjects (teacher_id, subject_id) VALUES (4, 3);   -- Teacher 4 also teaches Chemistry
INSERT INTO TeacherSubjects (teacher_id, subject_id) VALUES (5, 8);   -- Teacher 5 also teaches CS

-- Multiple teachers for same subject
INSERT INTO TeacherSubjects (teacher_id, subject_id) VALUES (6, 1);   -- Math
INSERT INTO TeacherSubjects (teacher_id, subject_id) VALUES (7, 1);   -- Math
INSERT INTO TeacherSubjects (teacher_id, subject_id) VALUES (8, 2);   -- Physics
INSERT INTO TeacherSubjects (teacher_id, subject_id) VALUES (9, 2);   -- Physics
INSERT INTO TeacherSubjects (teacher_id, subject_id) VALUES (10, 2);  -- Physics

SELECT * FROM Fees;
INSERT INTO Fees (course_id, student_id, amount_paid, amount_pending) VALUES (1, 1, 15000.00, 5000.00);
INSERT INTO Fees (course_id, student_id, amount_paid, amount_pending) VALUES (2, 2, 20000.00, 0.00);
INSERT INTO Fees (course_id, student_id, amount_paid, amount_pending) VALUES (3, 3, 12000.00, 8000.00);
INSERT INTO Fees (course_id, student_id, amount_paid, amount_pending) VALUES (4, 4, 18000.00, 2000.00);
INSERT INTO Fees (course_id, student_id, amount_paid, amount_pending) VALUES (5, 5, 10000.00, 0.00);
INSERT INTO Fees (course_id, student_id, amount_paid, amount_pending) VALUES (6, 6, 8000.00, 2000.00);
INSERT INTO Fees (course_id, student_id, amount_paid, amount_pending) VALUES (7, 7, 25000.00, 0.00);
INSERT INTO Fees (course_id, student_id, amount_paid, amount_pending) VALUES (8, 8, 15000.00, 5000.00);
INSERT INTO Fees (course_id, student_id, amount_paid, amount_pending) VALUES (9, 9, 30000.00, 0.00);
INSERT INTO Fees (course_id, student_id, amount_paid, amount_pending) VALUES (10, 10, 16000.00, 4000.00);
INSERT INTO Fees (course_id, student_id, amount_paid, amount_pending) VALUES (11, 11, 22000.00, 3000.00);
INSERT INTO Fees (course_id, student_id, amount_paid, amount_pending) VALUES (3, 12, 18000.00, 2000.00);
INSERT INTO Fees (course_id, student_id, amount_paid, amount_pending) VALUES (6, 13, 9000.00, 1000.00);
INSERT INTO Fees (course_id, student_id, amount_paid, amount_pending) VALUES (1, 14, 20000.00, 0.00);
INSERT INTO Fees (course_id, student_id, amount_paid, amount_pending) VALUES (7, 15, 12000.00, 3000.00);
INSERT INTO Fees (course_id, student_id, amount_paid, amount_pending) VALUES (8, 16, 15000.00, 0.00);
INSERT INTO Fees (course_id, student_id, amount_paid, amount_pending) VALUES (2, 17, 18000.00, 2000.00);
INSERT INTO Fees (course_id, student_id, amount_paid, amount_pending) VALUES (4, 18, 9000.00, 1000.00);
INSERT INTO Fees (course_id, student_id, amount_paid, amount_pending) VALUES (5, 19, 10000.00, 0.00);
INSERT INTO Fees (course_id, student_id, amount_paid, amount_pending) VALUES (9, 20, 25000.00, 0.00);


SELECT * FROM Profiles;
INSERT INTO Profiles (phone_number, email, address, age, user_type, user_id) VALUES 
('9876543210', 'student1@example.com', 'Pune, MH', 19, 'student', 1),
('9876543211', 'student2@example.com', 'Mumbai, MH', 20, 'student', 2),
('9876543212', 'student3@example.com', 'Nagpur, MH', 18, 'student', 3),
('9876543213', 'student4@example.com', 'Nashik, MH', 21, 'student', 4),
('9876543214', 'student5@example.com', 'Aurangabad, MH', 22, 'student', 5),
('9876543215', 'student6@example.com', 'Solapur, MH', 19, 'student', 6),
('9876543216', 'student7@example.com', 'Kolhapur, MH', 20, 'student', 7),
('9876543217', 'student8@example.com', 'Latur, MH', 21, 'student', 8),
('9876543218', 'student9@example.com', 'Amravati, MH', 23, 'student', 9),
('9876543219', 'student10@example.com', 'Jalgaon, MH', 22, 'student', 10),
('9876543220', 'student11@example.com', 'Pune, MH', 20, 'student', 11),
('9876543221', 'student12@example.com', 'Mumbai, MH', 21, 'student', 12),
('9876543222', 'student13@example.com', 'Nagpur, MH', 19, 'student', 13),
('9876543223', 'student14@example.com', 'Nashik, MH', 22, 'student', 14),
('9876543224', 'student15@example.com', 'Aurangabad, MH', 23, 'student', 15),
('9876543225', 'student16@example.com', 'Solapur, MH', 21, 'student', 16),
('9876543226', 'student17@example.com', 'Kolhapur, MH', 20, 'student', 17),
('9876543227', 'student18@example.com', 'Latur, MH', 19, 'student', 18),
('9876543228', 'student19@example.com', 'Amravati, MH', 18, 'student', 19),
('9876543229', 'student20@example.com', 'Jalgaon, MH', 22, 'student', 20);

INSERT INTO Profiles (phone_number, email, address, age, user_type, user_id) VALUES 
('9123456780', 'teacher1@example.com', 'Pune, MH', 35, 'teacher', 1),
('9123456781', 'teacher2@example.com', 'Mumbai, MH', 40, 'teacher', 2),
('9123456782', 'teacher3@example.com', 'Nagpur, MH', 38, 'teacher', 3),
('9123456783', 'teacher4@example.com', 'Nashik, MH', 42, 'teacher', 4),
('9123456784', 'teacher5@example.com', 'Aurangabad, MH', 37, 'teacher', 5),
('9123456785', 'teacher6@example.com', 'Solapur, MH', 36, 'teacher', 6),
('9123456786', 'teacher7@example.com', 'Kolhapur, MH', 39, 'teacher', 7),
('9123456787', 'teacher8@example.com', 'Latur, MH', 45, 'teacher', 8),
('9123456788', 'teacher9@example.com', 'Amravati, MH', 41, 'teacher', 9),
('9123456789', 'teacher10@example.com', 'Jalgaon, MH', 44, 'teacher', 10),
('9123456790', 'teacher11@example.com', 'Pune, MH', 43, 'teacher', 11),
('9123456791', 'teacher12@example.com', 'Mumbai, MH', 37, 'teacher', 12),
('9123456792', 'teacher13@example.com', 'Nagpur, MH', 39, 'teacher', 13),
('9123456793', 'teacher14@example.com', 'Nashik, MH', 35, 'teacher', 14),
('9123456794', 'teacher15@example.com', 'Aurangabad, MH', 41, 'teacher', 15),
('9123456795', 'teacher16@example.com', 'Solapur, MH', 40, 'teacher', 16),
('9123456796', 'teacher17@example.com', 'Kolhapur, MH', 42, 'teacher', 17),
('9123456797', 'teacher18@example.com', 'Latur, MH', 36, 'teacher', 18),
('9123456798', 'teacher19@example.com', 'Amravati, MH', 44, 'teacher', 19),
('9123456799', 'teacher20@example.com', 'Jalgaon, MH', 43, 'teacher', 20);


