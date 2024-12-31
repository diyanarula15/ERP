import Student.Student;
import Student.Student.StudentData;
import Professor.Professor;
import Professor.Professor.ProfessorData;
import Admin.Admin;

import java.util.Scanner;

public class Main {

    // Single Scanner instance for the entire application
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        while (true) {
            System.out.println("\nWelcome to the University Course Registration System");
            System.out.println("1. Enter the Application");
            System.out.println("2. Exit the Application");
            System.out.print("Choose an option: ");

            int mainChoice;
            if (scanner.hasNextInt()) {
                mainChoice = scanner.nextInt();
                scanner.nextLine(); // Consume newline
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // Consume invalid input
                continue;
            }

            if (mainChoice == 1) {
                startApplication();
            } else if (mainChoice == 2) {
                System.out.println("Exiting the system...");
                break; // Exit the main loop
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }

        scanner.close(); // Close the scanner before exiting
    }

    private static void startApplication() {

        while (true) {
            System.out.println("\nLogin as:");
            System.out.println("1. Student");
            System.out.println("2. Professor");
            System.out.println("3. Administrator");
            System.out.println("4. Back to Main Menu");
            System.out.print("Choose an option: ");

            int choice;
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // Consume invalid input
                continue;
            }

            switch (choice) {
                case 1:
                    handleStudentLoginOrSignup();
                    break;
                case 2:
                    handleProfessorLoginOrSignup();
                    break;
                case 3:
                    loginAsAdmin();
                    break;
                case 4:
                    System.out.println("Returning to main menu...");
                    return; // Exit the startApplication method
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void handleStudentLoginOrSignup() {
        while (true) {
            System.out.println("\n1. Login as Student");
            System.out.println("2. Signup as Student");
            System.out.println("3. Back to Previous Menu");
            System.out.print("Choose an option: ");

            int choice;
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // Consume invalid input
                continue;
            }

            if (choice == 1) {
                loginAsStudent();
                break; // After login, exit to allow further navigation
            } else if (choice == 2) {
                signupAsStudent();
                break;
            } else if (choice == 3) {
                return; // Go back to the previous menu
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void handleProfessorLoginOrSignup() {
        while (true) {
            System.out.println("\n1. Login as Professor");
            System.out.println("2. Signup as Professor");
            System.out.println("3. Back to Previous Menu");
            System.out.print("Choose an option: ");

            int choice;
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // Consume invalid input
                continue;
            }

            if (choice == 1) {
                loginAsProfessor();
                break;
            } else if (choice == 2) {
                signupAsProfessor();
                break;
            } else if (choice == 3) {
                return;
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void loginAsStudent() {
        System.out.print("Enter your email: ");
        String email = scanner.nextLine();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        Student student = StudentData.getStudent(email);

        if (student != null && student.getPassword().equals(password)) {
            student.menu(scanner); // Pass the scanner to the menu
        } else {
            System.out.println("Invalid credentials or no account found with this email. Please try again.");
        }
    }

    private static void signupAsStudent() {
        System.out.print("Enter your email: ");
        String email = scanner.nextLine();
        System.out.print("Create your password: ");
        String password = scanner.nextLine();

        if (StudentData.getStudent(email) == null) {
            Student student = new Student();
            student.setEmail(email);
            student.setPassword(password);
            student.setSemester(1); // Default semester
            StudentData.addStudent(student);
            System.out.println("Student account created successfully. You can now log in.");
        } else {
            System.out.println("An account with this email already exists. Please log in.");
        }
    }

    private static void loginAsProfessor() {
        System.out.print("Enter your email: ");
        String email = scanner.nextLine();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        Professor professor = ProfessorData.getProfessor(email);

        if (professor != null && professor.getPassword().equals(password)) {
            professor.menu(scanner); // Pass the scanner to the menu
        } else {
            System.out.println("Invalid credentials or no account found with this email. Please try again.");
        }
    }

    private static void signupAsProfessor() {
        System.out.print("Enter your email: ");
        String email = scanner.nextLine();
        System.out.print("Create your password: ");
        String password = scanner.nextLine();

        if (ProfessorData.getProfessor(email) == null) {
            Professor professor = new Professor();
            professor.setEmail(email);
            professor.setPassword(password);
            ProfessorData.addProfessor(professor);
            System.out.println("Professor account created successfully. You can now log in.");
        } else {
            System.out.println("An account with this email already exists. Please log in.");
        }
    }

    private static void loginAsAdmin() {
        System.out.print("Enter your username: ");
        String username = scanner.nextLine();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        Admin loggedInAdmin = Admin.validateAdminLogin(username, password);

        if (loggedInAdmin != null) {
            loggedInAdmin.menu(scanner); // Pass the scanner to the menu
        } else {
            System.out.println("Invalid credentials. Please try again.");
        }
    }
}
