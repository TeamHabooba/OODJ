package group.habooba.core;

import group.habooba.core.auth.Engine;
import group.habooba.core.auth.Policy;
import group.habooba.core.base.AppObject;
import group.habooba.core.base.Logger;
import group.habooba.core.domain.Component;
import group.habooba.core.domain.Course;
import group.habooba.core.domain.Enrollment;
import group.habooba.core.domain.UidSchema;
import group.habooba.core.exceptions.AuthenticationException;
import group.habooba.core.exceptions.InvalidUserUidException;
import group.habooba.core.exceptions.RepositoryFileNotFoundException;
import group.habooba.core.exceptions.WrongPasswordException;
import group.habooba.core.repository.CourseRepository;
import group.habooba.core.repository.EnrollmentRepository;
import group.habooba.core.repository.UserRepository;
import group.habooba.core.user.CourseAdmin;
import group.habooba.core.user.Student;
import group.habooba.core.user.User;

import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

public class Core {


    private User activeUser;

    public void activeUser(User user) {
        activeUser = user;
    }

    public User activeUser() {
        return activeUser;
    }

    public String activeUserClass() {
        if (activeUser == null) return "";
        return activeUser.getClass().getSimpleName().toLowerCase();
    }


    /**
     * The fields below store all entities as HashMaps
     */
    private CourseRepository courseRepository;
    private UserRepository userRepository;
    private EnrollmentRepository enrollmentRepository;

    private Map<Long, Component> components;
    private Map<Long, Course> courses;
    private Map<Long, Enrollment> enrollments;
    private Map<Long, Student> students;
    private Map<Long, CourseAdmin> courseAdmins;
    private Map<Long, User> academicOfficers;
    private Map<Long, User> admins;
    private Map<Long, Policy> policies;
    private Engine policyEngine;


    /**
     * Loads all Course and Component objects from file. Creates 2 indexes: components and courses
     * @param filePath file to load data from
     */
    private void loadCoursesAndComponents(String filePath) throws RepositoryFileNotFoundException {
        // Try creating and loading CourseRepository instance
        try {
            courseRepository = new CourseRepository(filePath);
            courseRepository.load();
        } catch (FileNotFoundException e) {
            throw new RepositoryFileNotFoundException("Can't find a file for Enrollment repository.", e);
        }
        // Getting data from courseRepository as parsed Course instances collection (List<>)
        List<Course> loadedCourses = courseRepository.dataAsList();
        // Creating indexes by UID for all Courses and Components loaded
        this.courses = new HashMap<>();
        this.components = new HashMap<>();
        for(Course course : loadedCourses){
            this.courses.put(course.uid(), course);
            for(Component component : course.components()){
                this.components.put(component.uid(), component);
            }
        }
    }

    /**
     * Loads all User objects from file. Creates 4 indexes: students, courseAdmins, academicOfficers, admins
     * @param filePath file to load data from
     */
    public void loadUsers(String filePath) throws RepositoryFileNotFoundException {
        try {
            userRepository = new UserRepository("data/users.txt");
            userRepository.load();
        } catch (FileNotFoundException e) {
            throw new RepositoryFileNotFoundException("Can't find a file for Enrollment repository.", e);
        }
        // Getting data from courseRepository as parsed User instances collection (List<>)
        List<User> loadedUsers = userRepository.dataAsList();
        // Creating indexes by UID for all Courses and Components loaded
        this.students = new HashMap<>();
        this.courseAdmins = new HashMap<>();
        this.academicOfficers = new HashMap<>();
        this.admins = new HashMap<>();
        for(User user: loadedUsers){
            switch ((String) user.attributes().get("class")){
                case "student":
                    this.students.put(user.uid(), new Student(user));
                    break;
                case "courseAdmin":
                    this.courseAdmins.put(user.uid(), new CourseAdmin(user));
                    break;
                case "academicOfficer":
                    this.academicOfficers.put(user.uid(), user);
                    break;
                case "admin":
                    this.admins.put(user.uid(), user);
                    break;
            }
        }
    }


