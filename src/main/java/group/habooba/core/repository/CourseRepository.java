package group.habooba.core.repository;

import group.habooba.core.domain.Course;
import group.habooba.core.user.User;

import java.util.ArrayList;
import java.util.List;

public class CourseRepository implements Repository<Course>{
    // Not implemented
    @Override
    public ArrayList<Course> load(){
        return new ArrayList<Course>();
    }

    //Not implemented
    @Override
    public void save(Course object){

    }

    //Not implemented
    @Override
    public void save(List<Course> objects){

    }
}
