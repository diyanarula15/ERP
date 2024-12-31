package Professor;

import Course.Course;
import Student.Student;
import Student.Student.StudentData;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;

public class Professor {
    private String name;
    private String email;
    private String password;
    private List<Course> coursesManaged;

    // Professor data management class
    public static class ProfessorData {
        private static Map<String, Professor> professorRecords = new HashMap<>();

        // Method to add a professor
        public static void addProfessor(Professor professor) {
            professorRecords.put(professor.getEmail(), professor);
        }

        // Method to get a professor by email
        public static Professor getProfessor(String email) {
            return professorRecords.get(email);
        }

        // Method to get all professors
        public static List<Professor> getAllProfessors() {
            return new ArrayList<>(professorRecords.values());
        }
    }

    // Static block to pre-feed professor data
    static {
        // Adding default professors
        Professor prof1 = new Professor("Professor John", "prof1@university", "prof123");
        ProfessorData.addProfessor(prof1);

        Professor prof2 = new Professor("Professor Smith", "prof2@university", "prof456");
        ProfessorData.addProfessor(prof2);
    }

    // Constructor with parameters
    public Professor(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.coursesManaged = new ArrayList<>();
    }

    // Default constructor (if needed)
    public Professor() {
        this.coursesManaged = new ArrayList<>();
    }

    // Setter methods
    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Getter methods
    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public List<Course> getCoursesManaged() {
        return coursesManaged;
    }

    // Menu method for professor actions
    public void menu(Scanner scanner) {
        while (true) {
            System.out.println("\n-- Professor Menu --");
            System.out.println("1. Manage Courses");
            System.out.println("2. View Enrolled Students");
            System.out.println("3. Logout");
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
                    manageCourses(scanner);
                    break;
                case 2:
                    viewEnrolledStudents(scanner);
                    break;
                case 3:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    // Method to manage courses
    private void manageCourses(Scanner scanner) {
        if (coursesManaged.isEmpty()) {
            System.out.println("No courses assigned.");
            return;
        }
        System.out.println("\n-- Manage Courses --");
        for (int i = 0; i < coursesManaged.size(); i++) {
            Course course = coursesManaged.get(i);
            System.out.println((i + 1) + ". " + course.getCourseCode() + " - " + course.getTitle());
        }

        System.out.print("Select a course number to manage: ");
        int courseNumber;
        if (scanner.hasNextInt()) {
            courseNumber = scanner.nextInt();
            scanner.nextLine(); // Consume newline
        } else {
            System.out.println("Invalid input. Please enter a number.");
            scanner.nextLine(); // Consume invalid input
            return;
        }

        if (courseNumber > 0 && courseNumber <= coursesManaged.size()) {
            Course selectedCourse = coursesManaged.get(courseNumber - 1);
            updateCourseDetails(selectedCourse, scanner);
        } else {
            System.out.println("Invalid course number.");
        }
    }

    // Method to update course details
    private void updateCourseDetails(Course course, Scanner scanner) {
        while (true) {
            System.out.println("\nManaging Course: " + course.getCourseCode() + " - " + course.getTitle());
            System.out.println("1. Update Syllabus");
            System.out.println("2. Update Class Timings");
            System.out.println("3. Update Credits");
            System.out.println("4. Update Prerequisites");
            System.out.println("5. Update Enrollment Limit");
            System.out.println("6. Update Office Hours");
            System.out.println("7. Back to Course Management");
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
                    System.out.print("Enter new syllabus: ");
                    String syllabus = scanner.nextLine();
                    course.setSyllabus(syllabus);
                    System.out.println("Syllabus updated.");
                    break;
                case 2:
                    System.out.print("Enter new class timings: ");
                    String timings = scanner.nextLine();
                    course.setTiming(timings);
                    System.out.println("Class timings updated.");
                    break;
                case 3:
                    System.out.print("Enter new credits: ");
                    int credits;
                    if (scanner.hasNextInt()) {
                        credits = scanner.nextInt();
                        scanner.nextLine(); // Consume newline
                        course.setCredits(credits);
                        System.out.println("Credits updated.");
                    } else {
                        System.out.println("Invalid input for credits. Operation aborted.");
                        scanner.nextLine(); // Consume invalid input
                    }
                    break;
                case 4:
                    System.out.print("Enter new prerequisites: ");
                    String prerequisites = scanner.nextLine();
                    course.setPrerequisites(prerequisites);
                    System.out.println("Prerequisites updated.");
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    // Method to view students enrolled in courses
    private void viewEnrolledStudents(Scanner scanner) {
        if (coursesManaged.isEmpty()) {
            System.out.println("No courses assigned.");
            return;
        }
        System.out.println("\n-- Enrolled Students --");
        for (Course course : coursesManaged) {
            System.out.println("Course: " + course.getCourseCode() + " - " + course.getTitle());
            List<Student> students = StudentData.getEnrolledStudents(course);
            if (students.isEmpty()) {
                System.out.println("No students enrolled.");
            } else {
                for (Student student : students) {
                    System.out.println("Student: " + student.getName() + ", Email: " + student.getEmail());
                }
            }
        }
    }
}
