package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a single course a student is enrolled in,
 * along with every grade the student has earned in that course.
 */
public class Enrollment {

    private final String courseName;
    private final ArrayList<Double> grades = new ArrayList<>();

    public Enrollment(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseName() {
        return courseName;
    }

    public List<Double> getGrades() {
        return grades;
    }

    public void addGrade(double grade) {
        if (grade < 0 || grade > 100) {
            throw new IllegalArgumentException("Grade must be between 0 and 100.");
        }
        grades.add(grade);
    }

    public boolean hasGrades() {
        return !grades.isEmpty();
    }

    public double getAverage() {
        if (grades.isEmpty()) {
            return 0;
        }

        double total = 0;

        for (double grade : grades) {
            total += grade;
        }

        return total / grades.size();
    }

    public String getLetterGrade() {
        return hasGrades() ? toLetterGrade(getAverage()) : "N/A";
    }

    // Shared grading scale used by both Enrollment and Student
    public static String toLetterGrade(double marks) {
        if (marks >= 95) {
            return "A+";
        } else if (marks >= 90) {
            return "A";
        } else if (marks >= 80) {
            return "B";
        } else if (marks >= 70) {
            return "C";
        } else if (marks >= 65) {
            return "D";
        } else {
            return "F";
        }
    }

    public void displayDetails() {

        StringBuilder gradeList = new StringBuilder();

        for (int i = 0; i < grades.size(); i++) {
            if (i > 0) {
                gradeList.append(", ");
            }
            gradeList.append(grades.get(i));
        }

        if (hasGrades()) {
            System.out.printf(
                    "  - %-25s Avg: %6.2f (%s)  Grades: %s%n",
                    courseName,
                    getAverage(),
                    getLetterGrade(),
                    gradeList
            );
        } else {
            System.out.printf(
                    "  - %-25s No grades yet%n",
                    courseName
            );
        }
    }

    // File format: CourseName:90.0,85.5,77.0
    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder(courseName).append(":");

        for (int i = 0; i < grades.size(); i++) {
            if (i > 0) {
                sb.append(",");
            }
            sb.append(grades.get(i));
        }

        return sb.toString();
    }

    public static Enrollment fromString(String text) {

        int colon = text.lastIndexOf(':');

        Enrollment enrollment =
                new Enrollment(colon == -1 ? text : text.substring(0, colon));

        if (colon != -1 && colon < text.length() - 1) {
            for (String grade : text.substring(colon + 1).split(",")) {
                enrollment.addGrade(Double.parseDouble(grade.trim()));
            }
        }

        return enrollment;
    }
}
