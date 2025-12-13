package group.habooba.core;

import group.habooba.core.auth.Engine;
import group.habooba.core.base.Logger;
import group.habooba.core.domain.Component;
import group.habooba.core.domain.Course;
import group.habooba.core.domain.Enrollment;
import group.habooba.core.exceptions.AuthenticationException;
import group.habooba.core.exceptions.InvalidUserUidException;
import group.habooba.core.exceptions.RepositoryFileNotFoundException;
import group.habooba.core.exceptions.WrongPasswordException;
import group.habooba.core.repository.CourseRepository;
import group.habooba.core.repository.EnrollmentRepository;
import group.habooba.core.repository.UserRepository;
import group.habooba.core.user.CourseAdmin;
import group.habooba.core.user.Student;
import group.habooba.core.user.User;

import java.io.FileNotFoundException;
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


    /**
     * The fields below store all entities as HashMaps
     */
    private CourseRepository courseRepository;
    private UserRepository userRepository;
    private EnrollmentRepository enrollmentRepository;

    private Map<Long, Component> components;
    private Map<Long, Course> courses;
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
    private void loadCoursesAndComponents(String filePath) throws RepositoryFileNotFoundException {
        // Try creating and loading CourseRepository instance
        try {
            courseRepository = new CourseRepository(filePath);
            courseRepository.load();
        } catch (FileNotFoundException e) {
            throw new RepositoryFileNotFoundException("Can't find a file for Enrollment repository.", e);
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
    public void loadUsers(String filePath) throws RepositoryFileNotFoundException {
        try {
            userRepository = new UserRepository("data/users.txt");
            userRepository.load();
        } catch (FileNotFoundException e) {
            throw new RepositoryFileNotFoundException("Can't find a file for Enrollment repository.", e);
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


    public void loadEnrollmentsAndComponentResults(String filePath) throws RepositoryFileNotFoundException {
        // Try creating and loading EnrollmentRepository instance
        try {
            enrollmentRepository = new EnrollmentRepository(filePath);
            enrollmentRepository.load();
        } catch (FileNotFoundException e) {
            throw new RepositoryFileNotFoundException("Can't find a file for Enrollment repository.", e);
        }
        List<Enrollment> loadedEnrollments = enrollmentRepository.dataAsList();
        this.enrollments = new HashMap<>();
        for(Enrollment enrollment : loadedEnrollments){
            this.enrollments.put(enrollment.uid(), enrollment);
        }
    }


    private void initUidGenerator(){

    }


    /**
     * Not yet implemented
     * @param user
     * @return
     */
    private boolean validUser(User user){
        return true;
    }

    /**
     * Checks if User exists in the program memory
     * @param user
     * @return
     */
    private boolean userExists(User user){
        return students.containsKey(user.uid()) || academicOfficers.containsKey(user.uid()) || admins.containsKey(user.uid());
    }

    /**
     * Returns user class based on UID.
     * @param uid UID of the User.
     * @return class as String if found any user with uid() == uid. "" if no User found.
     */
    private String getUserClassByUid(long uid){
        if(students.containsKey(uid))
            return "student";
        if(courseAdmins.containsKey(uid))
            return "courseAdmin";
        if(academicOfficers.containsKey(uid))
            return "academicOfficer";
        if(admins.containsKey(uid))
            return "admin";
        return "";
    }

    /**
     * User authentication
     * @param user - user to authenticate. Must have uid and password set
     * @return true if successful, otherwise - false
     */
    private boolean authenticate(User user){
        if(user.password().isBlank()) return false;
        String userClass = getUserClassByUid(user.uid());
        User existingUser;
        switch (userClass){
            case "student":
                existingUser = students.get(user.uid());
                break;
            case "courseAdmin":
                existingUser = courseAdmins.get(user.uid());
                break;
            case "academicOfficer":
                existingUser = academicOfficers.get(user.uid());
                break;
            case "admin":
                existingUser = admins.get(user.uid());
                break;
            default:
                return false;
        }
        if(existingUser.password().equals(user.password())){
            this.activeUser(existingUser);
            return true;
        }
        return false;
    }

    /**
     * Method to log user in.
     * @param user logged in data.
     */
    public void tryAuthenticate(User user){
        if(user == null) throw new AuthenticationException("Provided User is null.");
        if(!validUser(user))
            throw new AuthenticationException("Provided User data is invalid.");
        if(!userExists(user))
            throw new InvalidUserUidException("Provided User does not exist.");
        if(!authenticate(user))
            throw new WrongPasswordException("Provided User password is incorrect.");
        Logger.log("Authenticated User: " + activeUser.toText());
    }


    /**
     * Fabric method. Creates one and only Core instance. Must have all the repository files specified. All specified files must be present at the
     * specified locations when calling this method.
     * @param coursesFilePath path to a file where all courses data is stored.
     * @return Core instance.
     * @throws RepositoryFileNotFoundException if any repository file doesn't exist.
     */
    public static Core init(String coursesFilePath, String usersFilePath, String enrollmentsFilePath) throws RepositoryFileNotFoundException {
        Core core = new Core(new Engine());
        core.loadCoursesAndComponents(coursesFilePath);
        core.loadUsers(usersFilePath);
        core.loadEnrollmentsAndComponentResults(enrollmentsFilePath);
        return core;
    }

    private Core(Engine policyEngine) {
        this.courseRepository = null;
        this.userRepository = null;
        this.enrollmentRepository = null;

        this.components = null;
        this.courses = null;
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


    /**
     * Returns a copy (!) of Component by UID.
     * @param uid desired Component UID.
     * @return Component instance if found, otherwise - null
     */
    public Component getComponentByUid(long uid){
        return this.components.get(uid);
    }

    public List<Course> getActiveCourseAdminCourses(){
        return null;
    }

    public List<Enrollment> getActiveStudentEnrollments(){
        return null;
    }
}