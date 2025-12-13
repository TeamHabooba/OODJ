package group.habooba.core.domain;

import group.habooba.core.base.AppObject;
import group.habooba.core.exceptions.UninitializedManifestException;
import group.habooba.core.exceptions.ValueException;

public class Uids {

    private static Manifest manifest;

    public static void initManifest(){
        manifest = new Manifest();
    }

    public static void initManifest(Manifest manifest){
        if(!(Uids.manifest == null))
            throw new ValueException("Manifest already initialized");
        Uids.manifest = manifest;
    }

    /**
     * Used to override standard data/manifest.txt path
     * @param overridenPath
     */
    public static void initManifest(String overridenPath){

    }

    private static void checkManifestInitialization(){
        if(Uids.manifest == null) throw new UninitializedManifestException("Manifest has not been initialized. Please call initManifest() first.");
    }


    public static long nextStudentUid(){
        return manifest.nextStudentUid();
    }

    private static void resetStudentUid(){

    }

    public static long nextCourseAdminUid(){
        return manifest.nextCourseAdminUid();
    }

    private static void resetCourseAdminUid(){

    }

    public static long nextAcademicOfficerUid(){
        return manifest.nextAcademicOfficerUid();
    }

    private static void resetAcademicOfficerUid(){

    }

    public static long nextAdminUid(){
        return manifest.nextAdminUid();
    }

    private static void resetAdminUid(){

    }

    public static long nextComponentUid(){
        return manifest.nextComponentUid();
    }

    private static void resetComponentUid(){

    }

    public static long nextEnrollmentUid(){
        return manifest.nextEnrollmentUid();
    }

    private static void resetEnrollmentUid(){

    }

    public static long nextCourseUid(){
        return manifest.nextCourseUid();
    }

    private static void resetCourseUid(){

    }

    public static long nextPolicyUid(){
        return manifest.nextPolicyUid();
    }

    private static void resetPolicyUid(){

    }

    public static boolean isValidUid(AppObject instance){
        if(instance.uid() == -1L) return false;
        switch(instance.type()){
            case "user":
                return isValidUserUid(instance);
            case "course":

        }
        return false;
    }

    public static boolean isValidUserUid(AppObject instance){
        return false;
    }
}
