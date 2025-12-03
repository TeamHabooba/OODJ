package group.habooba.core;

import group.habooba.core.auth.Engine;
import group.habooba.core.domain.Course;
import group.habooba.core.user.User;

import java.util.ArrayList;

class Context {
    private final ArrayList<User> users;
    private final ArrayList<Course> courses;
    private final Engine policyEngine;

    public Context(ArrayList<User> users, ArrayList<Course> courses, Engine policyEngine) {
        this.users = users;
        this.courses = courses;
        this.policyEngine = policyEngine;
    }

    public Context() {
        this(new ArrayList<>(), new ArrayList<>(), new Engine());
    }
}
