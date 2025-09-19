import java.io.*;
import java.util.Scanner;

public class Main {
    static final int MAX_COURSES = 100;
    static final int MAX_USERS = 50;
    static final String USER_FILE = "users.txt";
    static final String COURSE_FILE = "courses.txt";
    
    static String[] courseIds = new String[MAX_COURSES];
    static String[] titles = new String[MAX_COURSES];
    static int[] creditHours = new int[MAX_COURSES];
    static int courseCount = 0;
    
    static String[] usernames = new String[MAX_USERS];
    static String[] passwords = new String[MAX_USERS];
    static int userCount = 0;
    
    static Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        loadUsers();
        loadCourses();
        
        while (true) {
            System.out.println("=== COURSE SYSTEM ===");
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("3. Exit");
            System.out.print("Choose: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine();
            
            if (choice == 1) {
                if (login()) {
                    showMainMenu();
                } else {
                    System.out.println("Login failed!");
                }
            } else if (choice == 2) {
                if (registerUser()) {
                    System.out.println("Registration success!");
                } else {
                    System.out.println("Username taken!");
                }
            } else if (choice == 3) {
                System.out.println("Goodbye!");
                break;
            } else {
                System.out.println("Invalid choice!");
            }
        }
    }
    
    static void loadUsers() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(USER_FILE));
            String line;
            while ((line = reader.readLine()) != null && userCount < MAX_USERS) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    usernames[userCount] = parts[0];
                    passwords[userCount] = parts[1];
                    userCount++;
                }
            }
            reader.close();
        } catch (IOException e) {
            // File doesn't exist yet
        }
    }
    
    static void loadCourses() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(COURSE_FILE));
            String line;
            while ((line = reader.readLine()) != null && courseCount < MAX_COURSES) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    courseIds[courseCount] = parts[0];
                    titles[courseCount] = parts[1];
                    creditHours[courseCount] = Integer.parseInt(parts[2]);
                    courseCount++;
                }
            }
            reader.close();
        } catch (IOException e) {
            // File doesn't exist yet
        }
    }
    
    static void saveCourses() {
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(COURSE_FILE));
            for (int i = 0; i < courseCount; i++) {
                writer.println(courseIds[i] + "," + titles[i] + "," + creditHours[i]);
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Error saving courses");
        }
    }
    
    static boolean registerUser() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        
        for (int i = 0; i < userCount; i++) {
            if (usernames[i].equals(username)) {
                return false;
            }
        }
        
        if (userCount < MAX_USERS) {
            usernames[userCount] = username;
            passwords[userCount] = password;
            userCount++;
            
            try {
                PrintWriter writer = new PrintWriter(new FileWriter(USER_FILE, true));
                writer.println(username + "," + password);
                writer.close();
            } catch (IOException e) {
                System.out.println("Error saving user");
            }
            return true;
        }
        
        return false;
    }
    
    static boolean login() {
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        
        for (int i = 0; i < userCount; i++) {
            if (usernames[i].equals(username) && passwords[i].equals(password)) {
                return true;
            }
        }
        return false;
    }
    
    static int findCourseById(String courseId) {
        for (int i = 0; i < courseCount; i++) {
            if (courseIds[i].equals(courseId)) {
                return i;
            }
        }
        return -1;
    }
    
    static void addCourse() {
        if (courseCount >= MAX_COURSES) {
            System.out.println("Maximum courses reached!");
            return;
        }
        
        System.out.print("Enter Course ID: ");
        String id = scanner.nextLine();
        
        if (findCourseById(id) != -1) {
            System.out.println("Course ID already exists!");
            return;
        }
        
        System.out.print("Enter Course Title: ");
        String title = scanner.nextLine();
        
        System.out.print("Enter Credit Hours (1-6): ");
        int hours = scanner.nextInt();
        scanner.nextLine();
        
        if (hours < 1 || hours > 6) {
            System.out.println("Invalid credit hours!");
            return;
        }
        
        courseIds[courseCount] = id;
        titles[courseCount] = title;
        creditHours[courseCount] = hours;
        courseCount++;
        
        saveCourses();
        System.out.println("Course added!");
    }
    
    static void deleteCourse() {
        System.out.print("Enter Course ID to delete: ");
        String id = scanner.nextLine();
        
        int index = findCourseById(id);
        if (index == -1) {
            System.out.println("Course not found!");
            return;
        }
        
        for (int i = index; i < courseCount - 1; i++) {
            courseIds[i] = courseIds[i + 1];
            titles[i] = titles[i + 1];
            creditHours[i] = creditHours[i + 1];
        }
        
        courseCount--;
        saveCourses();
        System.out.println("Course deleted!");
    }
    
    static void searchCourse() {
        System.out.print("Search by: 1. ID 2. Title: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        
        if (choice == 1) {
            System.out.print("Enter Course ID: ");
            String id = scanner.nextLine();
            
            int index = findCourseById(id);
            if (index != -1) {
                System.out.println("Found: " + courseIds[index] + " - " + 
                    titles[index] + " (" + creditHours[index] + " hours)");
            } else {
                System.out.println("Not found!");
            }
        } else if (choice == 2) {
            System.out.print("Enter keyword: ");
            String keyword = scanner.nextLine();
            
            boolean found = false;
            for (int i = 0; i < courseCount; i++) {
                if (titles[i].toLowerCase().contains(keyword.toLowerCase())) {
                    System.out.println(courseIds[i] + " - " + titles[i] + 
                        " (" + creditHours[i] + " hours)");
                    found = true;
                }
            }
            
            if (!found) {
                System.out.println("No courses found!");
            }
        } else {
            System.out.println("Invalid choice!");
        }
    }
    
    static void updateCourse() {
        System.out.print("Enter Course ID to update: ");
        String id = scanner.nextLine();
        
        int index = findCourseById(id);
        if (index == -1) {
            System.out.println("Course not found!");
            return;
        }
        
        System.out.println("Current title: " + titles[index]);
        System.out.print("New title (press Enter to keep): ");
        String newTitle = scanner.nextLine();
        if (!newTitle.isEmpty()) {
            titles[index] = newTitle;
        }
        
        System.out.println("Current hours: " + creditHours[index]);
        System.out.print("New hours (1-6, 0 to keep): ");
        int newHours = scanner.nextInt();
        scanner.nextLine();
        
        if (newHours >= 1 && newHours <= 6) {
            creditHours[index] = newHours;
        }
        
        saveCourses();
        System.out.println("Course updated!");
    }
    
    static void listCourses() {
        if (courseCount == 0) {
            System.out.println("No courses!");
            return;
        }
        
        System.out.println("All courses:");
        for (int i = 0; i < courseCount; i++) {
            System.out.println((i+1) + ". " + courseIds[i] + " - " + 
                titles[i] + " (" + creditHours[i] + " hours)");
        }
    }
    
    static void showMainMenu() {
        while (true) {
            System.out.println("\n=== MAIN MENU ===");
            System.out.println("1. Add Course");
            System.out.println("2. Delete Course");
            System.out.println("3. Search Course");
            System.out.println("4. Update Course");
            System.out.println("5. List Courses");
            System.out.println("6. Logout");
            System.out.print("Choose: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine();
            
            switch (choice) {
                case 1: addCourse(); break;
                case 2: deleteCourse(); break;
                case 3: searchCourse(); break;
                case 4: updateCourse(); break;
                case 5: listCourses(); break;
                case 6: return;
                default: System.out.println("Invalid choice!");
            }
            
            System.out.print("Press Enter to continue...");
            scanner.nextLine();
        }
    }
}