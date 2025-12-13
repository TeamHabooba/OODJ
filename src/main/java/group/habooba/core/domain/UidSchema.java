package group.habooba.core.domain;

import group.habooba.core.exceptions.InvalidUserUidException;
import group.habooba.core.exceptions.UidOverflowException;

/**
 * UID layout and helpers for the application.
 *
 * <p>
 * The 64-bit layout used by this class:
 * <pre>
 * [ byte 7 (entity) ] [ byte 6 (user-flag + subtype) ] [ 6 bytes local counter ]
 * </pre>
 * The highest (sign) bit is kept zero (all BASE values are &lt; 0x8000_0000_0000_0000L)
 * so simple numeric range checks with {@code >=} / {@code <=} work reliably.
 * </p>
 *
 * <p>All validators start by calling {@link #isValidUid(long)} which rejects 0 and negative values.</p>
 */
public final class UidSchema {

    // ============================
    // shifts / masks
    // ============================

    /**
     * Number of bits to shift for the entity (most significant) byte.
     */
    public static final int ENTITY_SHIFT = 56;

    /**
     * Number of bits to shift for the subtype (second-most-significant) byte.
     */
    public static final int SUBTYPE_SHIFT = 48;

    /**
     * Mask that covers the local counter (low 48 bits).
     */
    public static final long LOCAL_COUNTER_MASK = 0x0000_FFFF_FFFF_FFFFL;

    // ============================
    // Entity blocks (by descending priority)
    // COMPONENT_RESULTS -> ENROLLMENTS -> COMPONENTS -> COURSES -> PROFILES -> USERS -> POLICIES
    // ============================

    /**
     * Base for component-result UIDs (largest reserved space).
     */
    public static final long COMPONENT_RESULT_UID_BASE = 0x4000_0000_0000_0000L;

    /** Minimum valid component-result UID (we reserve 0 for invalid). */
    public static final long COMPONENT_RESULT_UID_MIN = COMPONENT_RESULT_UID_BASE + 1;

    /** Maximum valid component-result UID (Long.MAX_VALUE). */
    public static final long COMPONENT_RESULT_UID_MAX = 0x7fff_ffff_ffff_ffffL;

    /**
     * Base for enrollment UIDs (second-largest block).
     */
    public static final long ENROLLMENT_BASE = 0x2000_0000_0000_0000L;

    /** Minimum valid enrollment UID. */
    public static final long ENROLLMENT_MIN = ENROLLMENT_BASE + 1;

    /** Maximum valid enrollment UID (right below COMPONENT_RESULT block). */
    public static final long ENROLLMENT_MAX = COMPONENT_RESULT_UID_BASE - 1;

    /**
     * Base for component UIDs.
     */
    public static final long COMPONENT_BASE = 0x1000_0000_0000_0000L;

    /** Minimum valid component UID. */
    public static final long COMPONENT_MIN = COMPONENT_BASE + 1;

    /** Maximum valid component UID (just below ENROLLMENT block). */
    public static final long COMPONENT_MAX = ENROLLMENT_BASE - 1;

    /**
     * Base for course UIDs.
     */
    public static final long COURSE_BASE = 0x0800_0000_0000_0000L;

    /** Minimum valid course UID. */
    public static final long COURSE_MIN = COURSE_BASE + 1;

    /** Maximum valid course UID (just below COMPONENT block). */
    public static final long COURSE_MAX = COMPONENT_BASE - 1;

    /**
     * Base for profile UIDs (lightweight profile objects that also have UID).
     */
    public static final long PROFILE_BASE = 0x0400_0000_0000_0000L;

    /** Minimum valid profile UID. */
    public static final long PROFILE_MIN = PROFILE_BASE + 1;

    /** Maximum valid profile UID (just below COURSE block). */
    public static final long PROFILE_MAX = COURSE_BASE - 1;

    /**
     * Base for all user UIDs (the whole user block). Individual subtypes are encoded
     * in the second byte (bits 48..55).
     */
    public static final long USER_BASE = 0x0200_0000_0000_0000L;

