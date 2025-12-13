package group.habooba.core;

import group.habooba.core.auth.Engine;
import group.habooba.core.auth.Policy;
import group.habooba.core.base.AppObject;
import group.habooba.core.base.Logger;
import group.habooba.core.domain.*;
import group.habooba.core.exceptions.*;
import group.habooba.core.repository.*;
import group.habooba.core.user.CourseAdmin;
import group.habooba.core.user.Student;
import group.habooba.core.user.User;

import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

import static group.habooba.core.base.Utils.asMap;
import static group.habooba.core.repository.Resources.readFromFile;
import static group.habooba.core.repository.Resources.writeToFile;

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
        return (String) activeUser.attributes().get("class");
    }


    /**
     * The fields below store all entities as HashMaps
     */
    private CourseRepository courseRepository;
    private UserRepository userRepository;
    private EnrollmentRepository enrollmentRepository;
    private RecoveryMilestoneRepository recoveryMilestoneRepository;
    private StudentAcademicsRepository studentAcademicsRepository;

    private Map<Long, Component> components;
    private Map<Long, Course> courses;
    private Map<Long, Enrollment> enrollments;
    private Map<Long, Student> students;
    private Map<Long, CourseAdmin> courseAdmins;
    private Map<Long, User> academicOfficers;
    private Map<Long, User> admins;
    private Map<Long, Policy> policies;
    private Map<Long, RecoveryMilestone> recoveryMilestones;
    private Map<Long, StudentAcademics> studentAcademics;
    private Engine policyEngine;

    private static Manifest manifest;

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

    public void loadRecoveryMilestones(String filePath) throws RepositoryFileNotFoundException {
        try {
            recoveryMilestoneRepository = new RecoveryMilestoneRepository(filePath);
            recoveryMilestoneRepository.load();
        } catch (FileNotFoundException e) {
            throw new RepositoryFileNotFoundException("Can't find a file for RecoveryMilestone repository.", e);
        }
        List<RecoveryMilestone> loadedMilestones = recoveryMilestoneRepository.dataAsList();
        this.recoveryMilestones = new HashMap<>();
        for(RecoveryMilestone milestone : loadedMilestones){
            this.recoveryMilestones.put(milestone.uid(), milestone);
        }
    }

    /**
     * Loads all StudentAcademics objects from file. Creates index: studentAcademics
     * @param filePath file to load data from
     */
    public void loadStudentAcademics(String filePath) throws RepositoryFileNotFoundException {
        try {
            studentAcademicsRepository = new StudentAcademicsRepository(filePath);
            studentAcademicsRepository.load();
        } catch (FileNotFoundException e) {
            throw new RepositoryFileNotFoundException("Can't find a file for StudentAcademics repository.", e);
        }
        List<StudentAcademics> loadedAcademics = studentAcademicsRepository.dataAsList();
        this.studentAcademics = new HashMap<>();
        for(StudentAcademics academics : loadedAcademics){
            this.studentAcademics.put(academics.uid(), academics);
        }
    }

    /**
     * Gets all existing courses
     * @return List of all courses
     */
    public List<Course> getAllCourses() {
        if (courses == null) {
            return new ArrayList<>();
        }
        return new ArrayList<>(courses.values());
    }

    public Course getCourseByUid(long courseUid) {
        return courses.get(courseUid);
    }

    /**
     * Gets all enrollments associated with a specific course UID
     * @param courseUid the UID of the course
     * @return List of enrollments for the specified course
     */
    public List<Enrollment> getEnrollmentsByCourseUid(long courseUid) {
        if (enrollments == null) {
            return new ArrayList<>();
        }
        return enrollments.values().stream()
                .filter(e -> e.course().uid() == courseUid)
                .collect(Collectors.toList());
    }

    /**
     * Gets all enrollments associated with a specific student UID
     * @param studentUid the UID of the student
     * @return List of enrollments for the specified student
     */
    public List<Enrollment> getEnrollmentsByStudentUid(long studentUid) {
        if (enrollments == null) {
            return new ArrayList<>();
        }
        return enrollments.values().stream()
                .filter(e -> e.studentUid() == studentUid)
                .collect(Collectors.toList());
    }

    /**
     * Loads and creates a singleton Manifest instance
     * @return the singleton Manifest instance
     */
    public static Manifest loadManifest() {
        if (manifest == null) {
            manifest = new Manifest();
            String manifestContent = readFromFile(Manifest.PATH);
            if (manifestContent != null && !manifestContent.isEmpty()) {
                try {
                    Map<String, Object> manifestData = asMap(TextParser.fromText(manifestContent));
                    // Load manifest data if needed
                    // For now, just create the instance
                } catch (Exception e) {
                    Logger.log("Could not parse manifest file: " + e.getMessage());
                }
            }
        }
        return manifest;
    }

    /**
     * Saves the Manifest to file
     * @return true if save was successful, false otherwise
     */
    public static boolean saveManifest() {
        if (manifest == null) {
            Logger.log("Manifest is null, cannot save.");
            return false;
        }

        Map<String, Object> manifestData = new HashMap<>();
        manifestData.put("version", Manifest.CURRENT_FILE_FORMAT_VERSION);
        manifestData.put("versionMajor", Manifest.CURRENT_FILE_FORMAT_VERSION_MAJOR);
        manifestData.put("versionMinor", Manifest.CURRENT_FILE_FORMAT_VERSION_MINOR);

        String manifestText = TextSerializer.toTextPretty(manifestData);
        return writeToFile(Manifest.PATH, manifestText);
    }

    /**
     * Constructs the RecoveryMilestone index
     */
    public void buildRecoveryMilestoneIndex() {
        if (recoveryMilestones == null) {
            recoveryMilestones = new HashMap<>();
        }
    }

    /**
     * Constructs the StudentAcademics index
     */
    public void buildStudentAcademicsIndex() {
        if (studentAcademics == null) {
            studentAcademics = new HashMap<>();
        }
    }

    /**
     * Links and replaces default objects with references to real objects from other indexes.
     * This method should be called after all indexes are loaded.
     */
    public void linkAllIndexes() {
        // Link enrollments with courses and students
        if (enrollments != null && courses != null && students != null) {
            for (Enrollment enrollment : enrollments.values()) {
                // Link course
                if (courses.containsKey(enrollment.course().uid())) {
                    enrollment.course(courses.get(enrollment.course().uid()));
                }
                // Link student
                if (students.containsKey(enrollment.studentUid())) {
                    Student student = students.get(enrollment.studentUid());
                    if(!student.academics().empty()) {
                        // Update student's academics with this enrollment if not already present
                        if (!student.academics().enrollments().contains(enrollment)) {
                            student.academics().enrollments().add(enrollment);
                        }
                    }
                }
            }
        }
        // Link recovery milestones with enrollments
        if (recoveryMilestones != null && enrollments != null) {
            for (RecoveryMilestone milestone : recoveryMilestones.values()) {
                if (enrollments.containsKey(milestone.enrollment().uid())) {
                    milestone.enrollment(enrollments.get(milestone.enrollment().uid()));
                }
            }
        }
        // Link student academics with enrollments
        if (studentAcademics != null && enrollments != null) {
            for (StudentAcademics academics : studentAcademics.values()) {
                List<Enrollment> linkedEnrollments = new ArrayList<>();
                for (Enrollment enrollment : academics.enrollments()) {
                    if (enrollments.containsKey(enrollment.uid())) {
                        linkedEnrollments.add(enrollments.get(enrollment.uid()));
                    }
                }
                academics.enrollments(linkedEnrollments);
            }
        }
    }

    /**
     * Constructs the working model by linking all indexes and creating missing StudentAcademics entries.
     * Should be called after all data is loaded.
     */
    public void constructWorkingModel() {
        // First, build the indexes
        buildRecoveryMilestoneIndex();
        buildStudentAcademicsIndex();
        // Link all indexes
        linkAllIndexes();
        // Check for missing StudentAcademics entries and create them
        if (students != null && enrollments != null && studentAcademics != null) {
            for (Student student : students.values()) {
                // If student doesn't have an entry in studentAcademics index
                if (!studentAcademics.containsKey(student.uid())) {
                    // Find the earliest enrollment for this student
                    List<Enrollment> studentEnrollments = getEnrollmentsByStudentUid(student.uid());
                    if (!studentEnrollments.isEmpty()) {
                        // Sort enrollments by timestamp to find the earliest
                        studentEnrollments.sort((e1, e2) -> {
                            if (e1.course().firstWeek() == null || e2.course().firstWeek() == null) {
                                return 0;
                            }
                            return e1.course().firstWeek().compareTo(e2.course().firstWeek());
                        });
                        Enrollment earliestEnrollment = studentEnrollments.get(0);
                        // Create new StudentAcademics from student and earliest enrollment
                        StudentAcademics newAcademics = new StudentAcademics(
                                student.uid(),
                                student.academics().program(),
                                student.academics().schoolOfStudy(),
                                earliestEnrollment.course().firstWeek() != null ?
                                        earliestEnrollment.course().firstWeek() : new StudyTimestamp(),
                                studentEnrollments,
                                new group.habooba.core.base.AttributeMap()
                        );
                        // Add to index
                        studentAcademics.put(student.uid(), newAcademics);
                        // Update student's academics reference
                        student.academics(newAcademics);
                    } else {
                        // No enrollments found, create empty academics
                        StudentAcademics newAcademics = new StudentAcademics(
                                student.uid(),
                                student.academics().program(),
                                student.academics().schoolOfStudy(),
                                student.academics().currentTimestamp(),
                                new ArrayList<>(),
                                new group.habooba.core.base.AttributeMap()
                        );
                        studentAcademics.put(student.uid(), newAcademics);
                        student.academics(newAcademics);
                    }
                } else {
                    // Link existing StudentAcademics to student
                    student.academics(studentAcademics.get(student.uid()));
                }
            }
            // Save the updated studentAcademics to repository
            if (studentAcademicsRepository != null) {
                List<StudentAcademics> academicsList = new ArrayList<>(studentAcademics.values());
                studentAcademicsRepository.updateDataFromList(academicsList);
                studentAcademicsRepository.save();
            }
        }
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
     * @throws UidOverflowException if assignment exceeds maxUid
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
            if (next > maxUid) throw new UidOverflowException("UID space exhausted while reassigning");
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
                .toList();
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

    /* --------- update enrollment student reference --------- */

    /**
     * Update enrollment's studentUid from oldStudentUid to newStudentUid.
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
    public boolean checkNoGapsInRecoveryMilestones() { return noGapsInMapKeys(this.recoveryMilestones); }
    public boolean checkNoGapsInStudentAcademics() { return noGapsInMapKeys(this.studentAcademics); }
    public boolean checkNoGapsInPolicies() { return noGapsInMapKeys(this.policies); }

    /* -------------- per-collection repair methods -------------- */

    /**
     * Repair students and update enrollments' studentUid references accordingly.
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
     * Repair all collections in the preferred order and save changes to repositories.
     * @return true if any repair or change was applied
     */
    public boolean repairAllAndSave() {
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
        // save back to repositories if changes occurred
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
