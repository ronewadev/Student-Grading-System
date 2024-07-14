
# Student-Grading-System


Project: Student Gradebook System
1. Introduction
The Student Gradebook System is a Java application designed to manage student grades, calculate semester and final marks, generate reports, and handle student information. The application includes functionalities for user authentication, data persistence, and role-based access control for students, instructors, and administrators.
2. Features
•	Student Management:
o	Add and edit student information.
•	Course Management:
o	Add and edit course details.
•	Grade Management:
o	Add and edit grades for each student.
•	Mark Calculation:
o	Calculate semester and final marks.
•	Report Generation:
o	Generate individual and class grade reports.
•	User Authentication:
o	Login/logout functionality.
•	Data Persistence:
o	Database integration for storing data.
•	Advanced Features:
o	Role-based access control.
o	Enhanced UI and user experience.
o	Administrator functionalities for managing student accounts and courses.
3. Project Setup
•	Environment:
o	IDE: IntelliJ IDEA
o	Version Control: Git, GitHub
o	Database: MySQL
o	Framework: JavaFX for UI
•	Setup Instructions:
1.	Clone the repository from GitHub.
2.	Open the project in IntelliJ IDEA.
3.	Set up the database using the provided SQL scripts.
4.	Configure database connection settings in the application.
5.	Build and run the project.
4. User Manual
•	Login:
o	Enter username and password to log in.
o	Different functionalities are available based on user roles (student, instructor, admin).
•	Managing Students:
o	Navigate to the "Students" section.
o	Use the form to add or edit student information.
o	Click "Save" to save the changes.
•	Managing Courses:
o	Navigate to the "Courses" section.
o	Use the form to add or edit course details.
o	Click "Save" to save the changes.
•	Managing Grades:
o	Navigate to the "Grades" section.
o	Select a student and a course.
o	Enter the grade and click "Save" to save the grade.
•	Generating Reports:
o	Navigate to the "Reports" section.
o	Select the type of report (individual or class).
o	Click "Generate" to view the report.
5. Technical Specification
•	Project Architecture:
o	MVC (Model-View-Controller) pattern.
o	Packages: com.gradebook.ui, com.gradebook.models, com.gradebook.services, com.gradebook.utils.
•	Classes:
o	Student: Represents a student with fields for name, ID, etc.
o	Course: Represents a course with fields for name, credits, etc.
o	Grade: Represents a grade with fields for student, course, and mark.
o	User: Represents a user with fields for username, password, and role.
•	Database Schema:
o	Tables: users, students, courses, grades.
o	Relationships: Foreign keys to link grades with students and courses.
6. Testing
•	Test Cases:
o	Add test cases for all functionalities (adding/editing students, courses, grades, etc.).
o	Test user authentication and role-based access control.
o	Validate data input and error handling.
7. Presentation
•	Slide Deck:
o	Introduction to the project.
o	Key features and functionalities.
o	Demo of the application.
o	Discussion of challenges and solutions.
o	Future enhancements and improvements.
8. Conclusion
The Student Gradebook System is a comprehensive application designed to manage student grades efficiently. With functionalities for student, course, and grade management, as well as advanced features like user authentication and data persistence, the system provides a robust solution for educational institutions.

Project Structure:
Main Class
Main.jav

Models:
Student.java
Course.java
Grade.java
User.java

Controllers:
LoginController.java
MainController.java
StudentController.java