    /** Minimum value in the overall user block. */
    public static final long USER_MIN = USER_BASE + 1;

    /** Maximum value in the overall user block (just below PROFILE block). */
    public static final long USER_MAX = PROFILE_BASE - 1;

    /**
     * Base for policy UIDs (smallest block).
     */
    public static final long POLICY_BASE = 0x0100_0000_0000_0000L;

    /** Minimum valid policy UID. */
    public static final long POLICY_MIN = POLICY_BASE + 1;

    /** Maximum valid policy UID (just below USER block). */
    public static final long POLICY_MAX = USER_BASE - 1;

    // ============================
    // User byte layout (second-most-significant byte)
    // ============================

    /**
     * USER flag byte value (in the second-most-significant byte).
     *
     * <p>High bit of the second byte is used as a USER flag (0x80). Checking
     * "(secondByte &amp; USER_FLAG_BYTE) != 0" tells if uid belongs to a user.</p>
     */
    public static final int USER_FLAG_BYTE = 0x80; // 1000_0000 in binary

    /** Student subtype bit inside the second byte. */
    public static final int STUDENT_SUBTYPE_BYTE = 0x01;

    /** Course admin subtype bit inside the second byte. */
    public static final int COURSE_ADMIN_SUBTYPE_BYTE = 0x02;

    /** Academic officer subtype bit inside the second byte. */
    public static final int ACADEMIC_OFFICER_SUBTYPE_BYTE = 0x04;

    /** Global admin subtype bit inside the second byte. */
    public static final int ADMIN_SUBTYPE_BYTE = 0x08;

    /**
     * Helper: combine a raw subtype byte value into a 64-bit value shifted into the second byte.
     *
     * @param b raw byte value (0..255)
     * @return raw value shifted to bits 48..55
     */
    private static long subtypeByteToLong(int b) {
        return ((long) (b & 0xFF)) << SUBTYPE_SHIFT;
    }

    // ============================
    // Per-subtype BASE / MIN / MAX (inside USER block)
    // ============================

    /** Base UID for students (USER block + user-flag + student-subtype). */
    public static final long STUDENT_UID_BASE = USER_BASE | subtypeByteToLong(USER_FLAG_BYTE | STUDENT_SUBTYPE_BYTE);

    /** Minimum valid student UID (first usable student id). */
    public static final long STUDENT_UID_MIN = STUDENT_UID_BASE + 1;

    /** Maximum valid student UID (base with all local counter bits set). */
    public static final long STUDENT_UID_MAX = STUDENT_UID_BASE | LOCAL_COUNTER_MASK;

    /** Base UID for course administrators. */
    public static final long COURSE_ADMIN_UID_BASE = USER_BASE | subtypeByteToLong(USER_FLAG_BYTE | COURSE_ADMIN_SUBTYPE_BYTE);

    /** Minimum valid course-admin UID. */
    public static final long COURSE_ADMIN_UID_MIN = COURSE_ADMIN_UID_BASE + 1;

    /** Maximum valid course-admin UID. */
    public static final long COURSE_ADMIN_UID_MAX = COURSE_ADMIN_UID_BASE | LOCAL_COUNTER_MASK;

    /** Base UID for academic officers. */
    public static final long ACADEMIC_OFFICER_UID_BASE = USER_BASE | subtypeByteToLong(USER_FLAG_BYTE | ACADEMIC_OFFICER_SUBTYPE_BYTE);

    /** Minimum valid academic-officer UID. */
    public static final long ACADEMIC_OFFICER_UID_MIN = ACADEMIC_OFFICER_UID_BASE + 1;

    /** Maximum valid academic-officer UID. */
    public static final long ACADEMIC_OFFICER_UID_MAX = ACADEMIC_OFFICER_UID_BASE | LOCAL_COUNTER_MASK;

    /** Base UID for global administrators. */
    public static final long ADMIN_UID_BASE = USER_BASE | subtypeByteToLong(USER_FLAG_BYTE | ADMIN_SUBTYPE_BYTE);

    /** Minimum valid admin UID. */
    public static final long ADMIN_UID_MIN = ADMIN_UID_BASE + 1;

