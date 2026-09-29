import service.StudentManager;
import thread.AutoSaveTask;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        StudentManager manager =
                new StudentManager(sc);

        // Start background auto-save thread
        AutoSaveTask autoSaveTask =
                new AutoSaveTask(manager);

        Thread autoSaveThread =
                new Thread(autoSaveTask);

        autoSaveThread.setDaemon(true);
        autoSaveThread.start();

        boolean running = true;

        System.out.println("\n==============================================");
        System.out.println("       STUDENT MANAGEMENT SYSTEM");
        System.out.println("==============================================");
        System.out.println("Auto-save is enabled every 60 seconds.");

        while (running) {

            printMenu();

            int choice;

            try {

                System.out.print("Enter your choice: ");

                choice =
                        Integer.parseInt(
                                sc.nextLine().trim()
                        );

            } catch (NumberFormatException e) {

                System.out.println(
                        "\nInvalid input! Please enter a number."
                );

                continue;
            }

            switch (choice) {

                case 1:
                    manager.addStudent();
                    break;

                case 2:
                    manager.viewStudents();
                    break;

                case 3:
                    manager.searchById();
                    break;

                case 4:
                    manager.searchByName();
                    break;

                case 5:
                    manager.searchByCourse();
                    break;

                case 6:
                    manager.updateStudent();
                    break;

                case 7:
                    manager.deleteStudent();
                    break;

                case 8:
                    manager.displayStatistics();
                    break;

                case 9:
                    manager.displayTopStudents();
                    break;

                case 10:
                    manager.sortStudents();
                    break;

                case 11:
                    manager.courseStatistics();
                    break;

                case 12:
                    manager.manageCoursesAndGrades();
                    break;

                case 13:
                    manager.saveToFile();
                    System.out.println(
                            "Data saved successfully."
                    );
                    break;

                case 14:

                    manager.saveToFile();

                    autoSaveTask.stopTask();

                    running = false;

                    System.out.println(
                            "\n=============================================="
                    );
                    System.out.println(
                            "Data saved successfully."
                    );
                    System.out.println(
                            "Thank you for using Student Management System!"
                    );
                    System.out.println(
                            "=============================================="
                    );

                    break;

                default:
                    System.out.println(
                            "Invalid choice! Please select 1-14."
                    );
            }
        }

        sc.close();
    }

    private static void printMenu() {

        System.out.println("\n");
        System.out.println("==============================================");
        System.out.println("          STUDENT MANAGEMENT SYSTEM");
        System.out.println("==============================================");
        System.out.println("1.  Add Student");
        System.out.println("2.  View All Students");
        System.out.println("3.  Search Student by ID");
        System.out.println("4.  Search Student by Name");
        System.out.println("5.  Search Students by Course");
        System.out.println("6.  Update Student");
        System.out.println("7.  Delete Student");
        System.out.println("8.  Display Statistics");
        System.out.println("9.  Display Top Performing Students");
        System.out.println("10. Sort Students");
        System.out.println("11. Course-wise Student Count");
        System.out.println("12. Manage Courses & Grades");
        System.out.println("13. Save Data");
        System.out.println("14. Exit");
        System.out.println("==============================================");
    }
}