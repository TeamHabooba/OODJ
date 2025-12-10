package group.habooba.core;

import group.habooba.core.auth.Engine;
import group.habooba.core.domain.Component;
import group.habooba.core.domain.ComponentResult;
import group.habooba.core.domain.Course;
import group.habooba.core.user.CourseAdmin;
import group.habooba.core.user.Student;
import group.habooba.core.user.User;

import java.util.ArrayList;
import java.util.HashMap;

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


    public static String CURRENT_FILE_FORMAT_VERSION = "0.1";
    public static int CURRENT_FILE_FORMAT_VERSION_MAJOR = 0;
    public static int CURRENT_FILE_FORMAT_VERSION_MINOR = 1;

    private final HashMap<Long, Component> components;
    private final HashMap<Long, Course> courses;
    private final HashMap<Long, ComponentResult> componentResults;
    private final HashMap<Long, Student> students;
    private final HashMap<Long, CourseAdmin> courseAdmins;
    private final HashMap<Long, User> academicOfficers;
    private final HashMap<Long, User> admins;
    private final Engine policyEngine;


    public static Core init() {
        return new Core();
    }

    private Core(ArrayList<User> users, ArrayList<Course> courses, Engine policyEngine) {
        this.components = new HashMap<>();
        this.courses = new HashMap<>();
        this.componentResults = new HashMap<>();
        this.students = new HashMap<>();
        this.courseAdmins = new HashMap<>();
        this.academicOfficers = new HashMap<>();
        this.admins = new HashMap<>();
        this.policyEngine = policyEngine;
    }

    private Core() {
        this(new ArrayList<>(), new ArrayList<>(), new Engine());
    }
}
