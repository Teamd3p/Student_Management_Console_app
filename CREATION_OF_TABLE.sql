use defaultdb;

CREATE TABLE `student` (
  `student_id` INT NOT NULL AUTO_INCREMENT,
  `student_name` VARCHAR(100) NOT NULL,
  `is_active` BOOLEAN NOT NULL DEFAULT FALSE,
  `admission` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`student_id`)
);

CREATE TABLE `teacher` (
  `teacher_id` INT NOT NULL AUTO_INCREMENT,
  `teacher_name` VARCHAR(100) NOT NULL,
  `is_active` BOOLEAN NOT NULL DEFAULT FALSE,
  `joining_date` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`teacher_id`)
);

CREATE TABLE Subjects (
    subject_id INT AUTO_INCREMENT PRIMARY KEY,
    subject_name VARCHAR(100) NOT NULL,
    subject_description TEXT
);

CREATE TABLE StudentCourse (
    student_id INT,
    course_id INT,
    enrolled_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (student_id, course_id),
    FOREIGN KEY (student_id) REFERENCES students(student_id),
    FOREIGN KEY (course_id) REFERENCES courses(course_id)
);

CREATE TABLE StudentCourse (
    student_course_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    student_id INT NOT NULL,
    course_id INT NOT NULL,
    enrolled_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    UNIQUE (student_id, course_id),
    FOREIGN KEY (student_id) REFERENCES students(student_id),
    FOREIGN KEY (course_id) REFERENCES courses(course_id)
);

CREATE TABLE `defaultdb`.`TeacherSubjects` (
  `teacher_subject_id` INT NOT NULL AUTO_INCREMENT,
  `teacher_id` INT NOT NULL,
  `subject_id` INT NOT NULL,
  PRIMARY KEY (`teacher_subject_id`),
  FOREIGN KEY (`teacher_id`) REFERENCES `Teachers`(`teacher_id`),
  FOREIGN KEY (`subject_id`) REFERENCES `Subjects`(`subject_id`)
);

CREATE TABLE `defaultdb`.`Fees` (
  `fees_id` INT NOT NULL AUTO_INCREMENT,
  `course_id` INT NOT NULL,
  `student_id` INT NOT NULL,
  `amount_paid` DECIMAL(10,2) NOT NULL,
  `amount_pending` DECIMAL(10,2) NOT NULL,
  PRIMARY KEY (`fees_id`),
  FOREIGN KEY (`course_id`) REFERENCES `Courses`(`course_id`),
  FOREIGN KEY (`student_id`) REFERENCES `Students`(`student_id`)
);

CREATE TABLE `defaultdb`.`Courses` (
  `course_id` INT NOT NULL AUTO_INCREMENT,
  `course_name` VARCHAR(45) NOT NULL,
  `course_fees` DECIMAL(10,2) NOT NULL,
  `is_active` BOOLEAN NOT NULL DEFAULT FALSE,
  PRIMARY KEY (`course_id`)
);




