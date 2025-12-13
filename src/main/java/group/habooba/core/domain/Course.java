package group.habooba.core.domain;

import group.habooba.core.base.AppObject;
import group.habooba.core.base.AttributeMap;
import group.habooba.core.repository.TextParser;
import group.habooba.core.repository.TextSerializer;

import java.util.*;
import java.util.stream.Collectors;

import static group.habooba.core.base.Utils.asMap;
import static group.habooba.core.base.Utils.deepCopy;

public final class Course extends AppObject<Course> {
    private String name;
    private ProgramType program;
    private SchoolOfStudy school;
    private ArrayList<Component> components;
    private StudyTimestamp firstWeek;
    private StudyTimestamp lastWeek;

    public Course(long uid) {
        this(uid, "", ProgramType.DEGREE, SchoolOfStudy.NONE, new ArrayList<>(), new AttributeMap(), null, null);
    }

    public Course(long uid, String name, ProgramType program, SchoolOfStudy school,
                  ArrayList<Component> components, AttributeMap attributes) {
        this(uid, name, program, school, components, attributes, null, null);
    }

    public Course(long uid, String name, ProgramType program, SchoolOfStudy school,
                  ArrayList<Component> components, AttributeMap attributes,
                  StudyTimestamp firstWeek, StudyTimestamp lastWeek) {
        this.uid = uid;
        this.name = name;
        this.program = program;
        this.school = school;
        this.components = components;
        this.attributes = attributes;

        // Set default values if firstWeek or lastWeek are null or empty
        if (firstWeek == null || firstWeek.empty()) {
            // First week of first semester, first year (year=1, semester=1, week=1)
            this.firstWeek = new StudyTimestamp(1, 1, 1);
        } else {
            this.firstWeek = firstWeek;
        }

        if (lastWeek == null || lastWeek.empty()) {
            // Last week of second semester, first year (year=1, semester=2, week=12)
            // Assuming 12 weeks per semester as a reasonable default
            this.lastWeek = new StudyTimestamp(1, 2, 12);
        } else {
            this.lastWeek = lastWeek;
        }
    }

    public Course(Course other) {
        this.uid = other.uid;
        this.name = other.name;
        this.program = other.program;
        this.school = other.school;
        this.components = (ArrayList<Component>) deepCopy(other.components);
        this.attributes = other.attributes.copy();
        this.firstWeek = other.firstWeek != null ? new StudyTimestamp(other.firstWeek) : new StudyTimestamp(1, 1, 1);
        this.lastWeek = other.lastWeek != null ? new StudyTimestamp(other.lastWeek) : new StudyTimestamp(1, 2, 12);
    }

    /**
     * Tries to find component with the specified uid. Returns empty Optional if failed
     *
     * @param uid desired uid
     * @return Optional with Component if successful, otherwise - empty Optional
     */
    public Optional<Component> componentByUid(long uid) {
        return components.stream()
                .filter(component -> component.uid() == uid)
                .findFirst();
    }

    /**
     * Returns all Course Components as map
     *
     * @return Map of Components
     */
    public Map<String, Component> componentsMap() {
        return components.stream()
                .collect(Collectors.toMap(Component::name, component -> component));
    }

    public long uid() {
        return uid;
    }

    public void uid(long uid) {
        this.uid = uid;
    }

    public String name() {
        return name;
    }

    public void name(String name) {
        this.name = name;
    }

    public ProgramType program() {
        return program;
    }

    public void program(ProgramType program) {
        this.program = program;
    }

    public SchoolOfStudy school() {
        return school;
    }

    public void school(SchoolOfStudy school) {
        this.school = school;
    }

    public ArrayList<Component> components() {
        return components;
    }

    public void components(ArrayList<Component> components) {
        this.components = components;
    }

    public StudyTimestamp firstWeek() {
        return firstWeek;
    }

    public void firstWeek(StudyTimestamp firstWeek) {
        if (firstWeek == null || firstWeek.empty()) {
            this.firstWeek = new StudyTimestamp(1, 1, 1);
        } else {
            this.firstWeek = firstWeek;
        }
    }

    public StudyTimestamp lastWeek() {
        return lastWeek;
    }

    public void lastWeek(StudyTimestamp lastWeek) {
        if (lastWeek == null || lastWeek.empty()) {
            this.lastWeek = new StudyTimestamp(1, 2, 12);
        } else {
            this.lastWeek = lastWeek;
        }
    }

    public boolean empty() {
        return uid == 0 || name.isBlank() || school == SchoolOfStudy.NONE && program == ProgramType.DEGREE;
    }

