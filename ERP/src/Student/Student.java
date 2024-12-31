package Student;

import Complaint.*;
import Course.Course;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

// Main Student Class
public class Student {
    private String name;
    private String email;
    private String password;
    private int semester;
    private List<Course> registeredCourses;
    private List<Complaint> complaints;
    private List<String> grades;
    private double totalCredits;

    // Static block to pre-feed student data
    static {
        // Pre-feed some students
        Student student1 = new Student("Student One", "student1@university", "password1", 1);
        StudentData.addStudent(student1);

        Student student2 = new Student("Student Two", "student2@university", "password2", 1);
        StudentData.addStudent(student2);
    }

    // Constructor with parameters
    public Student(String name, String email, String password, int semester) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.semester = semester;
        this.registeredCourses = new ArrayList<>();
        this.complaints = new ArrayList<>();
        this.grades = new ArrayList<>();
        this.totalCredits = 0;
    }

    // Default constructor (if needed)
    public Student() {
        this.registeredCourses = new ArrayList<>();
        this.complaints = new ArrayList<>();
        this.grades = new ArrayList<>();
        this.totalCredits = 0;
    }

    public void menu(Scanner scanner) {
        while (true) {
            System.out.println("\n-- Student Menu --");
            System.out.println("1. View Available Courses");
            System.out.println("2. Register for Courses");
            System.out.println("3. View Schedule");
            System.out.println("4. Track Academic Progress");
            System.out.println("5. Drop Courses");
            System.out.println("6. Submit Complaints");
            System.out.println("7. Logout");
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
                    viewAvailableCourses();
                    break;
                case 2:
                    registerCourse(scanner);
                    break;
                case 3:
                    viewSchedule();
                    break;
                case 4:
                    trackProgress();
                    break;
                case 5:
                    dropCourse(scanner);
                    break;
                case 6:
                    submitComplaint(scanner);
                    break;
                case 7:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    // 1. View Available Courses
    private void viewAvailableCourses() {
        Course.viewCourses();
    }

    // 2. Register for Courses
    private void registerCourse(Scanner scanner) {
        System.out.println("\n-- Register for Courses --");
        System.out.print("Enter your current semester: ");
        int currentSemester;
        if (scanner.hasNextInt()) {
            currentSemester = scanner.nextInt();
            scanner.nextLine(); // Consume newline
        } else {
            System.out.println("Invalid input for semester. Operation aborted.");
            scanner.nextLine(); // Consume invalid input
            return;
        }

        List<Course> availableCourses = Course.getAvailableCourses();
        System.out.println("Available courses for Semester " + currentSemester + ":");
        boolean found = false;
        for (Course course : availableCourses) {
            if (currentSemester == semester && (course.getPrerequisites().equalsIgnoreCase("None") || hasCompletedPrerequisite(course.getPrerequisites()))) {
                System.out.println(course.getCourseCode() + ": " + course.getTitle() + " by " + course.getProfessor());
                found = true;
            }
        }

        if (!found) {
            System.out.println("No available courses found for your semester or prerequisites not met.");
            return;
        }

        System.out.print("Enter course code to register: ");
        String courseCode = scanner.nextLine();

        Course selectedCourse = findCourse(courseCode);

        if (selectedCourse != null && (selectedCourse.getPrerequisites().equalsIgnoreCase("None") || hasCompletedPrerequisite(selectedCourse.getPrerequisites()))) {
            if (totalCredits + selectedCourse.getCredits() <= 20) {
                if (!registeredCourses.contains(selectedCourse)) {
                    registeredCourses.add(selectedCourse);
                    totalCredits += selectedCourse.getCredits();
                    System.out.println("Registered for course: " + selectedCourse.getTitle());
                } else {
                    System.out.println("You are already registered for this course.");
                }
            } else {
                System.out.println("Credit limit exceeded. You can only register for up to 20 credits.");
            }
        } else {
            System.out.println("Course not found or prerequisites not met.");
        }
    }

    private boolean hasCompletedPrerequisite(String prerequisite) {
        for (String grade : grades) {
            if (grade.contains(prerequisite)) {
                return true;
            }
        }
        return false;
    }

    private Course findCourse(String courseCode) {
        for (Course course : Course.getAvailableCourses()) {
            if (course.getCourseCode().equalsIgnoreCase(courseCode)) {
                return course;
            }
        }
        return null;
    }

    // 3. View Schedule
    public void viewSchedule() {
        System.out.println("\nYour Schedule:");
        if (registeredCourses.isEmpty()) {
            System.out.println("No courses registered.");
            return;
        }
        for (Course course : registeredCourses) {
            System.out.println(course.getCourseCode() + " - " + course.getTitle() + " by " + course.getProfessor() + " at " + course.getTiming());
        }
    }

    // 4. Track Academic Progress
    public void trackProgress() {
        if (grades.isEmpty()) {
            System.out.println("No grades available.");
            return;
        }
        double sgpa = calculateSGPA();
        double cgpa = calculateCGPA();
        System.out.println("\n-- Academic Progress --");
        System.out.println("Grades: " + grades);
        System.out.println("SGPA: " + sgpa);
        System.out.println("CGPA: " + cgpa);
    }

    private double calculateSGPA() {
        double totalPoints = 0;
        int totalCreditsLocal = 0;

        for (String grade : grades) {
            String[] parts = grade.split(": ");
            if (parts.length == 2) {
                String courseCode = parts[0];
                String gradeValue = parts[1];

                Course course = findCourse(courseCode);
                if (course != null) {
                    totalCreditsLocal += course.getCredits();
                    totalPoints += convertGradeToPoint(gradeValue) * course.getCredits();
                }
            }
        }

        return totalCreditsLocal > 0 ? totalPoints / totalCreditsLocal : 0;
    }

    private double calculateCGPA() {
        return calculateSGPA(); // Placeholder for actual CGPA calculation
    }

    private double convertGradeToPoint(String grade) {
        switch (grade.toUpperCase()) {
            case "A":
                return 4.0;
            case "B":
                return 3.0;
            case "C":
                return 2.0;
            case "D":
                return 1.0;
            case "F":
                return 0.0;
            default:
                return 0.0;
        }
    }

    // 5. Drop Courses
    public void dropCourse(Scanner scanner) {
        if (registeredCourses.isEmpty()) {
            System.out.println("No courses to drop.");
            return;
        }
        System.out.println("\n-- Drop a Course --");
        for (int i = 0; i < registeredCourses.size(); i++) {
            System.out.println((i + 1) + ". " + registeredCourses.get(i).getCourseCode() + " - " + registeredCourses.get(i).getTitle());
        }
        System.out.print("Enter the course number to drop: ");
        int courseNumber;
        if (scanner.hasNextInt()) {
            courseNumber = scanner.nextInt();
            scanner.nextLine(); // Consume newline
        } else {
            System.out.println("Invalid input. Please enter a number.");
            scanner.nextLine(); // Consume invalid input
            return;
        }

        if (courseNumber > 0 && courseNumber <= registeredCourses.size()) {
            Course droppedCourse = registeredCourses.remove(courseNumber - 1);
            totalCredits -= droppedCourse.getCredits();
            System.out.println("Course dropped successfully.");
        } else {
            System.out.println("Invalid course number.");
        }
    }

    // 6. Submit Complaints
    public void submitComplaint(Scanner scanner) {
        System.out.print("Enter your complaint description: ");
        String description = scanner.nextLine();
        Complaint complaint = new Complaint(description);
        complaints.add(complaint);
        System.out.println("Complaint submitted.");
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public int getSemester() {
        return semester;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public List<Course> getRegisteredCourses() {
        return registeredCourses;
    }

    public List<String> getGrades() {
        return grades;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Password handling
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Nested StudentData Class for managing student records
    public static class StudentData {
        private static Map<String, Student> studentRecords = new HashMap<>();

        public static void addStudent(Student student) {
            studentRecords.put(student.getEmail(), student);
        }

        public static Student getStudent(String email) {
            return studentRecords.get(email);
        }

        public static List<Student> getAllStudents() {
            return new ArrayList<>(studentRecords.values());
        }

        public static List<Student> getEnrolledStudents(Course course) {
            List<Student> enrolledStudents = new ArrayList<>();
            for (Student student : studentRecords.values()) {
                if (student.getRegisteredCourses().contains(course)) {
                    enrolledStudents.add(student);
                }
            }
            return enrolledStudents;
        }
    }
}


