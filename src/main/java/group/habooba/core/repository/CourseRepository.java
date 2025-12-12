package group.habooba.core.repository;

import group.habooba.core.Logger;
import group.habooba.core.domain.Course;
import group.habooba.core.exceptions.IOException;
import group.habooba.core.user.User;

import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static group.habooba.core.Utils.asMap;
import static group.habooba.core.repository.Resources.readFromFile;
import static group.habooba.core.repository.Resources.writeToFile;

public class CourseRepository extends Repository<Course>{

    public CourseRepository(String path) throws FileNotFoundException {
        super(path);
    }

    @Override
    public List<Course> dataAsList(){
        var courseObjects = data();
        var courseList = new ArrayList<Course>();
        for(var course : courseObjects){
            courseList.add(Course.fromMap(course));
        }
        return courseList;
    }

    @Override
    public void updateDataFromList(List<Course> courses) {
        List<Map<String, Object>> courseObjects = new ArrayList<>();
        for(var course : courses){
            courseObjects.add(asMap(course));
        }
        documentObjectModel.put("data", courseObjects);
    }
}