    @Override
    public AttributeMap attributes() {
        return attributes;
    }

    @Override
    public void attributes(AttributeMap attributes) {
        this.attributes = attributes;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Course) obj;
        return this.uid == that.uid &&
                Objects.equals(this.name, that.name) &&
                Objects.equals(this.program, that.program) &&
                Objects.equals(this.school, that.school) &&
                Objects.equals(this.components, that.components) &&
                Objects.equals(this.attributes, that.attributes) &&
                Objects.equals(this.firstWeek, that.firstWeek) &&
                Objects.equals(this.lastWeek, that.lastWeek);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uid, name, program, school, components, attributes, firstWeek, lastWeek);
    }

    /**
     * NOT for txt serialization. Autogenerated
     * @return Printable string representation of Course instance
     */
    @Override
    public String toString() {
        return "Course[" +
                "uid=" + uid + ", " +
                "name=" + name + ", " +
                "program=" + program + ", " +
                "school=" + school + ", " +
                "components=" + components + ", " +
                "attributes=" + attributes + ", " +
                "firstWeek=" + firstWeek + ", " +
                "lastWeek=" + lastWeek + ']';
    }

    @Override
    public Map<String, Object> toMap() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("uid", uid);
        map.put("name", name);
        map.put("program", program);
        map.put("school", school);
        map.put("components", components);
        map.put("attributes", attributes.toMap());
        map.put("firstWeek", firstWeek.toMap());
        map.put("lastWeek", lastWeek.toMap());
        return map;
    }

    @Override
    public String toText() {
        return TextSerializer.toTextPretty(this.toMap());
    }

    public static Course fromMap(Map<String, Object> map) {
        return fromMapNew(map);
    }

    public static Course fromText(String string) {
        return fromMap(asMap(TextParser.fromText(string)));
    }

    public static Course fromMapOld(Map<String, Object> map) {
        ProgramType program = ProgramType.valueOf((String) map.get("program"));
        SchoolOfStudy school = SchoolOfStudy.valueOf((String) map.get("school"));
        ArrayList<Component> components = new ArrayList<>();
        for (var uid : (List<Long>) map.get("components")) {
            components.add(new Component(uid));
        }

        // Handle firstWeek and lastWeek with defaults
        StudyTimestamp firstWeek = null;
        StudyTimestamp lastWeek = null;

        if (map.containsKey("firstWeek") && map.get("firstWeek") != null) {
            firstWeek = StudyTimestamp.fromMap(asMap(map.get("firstWeek")));
        }

        if (map.containsKey("lastWeek") && map.get("lastWeek") != null) {
            lastWeek = StudyTimestamp.fromMap(asMap(map.get("lastWeek")));
        }

        return new Course(
                (long) map.get("uid"),
                (String) map.get("name"),
                program,
                school,
                components,
                AttributeMap.fromMap(asMap(map.get("attributes"))),
                firstWeek,
                lastWeek
        );
    }

    public static Course fromMapNew(Map<String, Object> map) {
        ProgramType program = ProgramType.valueOf((String) map.get("program"));
        SchoolOfStudy school = SchoolOfStudy.valueOf((String) map.get("school"));
        ArrayList<Component> components = new ArrayList<>();
        for (var s : (List<Map<String, Object>>) map.get("components")) {
            components.add(Component.fromMap(s));
        }

        // Handle firstWeek and lastWeek with defaults
        StudyTimestamp firstWeek = null;
        StudyTimestamp lastWeek = null;

        if (map.containsKey("firstWeek") && map.get("firstWeek") != null) {
            try {
                firstWeek = StudyTimestamp.fromMap(asMap(map.get("firstWeek")));
                // Check if the timestamp is all zeros
                if (firstWeek.empty()) {
                    firstWeek = null; // Will be set to default in constructor
                }
            } catch (Exception e) {
                firstWeek = null; // Will be set to default in constructor
            }
        }

        if (map.containsKey("lastWeek") && map.get("lastWeek") != null) {
            try {
                lastWeek = StudyTimestamp.fromMap(asMap(map.get("lastWeek")));
                // Check if the timestamp is all zeros
                if (lastWeek.empty()) {
                    lastWeek = null; // Will be set to default in constructor
                }
            } catch (Exception e) {
                lastWeek = null; // Will be set to default in constructor
            }
        }

        return new Course(
                (long) map.get("uid"),
                (String) map.get("name"),
                program,
                school,
                components,
                AttributeMap.fromMap(asMap(map.get("attributes"))),
                firstWeek,
                lastWeek
        );
    }

    @Override
    public Course copy() {
        return new Course(this);
    }
}