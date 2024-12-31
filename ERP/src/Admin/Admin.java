package Admin;

import Student.Student;
import Student.Student.StudentData;
import Professor.Professor;
import Professor.Professor.ProfessorData;
import Course.Course;
import Complaint.Complaint;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Main Admin Class
public class Admin {
    private String username;
    private String password;
    private List<Complaint> complaintList = new ArrayList<>();

    // List to store all admin instances
    private static List<Admin> admins = new ArrayList<>();

    // Static block to add pre-fed admin details
    static {
        // Adding default admins
        admins.add(new Admin("admin@university", "adminpass"));
        admins.add(new Admin("superadmin@university", "superpass"));
    }

    // Constructor to initialize an admin with specific credentials
    public Admin(String username, String password) {
        this.username = username;
        this.password = password;
        admins.add(this); // Add this admin to the list of admins
    }

    // Method to display Admin menu and functionalities
    public void menu(Scanner scanner) {
        while (true) {
            System.out.println("\n-- Admin Menu --");
            System.out.println("1. Manage Course Catalog");
            System.out.println("2. Manage Student Records");
            System.out.println("3. Assign Professors to Courses");
            System.out.println("4. Handle Complaints");
            System.out.println("5. Logout");
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
                    manageCourseCatalog(scanner);
                    break;
                case 2:
                    manageStudentRecords(scanner);
                    break;
                case 3:
                    assignProfessors(scanner);
                    break;
                case 4:
                    handleComplaints(scanner);
                    break;
                case 5:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    // Setter method for username
    public void setUsername(String username) {
        this.username = username;
    }

    // Setter method for password
    public void setPassword(String password) {
        this.password = password;
    }

    // Method to validate admin login credentials
    public static Admin validateAdminLogin(String username, String password) {
        for (Admin admin : admins) {
            if (admin.getUsername().equals(username) && admin.getPassword().equals(password)) {
                return admin;
            }
        }
        return null;
    }

    // Getter for username
    public String getUsername() {
        return username;
    }

    // Getter for password
    public String getPassword() {
        return password;
    }

    // 1. Manage Course Catalog
    private void manageCourseCatalog(Scanner scanner) {
        while (true) {
            System.out.println("\n-- Manage Course Catalog --");
            System.out.println("1. View Courses");
            System.out.println("2. Add Course");
            System.out.println("3. Remove Course");
            System.out.println("4. Back to Admin Menu");
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
                    viewCourses();
                    break;
                case 2:
                    addCourse(scanner);
                    break;
                case 3:
                    removeCourse(scanner);
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private void viewCourses() {
        System.out.println("\nAvailable Courses:");
        for (Course course : Course.getAvailableCourses()) {
            System.out.println(course.getCourseCode() + ": " + course.getTitle() + " by " + course.getProfessor());
        }
    }

    private void addCourse(Scanner scanner) {
        System.out.print("Enter Course Code: ");
        String code = scanner.nextLine();
        System.out.print("Enter Course Title: ");
        String title = scanner.nextLine();
        System.out.print("Enter Professor Name: ");
        String professor = scanner.nextLine();
        System.out.print("Enter Credits: ");
        int credits;
        if (scanner.hasNextInt()) {
            credits = scanner.nextInt();
            scanner.nextLine(); // Consume newline
        } else {
            System.out.println("Invalid input for credits. Operation aborted.");
            scanner.nextLine(); // Consume invalid input
            return;
        }
        System.out.print("Enter Prerequisites: ");
        String prerequisites = scanner.nextLine();
        System.out.print("Enter Timing: ");
        String timing = scanner.nextLine();

        Course newCourse = new Course(code, title, professor, credits, prerequisites, timing);
        Course.addCourse(newCourse);
        System.out.println("Course added successfully.");
    }

    private void removeCourse(Scanner scanner) {
        viewCourses();
        System.out.print("Enter Course Code to Remove: ");
        String code = scanner.nextLine();

        Course courseToRemove = null;
        for (Course course : Course.getAvailableCourses()) {
            if (course.getCourseCode().equals(code)) {
                courseToRemove = course;
                break;
            }
        }

        if (courseToRemove != null) {
            Course.getAvailableCourses().remove(courseToRemove);
            System.out.println("Course removed successfully.");
        } else {
            System.out.println("Course not found.");
        }
    }

    // 2. Manage Student Records
    private void manageStudentRecords(Scanner scanner) {
        while (true) {
            System.out.println("\n-- Manage Student Records --");
            System.out.println("1. View All Students");
            System.out.println("2. Update Student Grades");
            System.out.println("3. Update Student Personal Information");
            System.out.println("4. Back to Admin Menu");
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
                    viewAllStudents();
                    break;
                case 2:
                    updateStudentGrades(scanner);
                    break;
                case 3:
                    updateStudentInformation(scanner);
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private void viewAllStudents() {
        System.out.println("\n-- Student Records --");
        for (Student student : StudentData.getAllStudents()) {
            System.out.println("Name: " + student.getName() + ", Email: " + student.getEmail() + ", Semester: " + student.getSemester());
        }
    }

    private void updateStudentGrades(Scanner scanner) {
        viewAllStudents();
        System.out.print("Enter Student Email to Update Grades: ");
        String email = scanner.nextLine();
        Student student = StudentData.getStudent(email);

        if (student != null) {
            System.out.print("Enter new grades (format: CourseCode: Grade): ");
            String grade = scanner.nextLine();
            student.getGrades().add(grade);
            System.out.println("Grades updated successfully.");
        } else {
            System.out.println("Student not found.");
        }
    }

    private void updateStudentInformation(Scanner scanner) {
        viewAllStudents();
        System.out.print("Enter Student Email to Update Information: ");
        String email = scanner.nextLine();
        Student student = StudentData.getStudent(email);

        if (student != null) {
            System.out.print("Enter new name: ");
            String newName = scanner.nextLine();
            student.setName(newName);
            System.out.print("Enter new semester: ");
            if (scanner.hasNextInt()) {
                int newSemester = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                student.setSemester(newSemester);
                System.out.println("Student information updated successfully.");
            } else {
                System.out.println("Invalid input for semester. Operation aborted.");
                scanner.nextLine(); // Consume invalid input
            }
        } else {
            System.out.println("Student not found.");
        }
    }

    // 3. Assign Professors to Courses
    private void assignProfessors(Scanner scanner) {
        viewCourses();
        System.out.print("Enter Course Code: ");
        String courseCode = scanner.nextLine();

        System.out.println("Available Professors:");
        for (Professor professor : ProfessorData.getAllProfessors()) {
            System.out.println(professor.getName() + " - " + professor.getEmail());
        }

        System.out.print("Enter Professor Email to Assign: ");
        String professorEmail = scanner.nextLine();

        Course courseToAssign = null;
        for (Course course : Course.getAvailableCourses()) {
            if (course.getCourseCode().equals(courseCode)) {
                courseToAssign = course;
                break;
            }
        }

        Professor professorToAssign = ProfessorData.getProfessor(professorEmail);

        if (courseToAssign != null && professorToAssign != null) {
            courseToAssign.setProfessor(professorToAssign.getName());
            professorToAssign.getCoursesManaged().add(courseToAssign);
            System.out.println("Assigned " + professorToAssign.getName() + " to course " + courseToAssign.getTitle());
        } else {
            System.out.println("Course or Professor not found.");
        }
    }

    // 4. Handle Complaints
    private void handleComplaints(Scanner scanner) {
        while (true) {
            System.out.println("\n-- Handle Complaints --");
            System.out.println("1. View All Complaints");
            System.out.println("2. Resolve a Complaint");
            System.out.println("3. Back to Admin Menu");
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
                    viewComplaints();
                    break;
                case 2:
                    resolveComplaint(scanner);
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private void viewComplaints() {
        System.out.println("\n-- Complaints List --");
        if (complaintList.isEmpty()) {
            System.out.println("No complaints available.");
            return;
        }

        for (int i = 0; i < complaintList.size(); i++) {
            Complaint complaint = complaintList.get(i);
            System.out.println((i + 1) + ". " + complaint.getDescription() + " - Status: " + complaint.getStatus());
        }
    }

    private void resolveComplaint(Scanner scanner) {
        viewComplaints();
        if (complaintList.isEmpty()) {
            return;
        }
        System.out.print("Enter Complaint Number to Resolve: ");
        if (scanner.hasNextInt()) {
            int complaintNumber = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (complaintNumber > 0 && complaintNumber <= complaintList.size()) {
                Complaint complaint = complaintList.get(complaintNumber - 1);
                complaint.setStatus("Resolved");
                System.out.println("Complaint resolved successfully.");
            } else {
                System.out.println("Invalid complaint number.");
            }
        } else {
            System.out.println("Invalid input. Please enter a number.");
            scanner.nextLine(); // Consume invalid input
        }
    }
}


