package group.habooba.core.domain;

public class Manifest {
    public static final String CURRENT_FILE_FORMAT_VERSION = "0.2";
    public static final int CURRENT_FILE_FORMAT_VERSION_MAJOR = 0;
    public static final int CURRENT_FILE_FORMAT_VERSION_MINOR = 2;

    public static final String PATH = "data/manifest.txt";





    public Manifest(){

    }

    public Manifest(long student, long courseAdmin, long academicOfficer, long admin, long component, long enrollment, long course, long policy){
        currentStudentUid = student;
        currentCourseAdminUid = courseAdmin;
        currentAcademicOfficerUid = academicOfficer;
        currentAdminUid = admin;
        currentComponentUid = component;
        currentEnrollmentUid = enrollment;
        currentCourseUid = course;
        currentPolicyUid = policy;
    }


    public long nextStudentUid(){
        return ++currentStudentUid;
    }

    public long nextCourseAdminUid(){
        return ++currentCourseAdminUid;
    }

    public long nextAcademicOfficerUid(){
        return ++currentAcademicOfficerUid;
    }

    public long nextAdminUid(){
        return ++currentAdminUid;
    }

    public long nextComponentUid(){
        return ++currentComponentUid;
    }

    public long nextEnrollmentUid(){
        return ++currentEnrollmentUid;
    }

    public long nextCourseUid(){
        return ++currentCourseUid;
    }

    public long nextPolicyUid(){
        return ++currentPolicyUid;
    }


    public long currentStudentUid(){
        return currentStudentUid;
    }

    public long currentCourseAdminUid(){
        return currentCourseAdminUid;
    }

    public long currentAcademicOfficerUid(){
        return currentAcademicOfficerUid;
    }

    public long currentAdminUid(){
        return currentAdminUid;
    }

    public long currentComponentUid(){
        return currentComponentUid;
    }

    public long currentEnrollmentUid(){
        return currentEnrollmentUid;
    }

    public long currentCourseUid(){
        return currentCourseUid;
    }

    public long currentPolicyUid(){
        return currentPolicyUid;
    }



}
