package group.habooba.core.user;

import group.habooba.core.base.AttributeMap;
import group.habooba.core.domain.Course;

import java.util.ArrayList;

public class CourseAdmin extends User {
    private ArrayList<Course> courses;

    public CourseAdmin(long uid) { this(uid, new ArrayList<Course>());}

    public CourseAdmin(long uid, ArrayList<Course> courses) {this(uid, "", "", new AttributeMap(), new Profile("",""), courses);}

    public CourseAdmin(long uid, String password, String email, AttributeMap attributes, Profile profile, ArrayList<Course> courses){
        super(uid, password, email, attributes, profile);
        this.courses = courses;
    }

    public CourseAdmin(User user){
        super(user.uid,  user.password, user.email, user.attributes, user.profile);
        this.courses = new ArrayList<>();
    }
}
