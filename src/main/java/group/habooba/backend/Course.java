package group.habooba.backend;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public record Course(long id, String name, ProgramType program, List<Component> components) {

    public Optional<Component> componentByName(String name) {
        return components.stream()
                .filter(component -> component.name().equals(name))
                .findFirst();
    }

    public Optional<Component> componentById(long id) {
        return components.stream()
                .filter(component -> component.id() == id)
                .findFirst();
    }

    public Map<String, Component> componentsMap() {
        return components.stream()
                .collect(Collectors.toMap(Component::name, component -> component));
    }
}