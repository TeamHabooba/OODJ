package group.habooba.core.domain;

public class Manifest {
    public static final String CURRENT_FILE_FORMAT_VERSION = "0.2";
    public static final int CURRENT_FILE_FORMAT_VERSION_MAJOR = 0;
    public static final int CURRENT_FILE_FORMAT_VERSION_MINOR = 2;

    public static final String PATH = "data/manifest.txt";

    public static final long STUDENT_BASE_UID = 0x0001_0000_0000_0000L;
    public static final long COURSE_ADMIN_BASE_ID = 0x0001_8000_0000_0000L;
    public static final long ACADEMIC_OFFICER_BASE_ID = 0x0001_4000_0000_0000L;
    public static final long ADMIN_BASE_ID = 0x0000_0000_0000_0040L;
    public static final long COMPONENT_BASE_ID = 0x0000_8000_0000_0000L;
    public static final long ENROLLMENT_BASE_ID = 0x0000_4000_0000_0000L;
    public static final long COURSE_BASE_ID = 0x0000_2000_0000_0000L;
    public static final long POLICY_BASE_ID = 0x0000_1000_0000_0000L;

    private long currentStudentUid = STUDENT_BASE_UID + 1;
    private long currentCourseAdminUid = COURSE_ADMIN_BASE_ID + 1;
    private long currentAcademicOfficerUid = ACADEMIC_OFFICER_BASE_ID + 1;
    private long currentAdminUid = ADMIN_BASE_ID + 1;
    private long currentComponentUid = COMPONENT_BASE_ID + 1;
    private long currentEnrollmentUid = ENROLLMENT_BASE_ID + 1;
    private long currentCourseUid = COURSE_BASE_ID + 1;
    private long currentPolicyUid = POLICY_BASE_ID + 1;

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
