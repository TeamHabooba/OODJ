package group.habooba.ui.cli;

import group.habooba.core.auth.AttributeMap;
import group.habooba.core.domain.Component;
import group.habooba.core.domain.Course;
import group.habooba.core.domain.ProgramType;
import group.habooba.core.domain.SchoolOfStudy;
import group.habooba.core.exceptions.InvalidValueException;
import group.habooba.core.repository.TextParser;
import group.habooba.core.repository.TextSerializer;
import group.habooba.core.user.User;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static group.habooba.core.repository.Resources.readResourceLines;

public class Main {

    public static void updateComponents(ArrayList<Component> firstList, ArrayList<Component> secondList) {
        Map<Long, Component> secondListMap = secondList.stream()
                .collect(Collectors.toMap(Component::uid, Function.identity()));

        for (int i = 0; i < firstList.size(); i++) {
            long uid = firstList.get(i).uid();
            if (secondListMap.containsKey(uid)) {
                firstList.set(i, secondListMap.get(uid));
            }
        }
    }

    public static void main(String[] args) {
        // Set output encoding to UTF-8
        System.setOut(new PrintStream(new FileOutputStream(java.io.FileDescriptor.out), true, StandardCharsets.UTF_8));

        System.out.println("\u001B[33m========START==========\u001B[0m");
        String oldCourseString = readResourceLines("data/courses.txt").stream().reduce("", (a, b) -> a + '\n' + b);
        HashMap<String, Object> courseFileObject = (HashMap<String, Object>)TextParser.fromText(oldCourseString);
        var oldCoursedata = (ArrayList<HashMap<String, Object>>)courseFileObject.get("data");
        ArrayList<Course> oldObjectCourses = oldCoursedata.stream().map(c -> Course.fromMapOld(c)).collect(Collectors.toCollection(ArrayList::new));

        String componentString = readResourceLines("data/components.txt").stream().reduce("", (a, b) -> a + '\n' + b);
        HashMap<String, Object> componentFileObject = (HashMap<String, Object>)TextParser.fromText(componentString);
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

        System.out.println("\u001B[33m=========END==========\u001B[0m");
    }
}