    public void loadEnrollmentsAndComponentResults(String filePath) throws RepositoryFileNotFoundException {
        // Try creating and loading EnrollmentRepository instance
        try {
            enrollmentRepository = new EnrollmentRepository(filePath);
            enrollmentRepository.load();
        } catch (FileNotFoundException e) {
            throw new RepositoryFileNotFoundException("Can't find a file for Enrollment repository.", e);
        }
        List<Enrollment> loadedEnrollments = enrollmentRepository.dataAsList();
        this.enrollments = new HashMap<>();
        for(Enrollment enrollment : loadedEnrollments){
            this.enrollments.put(enrollment.uid(), enrollment);
        }
    }


    private void initUidGenerator(){

    }


    /**
     * Not yet implemented
     * @param user
     * @return
     */
    private boolean validUser(User user){
        return true;
    }

    /**
     * Checks if User exists in the program memory
     * @param user
     * @return
     */
    private boolean userExists(User user){
        return students.containsKey(user.uid()) || academicOfficers.containsKey(user.uid()) || admins.containsKey(user.uid());
    }

    /**
     * Returns user class based on UID.
     * @param uid UID of the User.
     * @return class as String if found any user with uid() == uid. "" if no User found.
     */
    private String getUserClassByUid(long uid){
        if(students.containsKey(uid))
            return "student";
        if(courseAdmins.containsKey(uid))
            return "courseAdmin";
        if(academicOfficers.containsKey(uid))
            return "academicOfficer";
        if(admins.containsKey(uid))
            return "admin";
        return "";
    }

    /**
     * User authentication
     * @param user - user to authenticate. Must have uid and password set
     * @return true if successful, otherwise - false
     */
    private boolean authenticate(User user){
        if(user.password().isBlank()) return false;
        String userClass = getUserClassByUid(user.uid());
        User existingUser;
        switch (userClass){
            case "student":
                existingUser = students.get(user.uid());
                break;
            case "courseAdmin":
                existingUser = courseAdmins.get(user.uid());
                break;
            case "academicOfficer":
                existingUser = academicOfficers.get(user.uid());
                break;
            case "admin":
                existingUser = admins.get(user.uid());
                break;
            default:
                return false;
        }
        if(existingUser.password().equals(user.password())){
            this.activeUser(existingUser);
            return true;
        }
        return false;
    }

    /**
     * Method to log user in.
     * @param user logged in data.
     */
    public void tryAuthenticate(User user){
        if(user == null) throw new AuthenticationException("Provided User is null.");
        if(!validUser(user))
            throw new AuthenticationException("Provided User data is invalid.");
        if(!userExists(user))
            throw new InvalidUserUidException("Provided User does not exist.");
        if(!authenticate(user))
            throw new WrongPasswordException("Provided User password is incorrect.");
        Logger.log("Authenticated User: " + activeUser.toText());
    }


    /**
     * Fabric method. Creates one and only Core instance. Must have all the repository files specified. All specified files must be present at the
     * specified locations when calling this method.
     * @param coursesFilePath path to a file where all courses data is stored.
     * @return Core instance.
     * @throws RepositoryFileNotFoundException if any repository file doesn't exist.
     */
    public static Core init(String coursesFilePath, String usersFilePath, String enrollmentsFilePath) throws RepositoryFileNotFoundException {
        Core core = new Core(new Engine());
        core.loadCoursesAndComponents(coursesFilePath);
        core.loadUsers(usersFilePath);
        core.loadEnrollmentsAndComponentResults(enrollmentsFilePath);
        return core;
    }

    private Core(Engine policyEngine) {
        this.courseRepository = null;
        this.userRepository = null;
        this.enrollmentRepository = null;

        this.components = null;
        this.courses = null;
        this.enrollments = null;
        this.students = null;
        this.courseAdmins = null;
        this.academicOfficers = null;
        this.admins = null;
        this.policyEngine = null;
    }

    private Core() {
        this(new Engine());
    }


