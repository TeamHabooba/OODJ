package group.habooba.core;

import group.habooba.core.domain.Component;
import group.habooba.core.domain.Course;
import group.habooba.core.exceptions.InvalidValueException;
import group.habooba.core.repository.TextParser;
import group.habooba.core.repository.TextSerializer;

import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static group.habooba.core.repository.Resources.readFromFile;
import static group.habooba.core.repository.Resources.readResource;

public class Utils {
    public static boolean almostEqual(double a, double b, double eps) {
        double diff = Math.abs(a - b);
        if (diff <= eps) return true;

        return diff <= Math.max(Math.abs(a), Math.abs(b)) * eps;
    }

    public static Map<String, Object> asMap(Object object) {
        return (Map<String, Object>)object;
    }


    /**
     * Helper method for reformatCourses()
     */
    private static void updateComponents(ArrayList<Component> firstList, ArrayList<Component> secondList) {
        Map<Long, Component> secondListMap = secondList.stream()
                .collect(Collectors.toMap(Component::uid, Function.identity()));

        for (int i = 0; i < firstList.size(); i++) {
            long uid = firstList.get(i).uid();
            if (secondListMap.containsKey(uid)) {
                firstList.set(i, secondListMap.get(uid));
            }
        }
    }

    /**
     *
     * Method to fix the old format of files to avoid regenerating hundreds of entries.
     */
    private static void reformatCourses(){
        String oldCourseString = readResource("data/courses.txt");
        var courseFileObject = asMap(TextParser.fromText(oldCourseString));
        var oldCoursedata = (ArrayList<HashMap<String, Object>>)courseFileObject.get("data");
        ArrayList<Course> oldObjectCourses = oldCoursedata.stream().map(c -> Course.fromMapOld(c)).collect(Collectors.toCollection(ArrayList::new));

        String componentString = readResource("data/components.txt");
        var componentFileObject = asMap(TextParser.fromText(componentString));
        var componentData = (ArrayList<HashMap<String, Object>>)componentFileObject.get("data");
        ArrayList<Component> objectComponents = componentData.stream().map(c -> Component.fromMap(c)).collect(Collectors.toCollection(ArrayList::new));

        for (var course : oldObjectCourses) {
            updateComponents(course.components(),  objectComponents);
        }

        String toFile = TextSerializer.toTextPretty(oldObjectCourses);

        Path exportPath = Paths.get("src/main/resources/data/componentNew.txt");
        try {
            Files.writeString(exportPath, toFile, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new InvalidValueException("Could not write componentNew.txt", e);
        }
    }

    private static void testCourseIO(){
        ArrayList<Course> courses = new ArrayList<>();
        String coursesString = readFromFile("data/courses.txt");
        Map<String, Object> courseFileObject = asMap(TextParser.fromText(coursesString));
        var courseData = (ArrayList<HashMap<String, Object>>)courseFileObject.get("data");
        for (var course : courseData) {
            courses.add(Course.fromMap(course));
        }
        ArrayList<String> coursesStr = courses.stream().map(TextSerializer::toText).collect(Collectors.toCollection(ArrayList::new));
        System.out.println(coursesStr.stream().reduce("", (acc, cur) -> acc + "\n" + cur ));
    }

    public static Object deepCopy(Object value) {
        if (value == null) {
            return null;
        }
        // Copyable
        if(value instanceof Copyable<?> copyable){
            return copyable.copy();
        }
        // Immutable types
        if (isImmutable(value)) {
            return value;
        }
        // Map
        if (value instanceof Map<?, ?> map) {
            Map<Object, Object> newMap = new HashMap<>();
            for (Map.Entry<?, ?> e : map.entrySet()) {
                newMap.put(deepCopy(e.getKey()), deepCopy(e.getValue()));
            }
            return newMap;
        }
        // List
        if (value instanceof List<?> list) {
            List<Object> newList = new ArrayList<>(list.size());
            for (Object item : list) {
                newList.add(deepCopy(item));
            }
            return newList;
        }
        // Set
        if (value instanceof Set<?> set) {
            Set<Object> newSet = new HashSet<>();
            for (Object item : set) {
                newSet.add(deepCopy(item));
            }
            return newSet;
        }
        // Array
        if (value.getClass().isArray()) {
            int length = Array.getLength(value);
            Object newArray = Array.newInstance(
                    value.getClass().getComponentType(), length
            );
            for (int i = 0; i < length; i++) {
                Array.set(newArray, i, deepCopy(Array.get(value, i)));
            }
            return newArray;
        }
        // TODO: Create custom exception
        throw new IllegalArgumentException(
                "Unsupported type for deep copy: " + value.getClass()
        );
    }

    private static boolean isImmutable(Object obj) {
        return obj instanceof String
                || obj instanceof Number
                || obj instanceof Boolean
                || obj instanceof Character
                || obj instanceof Enum<?>
                || obj.getClass().getPackageName().startsWith("java.time")
                || obj.getClass().getPackageName().startsWith("java.math");
    }
}
