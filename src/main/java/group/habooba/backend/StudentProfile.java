package group.habooba.backend;

import java.util.List;

public class StudentProfile {
    private String firstName;
    private String lastName;
    private List<Course> courses;

    public StudentProfile(String firstName, String lastName, List<Course> courses) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.courses = courses;
    }


    public String firstName() {
        return firstName;
    }

    public String lastName() {
        return lastName;
    }

    public String name() {
        return firstName + " " + lastName;
    }

    public ProgramType program() {

        return null;
    }

    public SchoolOfStudy schoolOfStudy() {

        return SchoolOfStudy.NONE;
    }

    public int year() {

        return 0;
    }

    public boolean nextLevelAllowed() {

        return false;
    }

    public List<Course> courses() {
        return courses;
    }
}