    /**
     * Returns a copy (!) of Component by UID.
     * @param uid desired Component UID.
     * @return Component instance if found, otherwise - null
     */
    public Component getComponentByUid(long uid){
        return this.components.get(uid);
    }

    public List<Course> getActiveCourseAdminCourses(){
        return null;
    }

    public List<Enrollment> getActiveStudentEnrollments(){
        return null;
    }


    /**
     * Last old->new mapping produced by the most recent rebuild operation.
     * Used to update cross-collection references (e.g. enrollments -> students).
     */
    private Map<Long, Long> lastOldToNewMapping = Collections.emptyMap();

    /**
     * Accessor for the last old->new mapping after a rebuild operation.
     *
     * @return oldUid -> newUid map
     */
    private Map<Long, Long> getLastOldToNewMapping() {
        return lastOldToNewMapping == null ? Collections.emptyMap() : lastOldToNewMapping;
    }

    /**
     * Generic utility that reassigns UIDs inside a Map<Long, T extends AppObject> to remove gaps
     * and returns a new map keyed by new uids.
     *
     * <p>
     * Reassignment rules:
     * - Collect values from the original map.
     * - Separate "good" (uid in range and >0) and "bad" (uid <=0 or out of entity range).
     * - Sort good by current uid, reassign sequentially starting from startUid.
     * - Append bad items after the good ones if space permits (also reassign).
     * - Record old->new mapping in {@link #lastOldToNewMapping} for later reference updates.
     * </p>
     *
     * @param <T> the entity type; must extend AppObject (provide uid()/uid(long))
     * @param originalMap the original map keyed by uid
     * @param startUid first uid to assign (inclusive)
     * @param maxUid maximum uid allowed (inclusive)
     * @return new map keyed by reassigned uid
     * @throws IllegalStateException if assignment exceeds maxUid
     */
    private <T extends AppObject> Map<Long, T> rebuildAndReassignUids(
            Map<Long, T> originalMap,
            long startUid,
            long maxUid
    ) {
        Map<Long, T> result = new HashMap<>();
        if (originalMap == null || originalMap.isEmpty()) {
            lastOldToNewMapping = Collections.emptyMap();
            return result;
        }

        List<T> good = new ArrayList<>();
        List<T> bad = new ArrayList<>();

        // split items into good and bad
        for (T obj : originalMap.values()) {
            long cur = obj == null ? Long.MIN_VALUE : obj.uid();
            if (cur <= 0L || cur < startUid || cur > maxUid) {
                bad.add(obj);
            } else {
                good.add(obj);
            }
        }

        // sort good by current uid ascending
        good.sort(Comparator.comparingLong(AppObject::uid));

        long next = startUid;
        Map<Long, Long> oldToNew = new HashMap<>();

        // reassign good items
        for (T item : good) {
            if (next > maxUid) throw new IllegalStateException("UID space exhausted while reassigning");
            long old = item.uid();
            long nue = next++;
            item.uid(nue);
            oldToNew.put(old, nue);
            result.put(nue, item);
        }

        // append bad items (reassign them), if space permits
        for (T item : bad) {
            if (next > maxUid) break; // no space left
            long old = (item == null) ? -1L : item.uid();
            long nue = next++;
            item.uid(nue);
            if (old > 0L) oldToNew.put(old, nue);
            result.put(nue, item);
        }

        lastOldToNewMapping = oldToNew;
        return result;
    }

    /**
     * Check whether a map keyed by Long has no gaps in its keys (strictly consecutive).
     *
     * @param map map keyed by uid
     * @return true if keys are consecutive (no gaps) and all >0; true for null/empty
     */
    private static boolean noGapsInMapKeys(Map<Long, ?> map) {
        if (map == null || map.isEmpty()) return true;
        List<Long> keys = map.keySet().stream()
                .filter(Objects::nonNull)
                .sorted()
                .collect(Collectors.toList());
        if (keys.isEmpty()) return true;
        for (Long k : keys) {
            if (k == null || k <= 0L) return false;
        }
        for (int i = 1; i < keys.size(); ++i) {
            long prev = keys.get(i - 1);
            long cur = keys.get(i);
            if (cur != prev + 1) return false;
        }
        return true;
    }

