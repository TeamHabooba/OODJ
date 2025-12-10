package group.habooba.core.user;

import group.habooba.core.domain.StudentAcademics;
import group.habooba.core.auth.AttributeMap;

public class Student extends User {

    private StudentAcademics academics;


    /*Student() {
        super();
        this.academics = new StudentAcademics();
    }*/

    Student(long uid, String password, String email,
            AttributeMap attributes,
            Profile profile, StudentAcademics academics) {
        super(uid, password, email, attributes, profile);
        this.academics = academics;
    }


}