package group.habooba.core.repository;

import group.habooba.core.domain.RecoveryMilestone;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RecoveryMilestoneRepository extends Repository<RecoveryMilestone> {
    public RecoveryMilestoneRepository(String path) throws FileNotFoundException {
        super(path);
    }

    @Override
    public List<RecoveryMilestone> dataAsList() {
        var objects = data();
        List<RecoveryMilestone> list = new ArrayList<>();
        for(var obj : objects){
            list.add(RecoveryMilestone.fromMap(obj));
        }
        return list;
    }

    @Override
    public void updateDataFromList(List<RecoveryMilestone> data) {
        List<Map<String, Object>> objects = new ArrayList<>();
        for(var obj : data){
            objects.add(obj.toMap());
        }
        documentObjectModel.put("data", objects);
    }


}
