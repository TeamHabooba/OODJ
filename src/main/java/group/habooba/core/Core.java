package group.habooba.core;

import group.habooba.core.auth.Engine;
import group.habooba.core.domain.Component;
import group.habooba.core.domain.ComponentResult;
import group.habooba.core.domain.Course;
import group.habooba.core.repository.CourseRepository;
import group.habooba.core.user.CourseAdmin;
import group.habooba.core.user.Student;
import group.habooba.core.user.User;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Core {


    private static User activeUser;

    public static void setActiveUser(User user) {
        activeUser = user;
    }

    public static User getActiveUser() {
        return activeUser;
    }

    public static String getActiveUserClass() {
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

    private Map<Long, Component> components;
    private Map<Long, Course> courses;
    private Map<Long, ComponentResult> componentResults;
    private Map<Long, Student> students;
    private Map<Long, CourseAdmin> courseAdmins;
    private Map<Long, User> academicOfficers;
    private Map<Long, User> admins;
    private Engine policyEngine;


    /**
     * Loads all Course and Component objects from file
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
     * Fabric method. Creates one and only Core instance
     * @param coursesFilePath path to a file where all courses data is stored
     * @return Core instance
     */
    public static Core init(String coursesFilePath) {
        Core core = new Core();
        core.loadCoursesAndComponents(coursesFilePath);
        return core;
    }

    private Core(ArrayList<User> users, ArrayList<Course> courses, Engine policyEngine) {
        this.courseRepository = null;

        this.components = null;
        this.courses = null;
        this.componentResults = null;
        this.students = null;
        this.courseAdmins = null;
        this.academicOfficers = null;
        this.admins = null;
        this.policyEngine = null;
    }

    private Core() {
        this(new ArrayList<>(), new ArrayList<>(), new Engine());
    }
}
