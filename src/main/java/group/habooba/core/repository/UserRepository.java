package group.habooba.core.repository;

import group.habooba.core.user.User;

import java.util.ArrayList;
import java.util.List;

public class UserRepository implements Repository<User>{
    // Not implemented
    @Override
    public ArrayList<User> load(){
        return new ArrayList<User>();
    }


    //Not implemented
    @Override
    public void save(List<User> objects){

    }
}
