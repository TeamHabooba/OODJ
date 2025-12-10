package group.habooba.core.domain;

import group.habooba.core.auth.AttributeMap;
import group.habooba.core.auth.PolicyResource;
import group.habooba.core.repository.TextSerializer;

import java.util.*;
import java.util.stream.Collectors;

public final class Course implements PolicyResource, TextSerializable {
    private final long uid;
    private String name;
    private ProgramType program;
    private SchoolOfStudy school;
    private ArrayList<Component> components;
    private final AttributeMap attributes;

    public Course(long uid, String name, ProgramType program, SchoolOfStudy school, ArrayList<Component> components, AttributeMap attributes) {
        this.uid = uid;
        this.name = name;
        this.program = program;
        this.school = school;
        this.components = components;
        this.attributes = attributes;
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

    @Override
    public String policyResourceType() {
        return "course";
    }

    @Override
    public long policyId() {
        return uid;
    }

    public long uid() {
        return uid;
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

    @Override
    public AttributeMap attributes() {
        return attributes;
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
                Objects.equals(this.attributes, that.attributes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uid, name, program, school, components, attributes);
    }

    /**
     * NOT for txt serialization.
     * @return
     */
    @Override
    public String toString() {
        return "Course[" +
                "uid=" + uid + ", " +
                "name=" + name + ", " +
                "program=" + program + ", " +
                "school=" + school + ", " +
                "components=" + components + ", " +
                "attributes=" + attributes + ']';
    }

    public HashMap<String, Object> toMap() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("uid", uid);
        map.put("name", name);
        map.put("program", program);
        map.put("school", school);
        map.put("components", components);
        map.put("attributes", attributes.toMap());
        return map;
    }

    public String toText(){
        return TextSerializer.toTextPretty(this.toMap());
    }

    public static Course fromMapOld(HashMap<String, Object> map){
        ProgramType program = ProgramType.valueOf((String) map.get("program"));
        SchoolOfStudy school = SchoolOfStudy.valueOf((String) map.get("school"));
        ArrayList<Component> components = new ArrayList<>();
        for (var uid : (ArrayList<Long>)map.get("components")){
            components.add(new Component(uid));
        }
        return new Course((long)map.get("uid"), (String)map.get("name"), program, school, components, AttributeMap.fromMap((Map<String, Object>)map.get("attributes")));
    }

    public static Course fromMapNew(HashMap<String, Object> map){
        ProgramType program = ProgramType.valueOf((String) map.get("program"));
        SchoolOfStudy school = SchoolOfStudy.valueOf((String) map.get("school"));
        ArrayList<Component> components = new ArrayList<>();
        for (var s : (ArrayList<HashMap<String, Object>>)map.get("components")){
            components.add(Component.fromMap(s));
        }
        return new Course((long)map.get("uid"), (String)map.get("name"), program, school, components, AttributeMap.fromMap((Map<String, Object>)map.get("attributes")));
    }
}