    /** Maximum valid admin UID. */
    public static final long ADMIN_UID_MAX = ADMIN_UID_BASE | LOCAL_COUNTER_MASK;

    // ============================
    // Current counters
    // ============================

    /**
     * Current student UID counter.
     *
     * <p>Initialized to {@code STUDENT_UID_BASE + 1} so the first call to {@link #nextStudentUid()}
     * returns the first user-visible student UID.</p>
     */
    private static long currentStudentUid = STUDENT_UID_BASE + 1;

    /**
     * Current course-admin UID counter.
     *
     * <p>Initialized to {@code COURSE_ADMIN_UID_BASE + 1} so the first call to {@link #nextCourseAdminUid()}
     * returns the first user-visible course-admin UID.</p>
     */
    private static long currentCourseAdminUid = COURSE_ADMIN_UID_BASE + 1;

    /**
     * Current academic-officer UID counter.
     */
    private static long currentAcademicOfficerUid = ACADEMIC_OFFICER_UID_BASE + 1;

    /**
     * Current admin UID counter.
     */
    private static long currentAdminUid = ADMIN_UID_BASE + 1;

    /**
     * Current component UID counter.
     *
     * <p>Initialized to {@code COMPONENT_BASE + 1} so generated component UIDs fall into component block.</p>
     */
    private static long currentComponentUid = COMPONENT_BASE + 1;

    /**
     * Current enrollment UID counter.
     */
    private static long currentEnrollmentUid = ENROLLMENT_BASE + 1;

    // ============================
    // Validators
    // ============================

    /**
     * Basic validity check for any UID.
     *
     * @param uid the UID to check
     * @return {@code true} if uid &gt; 0 (zero and negatives are considered invalid), {@code false} otherwise
     */
    public static boolean isValidUid(long uid) {
        return uid > 0L;
    }

    /**
     * Validate component-result UID.
     *
     * <p>This method first calls {@link #isValidUid(long)} then checks range
     * [COMPONENT_RESULT_UID_MIN, COMPONENT_RESULT_UID_MAX].</p>
     *
     * @param uid the UID to validate
     * @return {@code true} if uid is within the component-result range, {@code false} otherwise
     */
    public static boolean isValidComponentResultUid(long uid) {
        if (!isValidUid(uid)) return false;
        return uid >= COMPONENT_RESULT_UID_MIN && uid <= COMPONENT_RESULT_UID_MAX;
    }

    /**
     * Validate enrollment UID (range check).
     *
     * @param uid the UID to validate
     * @return {@code true} if uid belongs to [ENROLLMENT_MIN, ENROLLMENT_MAX], {@code false} otherwise
     */
    public static boolean isValidEnrollmentUid(long uid) {
        if (!isValidUid(uid)) return false;
        return uid >= ENROLLMENT_MIN && uid <= ENROLLMENT_MAX;
    }

    /**
     * Validate component UID (range check).
     *
     * @param uid the UID to validate
     * @return {@code true} if uid belongs to [COMPONENT_MIN, COMPONENT_MAX], {@code false} otherwise
     */
    public static boolean isValidComponentUid(long uid) {
        if (!isValidUid(uid)) return false;
        return uid >= COMPONENT_MIN && uid <= COMPONENT_MAX;
    }

    /**
     * Validate course UID (range check).
     *
     * @param uid the UID to validate
     * @return {@code true} if uid belongs to [COURSE_MIN, COURSE_MAX], {@code false} otherwise
     */
    public static boolean isValidCourseUid(long uid) {
        if (!isValidUid(uid)) return false;
        return uid >= COURSE_MIN && uid <= COURSE_MAX;
    }

    /**
     * Validate profile UID (range check).
     *
     * @param uid the UID to validate
     * @return {@code true} if uid belongs to [PROFILE_MIN, PROFILE_MAX], {@code false} otherwise
     */
    public static boolean isValidProfileUid(long uid) {
        if (!isValidUid(uid)) return false;
        return uid >= PROFILE_MIN && uid <= PROFILE_MAX;
    }

