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

public class CourseRepository implements Repository{
    private final String path;
    private Map<String, Object> documentObjectModel;

    // Not implemented
    @Override
    public void load(){
        if(loaded()) return;
        documentObjectModel = new HashMap<>();
        String pathContent = readFromFile(path);
        documentObjectModel = asMap(TextParser.fromText(pathContent));
    }

    //Not implemented
    @Override
    public void save() {
        String stringified = TextSerializer.toTextPretty(documentObjectModel);
        if(!fileExists())
            Logger.log("File " + path + " does not exist. Creating new file...");
        if(!writeToFile(path, stringified))
            Logger.log("Could not save to file: " + path + ".");
    }

    public CourseRepository(String path) throws FileNotFoundException {
        this.path = path;
        this.documentObjectModel = null;
        if(!fileExists())
            throw new FileNotFoundException("Can't create a repository: file does not exist.");
    }

    @Override
    public void reset(){
        this.documentObjectModel = null;
    }

    @Override
    public boolean loaded(){
        return documentObjectModel != null;
    }

    @Override
    public boolean fileExists(){
        return Files.exists(Paths.get(path));
    }

    public Map<String, Object> meta(){
        return asMap(documentObjectModel.get("meta"));
    }

    public List<Map<String, Object>> data(){
        return (List<Map<String, Object>>)documentObjectModel.get("data");
    }

    public String path(){
        return path;
    }

    public Map<String, Object> documentObjectModel(){
        return documentObjectModel;
    }
}
