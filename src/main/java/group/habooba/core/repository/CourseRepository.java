package group.habooba.core.repository;

import group.habooba.core.domain.Course;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static group.habooba.core.base.Utils.asMap;

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
            courseObjects.add(course.toMap());
        }
        documentObjectModel.put("data", courseObjects);
    }
}
