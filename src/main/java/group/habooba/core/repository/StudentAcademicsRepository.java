package group.habooba.core.repository;

import group.habooba.core.domain.StudentAcademics;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StudentAcademicsRepository extends Repository<StudentAcademics> {

    public StudentAcademicsRepository(String path) throws FileNotFoundException {
        super(path);
    }

    @Override
    public List<StudentAcademics> dataAsList() {
        var objects = data();
        List<StudentAcademics> list = new ArrayList<>();
        for (var obj : objects) {
            list.add(StudentAcademics.fromMap(obj));
        }
        return list;
    }

    @Override
    public void updateDataFromList(List<StudentAcademics> data) {
        List<Map<String, Object>> objects = new ArrayList<>();
        for (var obj : data) {
            objects.add(obj.toMap());
        }
        documentObjectModel.put("data", objects);
    }
}