    /* --------- update enrollment student reference using known API (no reflection) --------- */

    /**
     * Update enrollment's studentUid from oldStudentUid to newStudentUid.
     * Assumes Enrollment exposes long studentUid() and void studentUid(long).
     *
     * @param enrollment the enrollment object to update
     * @param oldStudentUid old student uid to replace
     * @param newStudentUid new student uid value
     * @return true if updated, false otherwise
     */
    private static boolean updateEnrollmentStudentReference(Enrollment enrollment, long oldStudentUid, long newStudentUid) {
        if (enrollment == null) return false;
        long cur = enrollment.studentUid();
        if (cur != oldStudentUid) return false;
        enrollment.studentUid(newStudentUid);
        return true;
    }

    /* -------------- per-collection check methods -------------- */

    public boolean checkNoGapsInComponents() { return noGapsInMapKeys(this.components); }
    public boolean checkNoGapsInCourses() { return noGapsInMapKeys(this.courses); }
    public boolean checkNoGapsInEnrollments() { return noGapsInMapKeys(this.enrollments); }
    public boolean checkNoGapsInStudents() { return noGapsInMapKeys(this.students); }
    public boolean checkNoGapsInCourseAdmins() { return noGapsInMapKeys(this.courseAdmins); }
    public boolean checkNoGapsInAcademicOfficers() { return noGapsInMapKeys(this.academicOfficers); }
    public boolean checkNoGapsInAdmins() { return noGapsInMapKeys(this.admins); }
    public boolean checkNoGapsInPolicies() { return noGapsInMapKeys(this.policies); }

    /* -------------- per-collection repair methods (no reflection) -------------- */

    /**
     * Repair students and update enrollments' studentUid references accordingly.
     *
     * @return true if changes were made
     */
    public boolean repairStudents() {
        if (students == null) return false;

        Map<Long, Student> newMap = rebuildAndReassignUids(this.students, UidSchema.STUDENT_UID_MIN, UidSchema.STUDENT_UID_MAX);
        Map<Long, Long> oldToNew = getLastOldToNewMapping();

        // update enrollments references
        if (this.enrollments != null && !oldToNew.isEmpty()) {
            for (Enrollment e : this.enrollments.values()) {
                for (Map.Entry<Long, Long> entry : oldToNew.entrySet()) {
                    if (updateEnrollmentStudentReference(e, entry.getKey(), entry.getValue())) {
                        // updated, continue to next enrollment
                    }
                }
            }
        }

        boolean changed = !this.students.keySet().equals(newMap.keySet()) || !this.students.equals(newMap);
        this.students = newMap;
        return changed;
    }

    public boolean repairCourseAdmins() {
        if (courseAdmins == null) return false;
        Map<Long, CourseAdmin> newMap = rebuildAndReassignUids(this.courseAdmins, UidSchema.COURSE_ADMIN_UID_MIN, UidSchema.COURSE_ADMIN_UID_MAX);
        boolean changed = !this.courseAdmins.keySet().equals(newMap.keySet()) || !this.courseAdmins.equals(newMap);
        this.courseAdmins = newMap;
        return changed;
    }

    public boolean repairAcademicOfficers() {
        if (academicOfficers == null) return false;
        Map<Long, User> newMap = rebuildAndReassignUids(this.academicOfficers, UidSchema.ACADEMIC_OFFICER_UID_MIN, UidSchema.ACADEMIC_OFFICER_UID_MAX);
        boolean changed = !this.academicOfficers.keySet().equals(newMap.keySet()) || !this.academicOfficers.equals(newMap);
        this.academicOfficers = newMap;
        return changed;
    }

