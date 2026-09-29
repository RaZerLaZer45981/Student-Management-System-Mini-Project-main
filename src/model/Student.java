
package model;

import java.util.ArrayList;
import java.util.List;

public class Student extends Person {

    private String studentId;
    private final ArrayList<Enrollment> enrollments = new ArrayList<>();

    public Student(String studentId, String name, int age) {
        super(name, age);
        this.studentId = studentId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    // ================= ENROLLMENTS =================

    public List<Enrollment> getEnrollments() {
        return enrollments;
    }

    public Enrollment getEnrollment(String courseName) {
        for (Enrollment enrollment : enrollments) {
            if (enrollment.getCourseName().equalsIgnoreCase(courseName)) {
                return enrollment;
            }
        }
        return null;
    }

    public boolean isEnrolledIn(String courseName) {
        return getEnrollment(courseName) != null;
    }

    public Enrollment enroll(String courseName) {
        Enrollment existing = getEnrollment(courseName);

        if (existing != null) {
            return existing;
        }

        Enrollment enrollment = new Enrollment(courseName);
        enrollments.add(enrollment);
        return enrollment;
    }

    public boolean dropCourse(String courseName) {
        Enrollment enrollment = getEnrollment(courseName);
        return enrollment != null && enrollments.remove(enrollment);
    }

    // ================= GRADES =================

    public boolean hasGrades() {
        for (Enrollment enrollment : enrollments) {
            if (enrollment.hasGrades()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Overall average = average of each course's average.
     * Courses without any grades are not counted.
     */
    public double getMarks() {
        double total = 0;
        int graded = 0;

        for (Enrollment enrollment : enrollments) {
            if (enrollment.hasGrades()) {
                total += enrollment.getAverage();
                graded++;
            }
        }

        return graded == 0 ? 0 : total / graded;
    }

    public String getGrade() {
        return hasGrades() ? Enrollment.toLetterGrade(getMarks()) : "N/A";
    }

    public String getStatus() {
        if (!hasGrades()) {
            return "N/A";
        }
        return getMarks() >= 40 ? "PASS" : "FAIL";
    }

    @Override
    public void displayDetails() {
        System.out.println("-----------------------------------------------");
        System.out.println("Student ID : " + studentId);
        System.out.println("Name       : " + getName());
        System.out.println("Age        : " + getAge());
        System.out.println("Courses    : " + enrollments.size());

        if (enrollments.isEmpty()) {
            System.out.println("  (not enrolled in any courses)");
        }

        for (Enrollment enrollment : enrollments) {
            enrollment.displayDetails();
        }

        if (hasGrades()) {
            System.out.printf("Average    : %.2f%n", getMarks());
        } else {
            System.out.println("Average    : N/A");
        }

        System.out.println("Grade      : " + getGrade());
        System.out.println("Status     : " + getStatus());
        System.out.println("-----------------------------------------------");
    }

    // File format: ID|Name|Age|Course1:90.0,85.0;Course2:77.0
    @Override
    public String toString() {
        StringBuilder courses = new StringBuilder();

        for (int i = 0; i < enrollments.size(); i++) {
            if (i > 0) {
                courses.append(";");
            }
            courses.append(enrollments.get(i));
        }

        return studentId + "|" + getName() + "|" + getAge() + "|" + courses;
    }
}
