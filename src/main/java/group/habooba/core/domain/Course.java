package group.habooba.core.domain;

import group.habooba.core.auth.AttributeMap;
import group.habooba.core.auth.PolicyResource;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public record Course(long uid, String name, ProgramType program, SchoolOfStudy school, long adminUid, List<Component> components, AttributeMap attributes) implements PolicyResource {

    /**
     * Tries to find component with the specified uid. Returns empty Optional if failed
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
}