    public boolean repairAdmins() {
        if (admins == null) return false;
        Map<Long, User> newMap = rebuildAndReassignUids(this.admins, UidSchema.ADMIN_UID_MIN, UidSchema.ADMIN_UID_MAX);
        boolean changed = !this.admins.keySet().equals(newMap.keySet()) || !this.admins.equals(newMap);
        this.admins = newMap;
        return changed;
    }

    public boolean repairComponents() {
        if (components == null) return false;
        Map<Long, Component> newMap = rebuildAndReassignUids(this.components, UidSchema.COMPONENT_MIN, UidSchema.COMPONENT_MAX);
        boolean changed = !this.components.keySet().equals(newMap.keySet()) || !this.components.equals(newMap);
        this.components = newMap;
        return changed;
    }

    public boolean repairCourses() {
        if (courses == null) return false;
        Map<Long, Course> newMap = rebuildAndReassignUids(this.courses, UidSchema.COURSE_MIN, UidSchema.COURSE_MAX);
        boolean changed = !this.courses.keySet().equals(newMap.keySet()) || !this.courses.equals(newMap);
        this.courses = newMap;
        return changed;
    }

    public boolean repairEnrollments() {
        if (enrollments == null) return false;
        Map<Long, Enrollment> newMap = rebuildAndReassignUids(this.enrollments, UidSchema.ENROLLMENT_MIN, UidSchema.ENROLLMENT_MAX);

        // If students were remapped previously, try to update enrollments accordingly:
        Map<Long, Long> oldToNew = getLastOldToNewMapping();
        if (oldToNew != null && !oldToNew.isEmpty()) {
            for (Enrollment e : newMap.values()) {
                for (Map.Entry<Long, Long> entry : oldToNew.entrySet()) {
                    updateEnrollmentStudentReference(e, entry.getKey(), entry.getValue());
                }
            }
        }

        boolean changed = !this.enrollments.keySet().equals(newMap.keySet()) || !this.enrollments.equals(newMap);
        this.enrollments = newMap;
        return changed;
    }

    public boolean repairPolicies() {
        if (policies == null) return false;
        Map<Long, Policy> newMap = rebuildAndReassignUids(this.policies, UidSchema.POLICY_MIN, UidSchema.POLICY_MAX);
        boolean changed = !this.policies.keySet().equals(newMap.keySet()) || !this.policies.equals(newMap);
        this.policies = newMap;
        return changed;
    }

    /**
     * Repair all collections in the preferred order and persist changes to repositories.
     *
     * @return true if any repair or change was applied
     */
    public boolean repairAllAndPersist() {
        boolean changedAny = false;

        // Order: enrollments -> components -> courses -> profiles (if implemented) -> students -> courseAdmins -> academicOfficers -> admins -> policies
        changedAny |= repairEnrollments();
        changedAny |= repairComponents();
        changedAny |= repairCourses();
        changedAny |= repairStudents();
        changedAny |= repairCourseAdmins();
        changedAny |= repairAcademicOfficers();
        changedAny |= repairAdmins();
        changedAny |= repairPolicies();

        // persist back to repositories if changes occurred
        if (changedAny) {
            try {
                if (courseRepository != null) {
                    List<Course> courseList = new ArrayList<>(this.courses.values());
                    courseRepository.updateDataFromList(courseList);
                    courseRepository.save();
                }
                if (userRepository != null) {
                    List<User> allUsers = new ArrayList<>();
                    if (students != null) allUsers.addAll(students.values());
                    if (courseAdmins != null) allUsers.addAll(courseAdmins.values());
                    if (academicOfficers != null) allUsers.addAll(academicOfficers.values());
                    if (admins != null) allUsers.addAll(admins.values());
                    userRepository.updateDataFromList(allUsers);
                    userRepository.save();
                }
                if (enrollmentRepository != null) {
                    List<Enrollment> enrollmentList = new ArrayList<>(this.enrollments.values());
                    enrollmentRepository.updateDataFromList(enrollmentList);
                    enrollmentRepository.save();
                }
            } catch (Exception ex) {
                throw new IllegalStateException("Failed saving repaired data to repositories", ex);
            }
        }

        return changedAny;
    }
}
