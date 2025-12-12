package group.habooba.core;

import group.habooba.core.auth.Engine;
import group.habooba.core.domain.Component;
import group.habooba.core.domain.ComponentResult;
import group.habooba.core.domain.Course;
import group.habooba.core.domain.Enrollment;
import group.habooba.core.repository.CourseRepository;
import group.habooba.core.repository.UserRepository;
import group.habooba.core.user.CourseAdmin;
import group.habooba.core.user.Student;
import group.habooba.core.user.User;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Core {


    private User activeUser;

    public void activeUser(User user) {
        activeUser = user;
    }

    public User activeUser() {
        return activeUser;
    }

    public String activeUserClass() {
        if (activeUser == null) return "";
        return activeUser.getClass().getSimpleName().toLowerCase();
    }


    public static String CURRENT_FILE_FORMAT_VERSION = "0.2";
    public static int CURRENT_FILE_FORMAT_VERSION_MAJOR = 0;
    public static int CURRENT_FILE_FORMAT_VERSION_MINOR = 2;

    /**
     * The fields below store all entities as HashMaps
     */
    private CourseRepository courseRepository;
    private UserRepository userRepository;

    private Map<Long, Component> components;
    private Map<Long, Course> courses;
    private Map<Long, ComponentResult> componentResults;
    private Map<Long, Enrollment> enrollments;
    private Map<Long, Student> students;
    private Map<Long, CourseAdmin> courseAdmins;
    private Map<Long, User> academicOfficers;
    private Map<Long, User> admins;
    private Engine policyEngine;


    /**
     * Loads all Course and Component objects from file. Creates 2 indexes: components and courses
     * @param filePath file to load data from
     */
    private void loadCoursesAndComponents(String filePath){
        // Try creating and loading CourseRepository instance
        try {
            courseRepository = new CourseRepository(filePath);
            courseRepository.load();
        } catch (FileNotFoundException e) {
            // TODO: make custom exception
            throw new RuntimeException(e);
        }
        // Getting data from courseRepository as parsed Course instances collection (List<>)
        List<Course> loadedCourses = courseRepository.dataAsList();
        // Creating indexes by UID for all Courses and Components loaded
        this.courses = new HashMap<>();
        this.components = new HashMap<>();
        for(Course course : loadedCourses){
            this.courses.put(course.uid(), course);
            for(Component component : course.components()){
                this.components.put(component.uid(), component);
            }
        }
    }

    /**
     * Loads all User objects from file. Creates 4 indexes: students, courseAdmins, academicOfficers, admins
     * @param filePath file to load data from
     */
    public void loadUsers(String filePath){
        try {
            userRepository = new UserRepository("data/users.txt");
            userRepository.load();
        } catch (FileNotFoundException e) {
            // TODO: make custom exception
            throw new RuntimeException(e);
        }
        // Getting data from courseRepository as parsed User instances collection (List<>)
        List<User> loadedUsers = userRepository.dataAsList();
        // Creating indexes by UID for all Courses and Components loaded
        this.students = new HashMap<>();
        this.courseAdmins = new HashMap<>();
        this.academicOfficers = new HashMap<>();
        this.admins = new HashMap<>();
        for(User user: loadedUsers){
            switch ((String) user.attributes().get("class")){
                case "student":
                    this.students.put(user.uid(), new Student(user));
                    break;
                case "courseAdmin":
                    this.courseAdmins.put(user.uid(), new CourseAdmin(user));
                    break;
                case "academicOfficer":
                    this.academicOfficers.put(user.uid(), user);
                    break;
                case "admin":
                    this.admins.put(user.uid(), user);
                    break;
            }
        }
    }

    public static void authenticate(User user){
        if(user == null)
    }

    /**
     * Fabric method. Creates one and only Core instance
     * @param coursesFilePath path to a file where all courses data is stored
     * @return Core instance
     */
    public static Core init(String coursesFilePath, String usersFilePath) {
        Core core = new Core();
        core.loadCoursesAndComponents(coursesFilePath);
        core.loadUsers(usersFilePath);
        return core;
    }

    private Core(Engine policyEngine) {
        this.courseRepository = null;

        this.components = null;
        this.courses = null;
        this.componentResults = null;
        this.enrollments = null;
        this.students = null;
        this.courseAdmins = null;
        this.academicOfficers = null;
        this.admins = null;
        this.policyEngine = null;
    }

    private Core() {
        this(new Engine());
    }
}