    /**
     * Validate policy UID (range check).
     *
     * @param uid the UID to validate
     * @return {@code true} if uid belongs to [POLICY_MIN, POLICY_MAX], {@code false} otherwise
     */
    public static boolean isValidPolicyUid(long uid) {
        if (!isValidUid(uid)) return false;
        return uid >= POLICY_MIN && uid <= POLICY_MAX;
    }

    // ============================
    // User / subtype checks
    // ============================

    /**
     * Validate that {@code uid} belongs to the overall USER block and has the user flag set.
     *
     * <p>Checks in order:
     * <ol>
     *   <li>{@link #isValidUid(long)}</li>
     *   <li>uid is within [USER_MIN, USER_MAX]</li>
     *   <li>the USER flag bit is set in the second byte</li>
     * </ol>
     * </p>
     *
     * @param uid the UID to validate
     * @return {@code true} if uid is in user block and has USER flag, {@code false} otherwise
     */
    public static boolean isValidUserUid(long uid) {
        if (!isValidUid(uid)) return false;
        if (uid < USER_MIN || uid > USER_MAX) return false;

        int secondByte = (int) ((uid >> SUBTYPE_SHIFT) & 0xFF);
        return (secondByte & USER_FLAG_BYTE) != 0;
    }

    /**
     * Internal helper to check that a uid belongs to the USER block, falls within the subtype MIN..MAX
     * and that both USER_FLAG and the subtype-bit are present.
     *
     * @param uid the UID to check
     * @param subtypeByte the subtype byte to require (STUDENT_SUBTYPE_BYTE etc.)
     * @return {@code true} if all checks pass
     */
    private static boolean checkUserSubtype(long uid, int subtypeByte) {
        if (!isValidUid(uid)) return false;
        if (uid < USER_MIN || uid > USER_MAX) return false;

        int secondByte = (int) ((uid >> SUBTYPE_SHIFT) & 0xFF);
        int required = USER_FLAG_BYTE | subtypeByte;
        return (secondByte & required) == required;
    }

    /**
     * Validate student UID.
     *
     * <p>Checks {@link #isValidUid(long)}, verifies uid is within [STUDENT_UID_MIN, STUDENT_UID_MAX],
     * and ensures both USER flag and student-subtype bit are set.</p>
     *
     * @param uid the UID to validate
     * @return {@code true} if uid is a valid student uid, {@code false} otherwise
     */
    public static boolean isValidStudentUid(long uid) {
        if (!isValidUid(uid)) return false;
        if (uid < STUDENT_UID_MIN || uid > STUDENT_UID_MAX) return false;
        return checkUserSubtype(uid, STUDENT_SUBTYPE_BYTE);
    }

    /**
     * Validate course-admin UID.
     *
     * @param uid the UID to validate
     * @return {@code true} if uid is a valid course-admin uid, {@code false} otherwise
     */
    public static boolean isValidCourseAdminUid(long uid) {
        if (!isValidUid(uid)) return false;
        if (uid < COURSE_ADMIN_UID_MIN || uid > COURSE_ADMIN_UID_MAX) return false;
        return checkUserSubtype(uid, COURSE_ADMIN_SUBTYPE_BYTE);
    }

    /**
     * Validate academic-officer UID.
     *
     * @param uid the UID to validate
     * @return {@code true} if uid is a valid academic-officer uid, {@code false} otherwise
     */
    public static boolean isValidAcademicOfficerUid(long uid) {
        if (!isValidUid(uid)) return false;
        if (uid < ACADEMIC_OFFICER_UID_MIN || uid > ACADEMIC_OFFICER_UID_MAX) return false;
        return checkUserSubtype(uid, ACADEMIC_OFFICER_SUBTYPE_BYTE);
    }

    /**
     * Validate global admin UID.
     *
     * @param uid the UID to validate
     * @return {@code true} if uid is a valid admin uid, {@code false} otherwise
     */
    public static boolean isValidAdminUid(long uid) {
        if (!isValidUid(uid)) return false;
        if (uid < ADMIN_UID_MIN || uid > ADMIN_UID_MAX) return false;
        return checkUserSubtype(uid, ADMIN_SUBTYPE_BYTE);
    }

