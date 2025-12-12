package group.habooba.core.repository;

import group.habooba.core.domain.Enrollment;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EnrollmentRepository extends Repository<Enrollment> {

    public EnrollmentRepository(String path) throws FileNotFoundException {
        super(path);
    }

    @Override
    public List<Enrollment> dataAsList(){
        var enrollmentObjects = data();
        var enrollmentList = new ArrayList<Enrollment>();
        for(var enrollment : enrollmentObjects){
            enrollmentList.add(Enrollment.fromMap(enrollment));
        }
        return enrollmentList;
    }

    @Override
    public void updateDataFromList(List<Enrollment> enrollments){
        List<Map<String, Object>> enrollmentObjects = new ArrayList<>();
        for(var enrollment : enrollments){
            enrollmentObjects.add(enrollment.toMap());
        }
        documentObjectModel.put("data", enrollmentObjects);
    }
}
