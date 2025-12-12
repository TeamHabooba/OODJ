package group.habooba.core.repository;

import group.habooba.core.domain.Course;
import group.habooba.core.user.User;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserRepository extends Repository<User> {

    public UserRepository(String path) throws FileNotFoundException {
        super(path);
    }

    @Override
    public List<User> dataAsList(){
        var userObjects = data();
        var userList = new ArrayList<User>();
        for(var user : userObjects){
            userList.add(User.fromMap(user));
        }
        return userList;
    }

    @Override
    public void updateDataFromList(List<User> users){
        List<Map<String, Object>> userObjects = new ArrayList<>();
        for(var user : users){
            userObjects.add(user.toMap());
        }
        documentObjectModel.put("data", userObjects);
    }
}
