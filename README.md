# 🎓 Student Management System

A console-based **Student Management System** developed using Java.

This mini project is designed to demonstrate important Java concepts such as **Object-Oriented Programming, Collections, Exception Handling, File Handling, Interfaces, Abstract Classes, Sorting, and Multithreading**.

The application allows users to add, view, search, update, delete, sort, and analyze student records through a simple menu-driven interface.

---

## 📌 Project Overview

The Student Management System manages student information such as:

- Student ID
- Name
- Course
- Age
- Marks
- Grade
- Pass/Fail Status

Student data is stored in a text file so that records are available even after restarting the application.

The application also includes statistics, course-wise student counts, top-performing students, sorting, and automatic background data saving.

---

## ✨ Features

### 👨‍🎓 Student Management

- Add new student
- View all students
- Search student by ID
- Search student by name
- Search students by course
- Update student details
- Delete student record
- Prevent duplicate Student IDs

### 📊 Student Analysis

- Calculate student grade automatically
- Display Pass/Fail status
- Calculate average marks
- Find highest marks
- Find lowest marks
- Display top-performing students
- Display total number of students
- Display course-wise student count

### 🔄 Sorting

Students can be sorted by:

- Marks - Highest to Lowest
- Marks - Lowest to Highest
- Name - A to Z
- Student ID

### 💾 File Handling

Student records are stored in:

```text
data/students.txt
````

The application:

* Loads existing records when started
* Saves records after modifications
* Allows manual saving
* Automatically saves data in the background

### 🛡️ Exception Handling

The application handles invalid inputs such as:

* Invalid numbers
* Invalid age
* Invalid marks
* Empty input
* Duplicate Student ID
* Student not found
* File handling errors

The program does not terminate unexpectedly because of normal user input errors.

### 🧵 Multithreading

A background thread performs automatic saving of student data every **60 seconds**.

This demonstrates basic Java multithreading using:

```java
Thread
Runnable
sleep()
```

---

# 🛠️ Technologies Used

* Java
* Java Collections Framework
* File I/O
* Exception Handling
* Multithreading
* Object-Oriented Programming
* Git
* GitHub

---

# 🧠 Java Concepts Demonstrated

## 1. Classes and Objects

The project uses multiple classes:

```text
Person
Student
StudentManager
AutoSaveTask
Main
```

Objects are created to represent students and manage application operations.

---

## 2. Constructors

Constructors are used to initialize student objects.

Example:

```java
Student student = new Student(
    id,
    name,
    course,
    age,
    marks
);
```

---

## 3. `this` Keyword

The project uses object references and constructors to initialize class data.

---

## 4. Static

The Java application uses static methods where appropriate, especially in the main application class.

---

## 5. Encapsulation

Student fields are declared as private:

```java
private String studentId;
private String course;
private double marks;
```

Getters and setters are used to access and modify the data.

---

## 6. Inheritance

The project demonstrates inheritance using:

```text
Person
   ↓
Student
```

The `Student` class extends the abstract `Person` class.

---

## 7. Abstraction

`Person` is an abstract class containing the common properties of a person.

```java
public abstract class Person
```

It also contains an abstract method:

```java
public abstract void displayDetails();
```

---

## 8. Polymorphism

The `Student` class overrides the `displayDetails()` method:

```java
@Override
public void displayDetails() {
    // Student details
}
```

This demonstrates method overriding and runtime polymorphism.

---

## 9. Interface

The project contains a `Manageable` interface:

```java
public interface Manageable {

    void addStudent();

    void viewStudents();

    void updateStudent();

