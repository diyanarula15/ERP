package Course;

import java.util.ArrayList;
import java.util.List;

public class Course {
    private String courseCode;
    private String title;
    private String professor;
    private int credits;
    private String prerequisites;
    private String timing;
    private String syllabus; // Added syllabus field
    private int enrollmentLimit; // Added enrollmentLimit
    private String officeHours; // Added officeHours

    public static List<Course> availableCourses = new ArrayList<>();

    // Static block to pre-feed courses
    static {
        // Adding some default courses
        availableCourses.add(new Course("CS101", "Introduction to Computer Science", "Professor 1", 4, "None", "Mon, Wed 10-11am"));
        availableCourses.add(new Course("CS102", "Data Structures and Algorithms", "Professor 2", 4, "CS101", "Tue, Thu 11-12 noon"));
        availableCourses.add(new Course("MA101", "Calculus I", "Professor 3", 3, "None", "Mon, Wed 9-10am"));
        availableCourses.add(new Course("PH101", "Physics I", "Professor 4", 4, "None", "Tue, Thu 1-2pm"));
    }

    // Constructor to initialize all fields except syllabus, enrollmentLimit, and officeHours
    public Course(String courseCode, String title, String professor, int credits, String prerequisites, String timing) {
        this.courseCode = courseCode;
        this.title = title;
        this.professor = professor;
        this.credits = credits;
        this.prerequisites = prerequisites;
        this.timing = timing;
        this.syllabus = "Not set"; // Initialize syllabus
        this.enrollmentLimit = 30; // Default enrollment limit
        this.officeHours = "Not set"; // Initialize office hours
    }

    // Getter and Setter for syllabus
    public String getSyllabus() {
        return syllabus;
    }

    public void setSyllabus(String syllabus) {
        this.syllabus = syllabus;
    }

    // Getter and Setter for enrollmentLimit
    public int getEnrollmentLimit() {
        return enrollmentLimit;
    }

    public void setEnrollmentLimit(int enrollmentLimit) {
        this.enrollmentLimit = enrollmentLimit;
    }

    // Getter and Setter for officeHours
    public String getOfficeHours() {
        return officeHours;
    }

    public void setOfficeHours(String officeHours) {
        this.officeHours = officeHours;
    }

    // Existing Getters and Setters
    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getProfessor() {
        return professor;
    }

    public void setProfessor(String professor) {
        this.professor = professor;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public String getTiming() {
        return timing;
    }

    public void setTiming(String timing) {
        this.timing = timing;
    }

    public String getPrerequisites() {
        return prerequisites;
    }

    public void setPrerequisites(String prerequisites) {
        this.prerequisites = prerequisites;
    }

    // Method to add a course to the availableCourses list
    public static void addCourse(Course course) {
        availableCourses.add(course);
    }

    // Method to view all available courses
    public static void viewCourses() {
        System.out.println("\n-- Available Courses --");
        if (availableCourses.isEmpty()) {
            System.out.println("No courses available.");
            return;
        }
        for (Course course : availableCourses) {
            System.out.println(course.courseCode + ": " + course.title + " by " + course.professor +
                    ", Credits: " + course.credits + ", Prerequisites: " + course.prerequisites +
                    ", Timing: " + course.timing + ", Syllabus: " + course.syllabus +
                    ", Enrollment Limit: " + course.enrollmentLimit +
                    ", Office Hours: " + course.officeHours);
        }
    }

    // Method to get the list of available courses
    public static List<Course> getAvailableCourses() {
        return availableCourses;
    }

    // Override toString for better readability
    @Override
    public String toString() {
        return courseCode + ": " + title + " | Professor: " + professor +
                " | Credits: " + credits + " | Prerequisites: " + prerequisites +
                " | Timing: " + timing + " | Syllabus: " + syllabus +
                " | Enrollment Limit: " + enrollmentLimit +
                " | Office Hours: " + officeHours;
    }
}
