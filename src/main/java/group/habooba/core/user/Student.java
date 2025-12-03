package group.habooba.core.user;

import group.habooba.core.domain.StudentAcademics;
import group.habooba.core.auth.AttributeMap;

public class Student extends User {

    private Profile profile;
    private StudentAcademics academics;


    Student() {
    }


    Student(long uid, String password, String email,
            AttributeMap attributes, long policyId,
            Profile profile, StudentAcademics academics) {
        super(uid, password, email);
        this.attributes = attributes;
        this.policyId = policyId;
        this.profile = profile;
        this.academics = academics;
    }
}