    void deleteStudent();
}
```

`StudentManager` implements this interface.

---

# 📦 Collections Used

## ArrayList

Used to store the complete list of students.

```java
ArrayList<Student> students;
```

It provides dynamic storage and easy iteration.

---

## HashMap

Used for quick Student ID-based lookup.

```java
HashMap<String, Student> studentMap;
```

Example:

```text
STU101 → Rahul Sharma
STU102 → Priya Singh
STU103 → Aman Kumar
```

---

## HashSet

Used to maintain unique course names.

```java
HashSet<String> courses;
```

This prevents duplicate course entries.

---

# 🗂️ Project Structure

```text
Student-Management-System/
│
├── src/
│   ├── Main.java
│   │
│   ├── model/
│   │   ├── Person.java
│   │   └── Student.java
│   │
│   ├── interfacee/
│   │   └── Manageable.java
│   │
│   ├── service/
│   │   └── StudentManager.java
│   │
│   ├── exception/
│   │   ├── DuplicateStudentException.java
│   │   └── StudentNotFoundException.java
│   │
│   └── thread/
│       └── AutoSaveTask.java
│
├── data/
│   └── students.txt
│
│
├── README.md
```

---

# ⚙️ Requirements

Before running the project, make sure Java is installed.

Check Java version:

```bash
java -version
```

Check Java compiler:

```bash
javac -version
```

Recommended:

```text
Java 17 or higher
```

---

# 🚀 How to Run

## Step 1: Clone the Repository

```bash
git clone <your-github-repository-url>
```

Move into the project:

```bash
cd Student-Management-System
```

---

## Step 2: Compile the Project

### Windows PowerShell

```powershell
javac -d out src/Main.java src/model/*.java src/interfacee/*.java src/service/*.java src/exception/*.java src/thread/*.java
```

---

## Step 3: Run the Application

```powershell
java -cp out Main
```

---

# 🖥️ Application Menu

```text
==============================================
          STUDENT MANAGEMENT SYSTEM
==============================================
1.  Add Student
2.  View All Students
3.  Search Student by ID
4.  Search Student by Name
5.  Search Students by Course
6.  Update Student
7.  Delete Student
8.  Display Statistics
9.  Display Top Performing Students
10. Sort Students
11. Course-wise Student Count
12. Save Data
13. Exit
==============================================
Enter your choice:
```

---

# ➕ Adding a Student

Example:

```text
========== ADD STUDENT ==========

Enter Student ID: STU101
Enter Name: Rahul Sharma
Enter Course: Computer Science
Enter Age: 21
Enter Marks: 87.5

Student added successfully!
Grade  : A
Status : PASS
```

---

# 📋 Viewing Students

Example:

```text
========== ALL STUDENTS ==========

-----------------------------------------------
Student ID : STU101
Name       : Rahul Sharma
Course     : Computer Science
Age        : 21
Marks      : 87.5
Grade      : A
Status     : PASS
-----------------------------------------------
```

---

# 🔍 Searching Student

Search by Student ID:

```text
========== SEARCH BY ID ==========

Enter Student ID: STU101

-----------------------------------------------
Student ID : STU101
Name       : Rahul Sharma
Course     : Computer Science
Age        : 21
Marks      : 87.5
Grade      : A
Status     : PASS
-----------------------------------------------
```

---

# 📈 Statistics

Example:

```text
========== STUDENT STATISTICS ==========

Total Students : 5
Average Marks  : 78.60
Highest Marks  : 96.00
Lowest Marks   : 38.00
Passed Students: 4
Failed Students: 1
Unique Courses : 3
```

---

# 🏆 Top Performing Students

The system automatically sorts students according to marks and displays the top five students.

Example:

```text
========== TOP PERFORMERS ==========

1. Ananya Verma | STU104 | Marks: 96.0 | Grade: A+
2. Priya Singh  | STU102 | Marks: 94.0 | Grade: A+
3. Rahul Sharma | STU101 | Marks: 87.5 | Grade: A
```

---

# 📚 Course Statistics

Example:

```text
========== COURSE STATISTICS ==========

Computer Science        : 2 students
Data Science            : 1 students
Artificial Intelligence : 1 students
```

---

# 📝 Grade System

The application automatically calculates grades according to marks.

| Marks    | Grade |
| -------- | ----- |
| 90 - 100 | A+    |
| 80 - 89  | A     |
| 70 - 79  | B     |
| 60 - 69  | C     |
| 50 - 59  | D     |
| Below 50 | F     |

Pass criteria:

```text
Marks >= 40 → PASS
Marks < 40  → FAIL
```

---

# 💾 File Storage

Student records are stored in:

```text
data/students.txt
```

Example:

```text
STU101|Rahul Sharma|Computer Science|21|87.5
STU102|Priya Singh|Data Science|20|94.0
STU103|Aman Kumar|Information Technology|22|72.0
```

When the program starts, it loads the existing records automatically.

---

# 🧵 Multithreading

The project contains a background auto-save thread.

The thread runs independently from the main menu.

```text
Main Thread
    |
    ├── Add Student
    ├── Search Student
    ├── Update Student
    ├── Delete Student
    └── View Statistics

Background Thread
    |
    └── Auto-save every 60 seconds
```

This demonstrates practical use of multithreading.

---

# ⚠️ Exception Handling

The application handles invalid user inputs.

Example:

```text
Enter Marks: abc

Please enter a valid number.
```

Invalid marks:

```text
Enter Marks: 125

Marks must be between 0 and 100.
```

Duplicate Student ID:

```text
Enter Student ID: STU101

Error: Student ID already exists!
```

Student not found:

```text
Enter Student ID: STU999

Error: Student with ID STU999 not found.
```

---

# 🧪 Sample Test Data

Use the following records for testing:

```text
STU101 | Rahul Sharma | Computer Science | 21 | 87.5
STU102 | Priya Singh | Data Science | 20 | 94
STU103 | Aman Kumar | Information Technology | 22 | 72
STU104 | Ananya Verma | Artificial Intelligence | 21 | 96
STU105 | Rohit Gupta | Computer Science | 20 | 38
```

---

# 🔐 Data Validation

The application validates:

* Student ID
* Student name
* Course
* Age
* Marks
* Duplicate Student IDs
* Empty input

Age validation:

```text
5 <= Age <= 100
```

Marks validation:

```text
0 <= Marks <= 100
```

---

# 🔄 Application Flow

```text
Start Application
       |
       ↓
Load Student Data
       |
       ↓
Start Auto-Save Thread
       |
       ↓
Display Main Menu
       |
       ├── Add Student
       ├── View Students
       ├── Search Student
       ├── Update Student
       ├── Delete Student
       ├── Statistics
       ├── Top Students
       ├── Sort Students
       ├── Course Statistics
       └── Save Data
       |
       ↓
Save Data
       |
       ↓
Exit Application
```

---

# 📚 Learning Outcomes

Through this project, the following Java concepts were practiced:

* Classes and Objects
* Constructors
* `this` keyword
* `static`
* Encapsulation
* Inheritance
* Polymorphism
* Abstraction
* Interfaces
* ArrayList
* HashMap
* HashSet
* Comparator
* Exception Handling
* Custom Exceptions
* File Handling
* BufferedReader
* BufferedWriter
* FileWriter
* Multithreading
* Runnable
* Thread
* Menu-driven programming
* Input validation

---

# 🔮 Future Enhancements

The project can be extended with:

* Java Swing / JavaFX GUI
* MySQL database
* Student login system
* Admin login
* Attendance management
* Subject-wise marks
* Semester-wise records
* PDF report generation
* CSV export
* Email notifications
* REST API using Spring Boot
* Web-based student dashboard
* Role-based access control

---

# 👨‍💻 Author

**Divankar Singh**

B.Tech Computer Science & Engineering

---

# 📄 License

This project is created for **educational and internship purposes**.

---

⭐ If you find this project useful, consider giving the repository a star!

````
