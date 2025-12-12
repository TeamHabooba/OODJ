package group.habooba.core.domain;

public class UidGenerator {

    private Manifest manifest;

    public UidGenerator(){
        this.manifest = new Manifest();
    }

    public UidGenerator(Manifest manifest){
        this.manifest = manifest;
    }


    public long nextStudentUid(){
        return manifest.nextStudentUid();
    }

    public long nextCourseAdminUid(){
        return manifest.nextCourseAdminUid();
    }

    public long nextAcademicOfficerUid(){
        return manifest.nextAcademicOfficerUid();
    }

    public long nextAdminUid(){
        return manifest.nextAdminUid();
    }

    public long nextComponentUid(){
        return manifest.nextComponentUid();
    }

    public long nextEnrollmentUid(){
        return manifest.nextEnrollmentUid();
    }

    public long nextCourseUid(){
        return manifest.nextCourseUid();
    }

    public long nextPolicyUid(){
        return manifest.nextPolicyUid();
    }
}
