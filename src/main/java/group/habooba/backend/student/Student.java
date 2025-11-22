package group.habooba.backend.student;

import group.habooba.backend.User;

public class Student extends User {

    private StudentProfile profile;
    private StudentAcademics academics;


    Student() {
    }


    Student(long uid, String password, String email,
            AttributeMap attributes, long policyId,
            StudentProfile profile, StudentAcademics academics) {
        super(uid, password, email);
        this.attributes = attributes;
        this.policyId = policyId;
        this.profile = profile;
        this.academics = academics;
    }
}