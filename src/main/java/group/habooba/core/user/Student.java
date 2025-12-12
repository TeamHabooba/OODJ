package group.habooba.core.user;

import group.habooba.core.domain.StudentAcademics;
import group.habooba.core.auth.AttributeMap;

public class Student extends User {

    private StudentAcademics academics;

    public Student() {
        super();
        this.academics = new StudentAcademics();
    }

    public Student(long uid, String password, String email,
            AttributeMap attributes,
            Profile profile, StudentAcademics academics) {
        super(uid, password, email, attributes, profile);
        this.academics = academics;
    }

    public Student(User user){
        super(user.uid,  user.password, user.email, user.attributes, user.profile);
        this.academics = new StudentAcademics();
    }

    public StudentAcademics academics() {
        return academics;
    }

    public void academics(StudentAcademics academics) {
        this.academics = academics;
    }
}