    // ============================
    // Next-UID generators and getters (non-thread-safe)
    // ============================

    /**
     * Generate the next student UID (increments internal counter).
     *
     * @return the next valid student UID
     * @throws UidOverflowException if generated value exceeds {@link #STUDENT_UID_MAX}
     */
    public static long nextStudentUid() {
        long next = ++currentStudentUid;
        if (next > STUDENT_UID_MAX) {
            throw new UidOverflowException("student UID counter overflow");
        }
        return next;
    }

    /**
     * Get the current student UID counter value.
     *
     * @return current student UID counter
     */
    public static long getCurrentStudentUid() {
        return currentStudentUid;
    }

    /**
     * Generate the next course-admin UID.
     *
     * @return next valid course-admin UID
     * @throws UidOverflowException if generated value exceeds {@link #COURSE_ADMIN_UID_MAX}
     */
    public static long nextCourseAdminUid() {
        long next = ++currentCourseAdminUid;
        if (next > COURSE_ADMIN_UID_MAX) {
            throw new UidOverflowException("course-admin UID counter overflow");
        }
        return next;
    }

    /**
     * Get the current course-admin UID counter value.
     *
     * @return current course-admin UID counter
     */
    public static long getCurrentCourseAdminUid() {
        return currentCourseAdminUid;
    }

    /**
     * Generate the next academic-officer UID.
     *
     * @return next valid academic-officer UID
     * @throws UidOverflowException if generated value exceeds {@link #ACADEMIC_OFFICER_UID_MAX}
     */
    public static long nextAcademicOfficerUid() {
        long next = ++currentAcademicOfficerUid;
        if (next > ACADEMIC_OFFICER_UID_MAX) {
            throw new UidOverflowException("academic-officer UID counter overflow");
        }
        return next;
    }

    /**
     * Get the current academic-officer UID counter value.
     *
     * @return current academic-officer UID counter
     */
    public static long getCurrentAcademicOfficerUid() {
        return currentAcademicOfficerUid;
    }

    /**
     * Generate the next admin UID.
     *
     * @return next valid admin UID
     * @throws UidOverflowException if generated value exceeds {@link #ADMIN_UID_MAX}
     */
    public static long nextAdminUid() {
        long next = ++currentAdminUid;
        if (next > ADMIN_UID_MAX) {
            throw new UidOverflowException("admin UID counter overflow");
        }
        return next;
    }

    /**
     * Get the current admin UID counter value.
     *
     * @return current admin UID counter
     */
    public static long getCurrentAdminUid() {
        return currentAdminUid;
    }

    /**
     * Generate the next component UID.
     *
     * @return next valid component UID
     * @throws UidOverflowException if generated value exceeds {@link #COMPONENT_MAX}
     */
    public static long nextComponentUid() {
        long next = ++currentComponentUid;
        if (next > COMPONENT_MAX) {
            throw new UidOverflowException("component UID counter overflow");
        }
        return next;
    }

    /**
     * Get the current component UID counter value.
     *
     * @return current component UID counter
     */
    public static long getCurrentComponentUid() {
        return currentComponentUid;
    }

    /**
     * Generate the next enrollment UID.
     *
     * @return next valid enrollment UID
     * @throws UidOverflowException if generated value exceeds {@link #ENROLLMENT_MAX}
     */
    public static long nextEnrollmentUid() {
        long next = ++currentEnrollmentUid;
        if (next > ENROLLMENT_MAX) {
            throw new UidOverflowException("enrollment UID counter overflow");
        }
        return next;
    }

    /**
     * Get the current enrollment UID counter value.
     *
     * @return current enrollment UID counter
     */
    public static long getCurrentEnrollmentUid() {
        return currentEnrollmentUid;
    }

    // ============================
    // Utility class ==> no instances
    // ============================

    /**
     * Private constructor to prevent instantiation.
     */
    private UidSchema() {
        throw new AssertionError("no instances");
    }
}
