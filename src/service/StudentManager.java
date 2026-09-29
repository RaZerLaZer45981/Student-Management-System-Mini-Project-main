package service;

import exception.DuplicateStudentException;
import exception.StudentNotFoundException;
import interfacee.Manageable;
import model.Enrollment;
import model.Student;

import java.io.*;
import java.util.*;

public class StudentManager implements Manageable {

    private final ArrayList<Student> students = new ArrayList<>();

    // Student ID -> Student
    private final HashMap<String, Student> studentMap = new HashMap<>();

    // Stores unique courses
    private final HashSet<String> courses = new HashSet<>();

    private final Scanner sc;

    private final String FILE_NAME = "data/students.txt";

    public StudentManager(Scanner sc) {
        this.sc = sc;
        loadFromFile();
    }

    // ================= ADD STUDENT =================

    @Override
    public void addStudent() {

        System.out.println("\n========== ADD STUDENT ==========");

        String id = readNonEmptyString("Enter Student ID: ");

        try {
            if (studentMap.containsKey(id)) {
                throw new DuplicateStudentException(
                        "Student ID already exists!"
                );
            }

            if (id.contains("|")) {
                System.out.println("Student ID cannot contain |");
                return;
            }

            String name = readNonEmptyString("Enter Name: ");

            if (name.contains("|")) {
                System.out.println("Name cannot contain |");
                return;
            }

            int age = readInt("Enter Age: ");

            if (age < 5 || age > 100) {
                System.out.println("Age must be between 5 and 100.");
                return;
            }

            Student student = new Student(
                    id,
                    name,
                    age
            );

            int courseCount = readInt("How many courses is this student taking? ");

            for (int i = 1; i <= courseCount; i++) {

                String course = readCourseName("Enter Course " + i + ": ");

                Enrollment enrollment = student.enroll(course);

                for (double grade : readGrades("Enter grades for " + course)) {
                    enrollment.addGrade(grade);
                }
            }

            students.add(student);
            studentMap.put(id, student);
            rebuildCourses();

            System.out.println("\nStudent added successfully!");
            student.displayDetails();

            saveToFile();

        } catch (DuplicateStudentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // ================= VIEW STUDENTS =================

    @Override
    public void viewStudents() {

        System.out.println("\n========== ALL STUDENTS ==========");

        if (students.isEmpty()) {
            System.out.println("No student records found.");
            return;
        }

        for (Student student : students) {
            student.displayDetails();
        }

        System.out.println("Total Students: " + students.size());
    }

    // ================= SEARCH BY ID =================

    public void searchById() {

        System.out.println("\n========== SEARCH BY ID ==========");

        String id = readNonEmptyString("Enter Student ID: ");

        try {
            Student student = studentMap.get(id);

            if (student == null) {
                throw new StudentNotFoundException(
                        "Student with ID " + id + " not found."
                );
            }

            student.displayDetails();

        } catch (StudentNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // ================= SEARCH BY NAME =================

    public void searchByName() {

        System.out.println("\n========== SEARCH BY NAME ==========");

        String name = readNonEmptyString("Enter Name: ");

        boolean found = false;

        for (Student student : students) {

            if (student.getName().toLowerCase()
                    .contains(name.toLowerCase())) {

                student.displayDetails();
                found = true;
            }
        }

        if (!found) {
            System.out.println("No student found.");
        }
    }

    // ================= SEARCH BY COURSE =================

    public void searchByCourse() {

        System.out.println("\n========== SEARCH BY COURSE ==========");

        String course = readNonEmptyString("Enter Course: ");

        boolean found = false;

        for (Student student : students) {

            if (student.isEnrolledIn(course)) {

                student.displayDetails();
                found = true;
            }
        }

        if (!found) {
            System.out.println("No student found for this course.");
        }
    }

    // ================= UPDATE =================

    @Override
    public void updateStudent() {

        System.out.println("\n========== UPDATE STUDENT ==========");

        String id = readNonEmptyString("Enter Student ID: ");

        Student student = studentMap.get(id);

        if (student == null) {
            System.out.println("Student not found.");
            return;
        }

        System.out.println("\nCurrent Details:");
        student.displayDetails();

        String name = readNonEmptyString("Enter New Name: ");

        if (name.contains("|")) {
            System.out.println("Name cannot contain |");
            return;
        }

        int age = readInt("Enter New Age: ");

        if (age < 5 || age > 100) {
            System.out.println("Invalid age.");
            return;
        }

        student.setName(name);
        student.setAge(age);

        System.out.println("\nStudent updated successfully!");
        System.out.println("(Use 'Manage Courses & Grades' to change courses or grades.)");

        saveToFile();
    }

    // ================= DELETE =================

    @Override
    public void deleteStudent() {

        System.out.println("\n========== DELETE STUDENT ==========");

        String id = readNonEmptyString("Enter Student ID: ");

        Student student = studentMap.get(id);

        if (student == null) {
            System.out.println("Student not found.");
            return;
        }

        student.displayDetails();

        System.out.print("Are you sure you want to delete? (Y/N): ");

        String choice = sc.nextLine();

        if (choice.equalsIgnoreCase("Y")) {

            students.remove(student);
            studentMap.remove(id);

            rebuildCourses();

            System.out.println("Student deleted successfully!");

            saveToFile();

        } else {
            System.out.println("Delete operation cancelled.");
        }
    }

    // ================= STATISTICS =================

    public void displayStatistics() {

        System.out.println("\n========== STUDENT STATISTICS ==========");

        if (students.isEmpty()) {
            System.out.println("No student records available.");
            return;
        }

        ArrayList<Student> graded = getGradedStudents();

        if (graded.isEmpty()) {
            System.out.println("Total Students : " + students.size());
            System.out.println("No grades have been entered yet.");
            return;
        }

        double total = 0;
        double highest = graded.get(0).getMarks();
        double lowest = graded.get(0).getMarks();

        int passed = 0;
        int failed = 0;

        for (Student student : graded) {

            double marks = student.getMarks();

            total += marks;

            if (marks > highest) {
                highest = marks;
            }

            if (marks < lowest) {
                lowest = marks;
            }

            if (marks >= 40) {
                passed++;
            } else {
                failed++;
            }
        }

        double average = total / graded.size();

        System.out.println("Total Students : " + students.size());
        System.out.printf("Class Average  : %.2f%n", average);
        System.out.printf("Highest Average: %.2f%n", highest);
        System.out.printf("Lowest Average : %.2f%n", lowest);
        System.out.println("Passed Students: " + passed);
        System.out.println("Failed Students: " + failed);
        System.out.println("Unique Courses : " + courses.size());
    }

    // ================= TOP STUDENTS =================

    public void displayTopStudents() {

        System.out.println("\n========== TOP PERFORMERS ==========");

        if (students.isEmpty()) {
            System.out.println("No records available.");
            return;
        }

        ArrayList<Student> sorted = getGradedStudents();

        if (sorted.isEmpty()) {
            System.out.println("No grades have been entered yet.");
            return;
        }

        sorted.sort(
                Comparator.comparingDouble(Student::getMarks)
                        .reversed()
        );

        int count = Math.min(5, sorted.size());

        for (int i = 0; i < count; i++) {

            Student student = sorted.get(i);

            System.out.printf(
                    "%d. %s | %s | Average: %.2f | Grade: %s%n",
                    i + 1,
                    student.getName(),
                    student.getStudentId(),
                    student.getMarks(),
                    student.getGrade()
            );
        }
    }

    // ================= SORT =================

    public void sortStudents() {

        System.out.println("\n========== SORT STUDENTS ==========");

        if (students.isEmpty()) {
            System.out.println("No records available.");
            return;
        }

        System.out.println("1. Average - Highest to Lowest");
        System.out.println("2. Average - Lowest to Highest");
        System.out.println("3. Name - A to Z");
        System.out.println("4. Student ID");

        int choice = readInt("Enter choice: ");

        switch (choice) {

            case 1:
                students.sort(
                        Comparator.comparingDouble(Student::getMarks)
                                .reversed()
                );
                break;

            case 2:
                students.sort(
                        Comparator.comparingDouble(Student::getMarks)
                );
                break;

            case 3:
                students.sort(
                        Comparator.comparing(
                                Student::getName,
                                String.CASE_INSENSITIVE_ORDER
                        )
                );
                break;

            case 4:
                students.sort(
                        Comparator.comparing(Student::getStudentId)
                );
                break;

            default:
                System.out.println("Invalid choice.");
                return;
        }

        System.out.println("Students sorted successfully.");

        viewStudents();
    }

    // ================= COURSE STATISTICS =================

    public void courseStatistics() {

        System.out.println("\n========== COURSE STATISTICS ==========");

        if (students.isEmpty()) {
            System.out.println("No records available.");
            return;
        }

        HashMap<String, Integer> courseCount = new HashMap<>();
        HashMap<String, Double> courseTotal = new HashMap<>();
        HashMap<String, Integer> gradedCount = new HashMap<>();

        for (Student student : students) {

            for (Enrollment enrollment : student.getEnrollments()) {

                String course = enrollment.getCourseName();

                courseCount.put(
                        course,
                        courseCount.getOrDefault(course, 0) + 1
                );

                if (enrollment.hasGrades()) {
                    courseTotal.put(
                            course,
                            courseTotal.getOrDefault(course, 0.0)
                                    + enrollment.getAverage()
                    );
                    gradedCount.put(
                            course,
                            gradedCount.getOrDefault(course, 0) + 1
                    );
                }
            }
        }

        if (courseCount.isEmpty()) {
            System.out.println("No students are enrolled in any courses.");
            return;
        }

        for (Map.Entry<String, Integer> entry :
                courseCount.entrySet()) {

            String course = entry.getKey();
            int graded = gradedCount.getOrDefault(course, 0);

            String avg = graded == 0
                    ? "N/A"
                    : String.format("%.2f", courseTotal.get(course) / graded);

            System.out.printf(
                    "%-25s : %d students | Course Average: %s%n",
                    course,
                    entry.getValue(),
                    avg
            );
        }
    }

    // ================= MANAGE COURSES & GRADES =================

    public void manageCoursesAndGrades() {

        System.out.println("\n========== MANAGE COURSES & GRADES ==========");

        String id = readNonEmptyString("Enter Student ID: ");

        Student student = studentMap.get(id);

        if (student == null) {
            System.out.println("Student not found.");
            return;
        }

        while (true) {

            System.out.println("\nStudent: " + student.getName() + " (" + id + ")");
            System.out.println("1. View Report Card");
            System.out.println("2. Enroll in a Course");
            System.out.println("3. Add Grade(s) to a Course");
            System.out.println("4. Drop a Course");
            System.out.println("5. Back to Main Menu");

            int choice = readInt("Enter choice: ");

            switch (choice) {

                case 1:
                    student.displayDetails();
                    break;

                case 2: {
                    String course = readCourseName("Enter Course Name: ");

                    if (student.isEnrolledIn(course)) {
                        System.out.println("Already enrolled in " + course + ".");
                        break;
                    }

                    Enrollment enrollment = student.enroll(course);

                    for (double grade : readGrades("Enter grades for " + course)) {
                        enrollment.addGrade(grade);
                    }

                    rebuildCourses();
                    saveToFile();
                    System.out.println("Enrolled in " + course + ".");
                    break;
                }

                case 3: {
                    Enrollment enrollment = chooseEnrollment(student);

                    if (enrollment == null) {
                        break;
                    }

                    ArrayList<Double> grades =
                            readGrades("Enter new grades for " + enrollment.getCourseName());

                    for (double grade : grades) {
                        enrollment.addGrade(grade);
                    }

                    saveToFile();
                    System.out.println(grades.size() + " grade(s) added.");
                    enrollment.displayDetails();
                    System.out.printf(
                            "Overall Average: %.2f (%s)%n",
                            student.getMarks(),
                            student.getGrade()
                    );
                    break;
                }

                case 4: {
                    Enrollment enrollment = chooseEnrollment(student);

                    if (enrollment == null) {
                        break;
                    }

                    student.dropCourse(enrollment.getCourseName());
                    rebuildCourses();
                    saveToFile();
                    System.out.println("Dropped " + enrollment.getCourseName() + ".");
                    break;
                }

                case 5:
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private Enrollment chooseEnrollment(Student student) {

        List<Enrollment> enrollments = student.getEnrollments();

        if (enrollments.isEmpty()) {
            System.out.println("This student is not enrolled in any courses.");
            return null;
        }

        for (int i = 0; i < enrollments.size(); i++) {
            System.out.println((i + 1) + ". " + enrollments.get(i).getCourseName());
        }

        int choice = readInt("Select course number: ");

        if (choice < 1 || choice > enrollments.size()) {
            System.out.println("Invalid course number.");
            return null;
        }

        return enrollments.get(choice - 1);
    }

    private ArrayList<Student> getGradedStudents() {

        ArrayList<Student> graded = new ArrayList<>();

        for (Student student : students) {
            if (student.hasGrades()) {
                graded.add(student);
            }
        }

        return graded;
    }

    // ================= SAVE FILE =================

    public synchronized void saveToFile() {

        try {

            File directory = new File("data");

            if (!directory.exists()) {
                directory.mkdirs();
            }

            BufferedWriter writer =
                    new BufferedWriter(
                            new FileWriter(FILE_NAME)
                    );

            for (Student student : students) {
                writer.write(student.toString());
                writer.newLine();
            }

            writer.close();

        } catch (IOException e) {

            System.out.println(
                    "Error while saving data: "
                            + e.getMessage()
            );
        }
    }

    // ================= LOAD FILE =================

    private void loadFromFile() {

        File file = new File(FILE_NAME);

        if (!file.exists()) {
            return;
        }

        try {

            BufferedReader reader =
                    new BufferedReader(
                            new FileReader(file)
                    );

            String line;

            while ((line = reader.readLine()) != null) {

                String[] data = line.split("\\|", -1);

                Student student = null;

                if (data.length == 4) {

                    // Current format: ID|Name|Age|Course1:g1,g2;Course2:g1
                    student = new Student(
                            data[0],
                            data[1],
                            Integer.parseInt(data[2])
                    );

                    if (!data[3].isEmpty()) {
                        for (String part : data[3].split(";")) {
                            student.getEnrollments()
                                    .add(Enrollment.fromString(part));
                        }
                    }

                } else if (data.length == 5) {

                    // Old format: ID|Name|Course|Age|Marks
                    student = new Student(
                            data[0],
                            data[1],
                            Integer.parseInt(data[3])
                    );

                    student.enroll(data[2])
                            .addGrade(Double.parseDouble(data[4]));
                }

                if (student != null) {
                    students.add(student);
                    studentMap.put(student.getStudentId(), student);
                }
            }

            reader.close();

            rebuildCourses();

            System.out.println(
                    students.size()
                            + " student records loaded."
            );

        } catch (IOException | IllegalArgumentException e) {

            System.out.println(
                    "Error while loading data."
            );
        }
    }

    // ================= REBUILD COURSES =================

    private void rebuildCourses() {

        courses.clear();

        for (Student student : students) {
            for (Enrollment enrollment : student.getEnrollments()) {
                courses.add(enrollment.getCourseName());
            }
        }
    }

    // ================= INPUT METHODS =================

    private String readNonEmptyString(String message) {

        while (true) {

            System.out.print(message);

            String input = sc.nextLine().trim();

            if (!input.isEmpty()) {
                return input;
            }

            System.out.println(
                    "Input cannot be empty. Try again."
            );
        }
    }

    private int readInt(String message) {

        while (true) {

            try {

                System.out.print(message);

                return Integer.parseInt(
                        sc.nextLine().trim()
                );

            } catch (NumberFormatException e) {

                System.out.println(
                        "Please enter a valid number."
                );
            }
        }
    }

    // Course names can't contain the characters used by the save file format
    private String readCourseName(String message) {

        while (true) {

            String input = readNonEmptyString(message);

            if (input.matches(".*[|:;,].*")) {
                System.out.println("Course name cannot contain | : ; or ,");
                continue;
            }

            return input;
        }
    }

    private ArrayList<Double> readGrades(String message) {

        while (true) {

            System.out.print(message + " (comma-separated, blank for none): ");

            String input = sc.nextLine().trim();

            ArrayList<Double> grades = new ArrayList<>();

            if (input.isEmpty()) {
                return grades;
            }

            try {

                for (String part : input.split(",")) {

                    double grade = Double.parseDouble(part.trim());

                    if (grade < 0 || grade > 100) {
                        throw new NumberFormatException();
                    }

                    grades.add(grade);
                }

                return grades;

            } catch (NumberFormatException e) {

                System.out.println(
                        "Grades must be numbers between 0 and 100, e.g. 90, 85.5, 77"
                );
            }
        }
    }
}
