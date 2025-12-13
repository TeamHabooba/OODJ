package group.habooba.core.repository;

import group.habooba.core.base.Logger;
import group.habooba.core.domain.TextSerializable;

import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static group.habooba.core.base.Utils.asMap;
import static group.habooba.core.repository.Resources.readFromFile;
import static group.habooba.core.repository.Resources.writeToFile;

public abstract class Repository<T extends TextSerializable> implements IRepository<T> {
    protected String path;
    protected Map<String, Object> documentObjectModel;

    /**
     * Loads data from file and saves inside the repository as Map
     * @return true if successful, otherwise - false
     */
    @Override
    public boolean load(){
        if(loaded()) return true;
        documentObjectModel = new HashMap<>();
        String pathContent = readFromFile(path);
        documentObjectModel = asMap(TextParser.fromText(pathContent));
        if(!documentValid()){
            reset();
            return false;
        }
        return true;
    }

    /**
     * Saves data from repository Map representation back to text file
     */
    @Override
    public void save() {
        String stringified = TextSerializer.toTextPretty(documentObjectModel);
        if(!fileExists())
            Logger.log("File " + path + " does not exist. Creating new file...");
        if(!writeToFile(path, stringified))
            Logger.log("Could not save to file: " + path + ".");
    }

    public Repository(String path) throws FileNotFoundException {
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

    @Override
    public boolean documentValid(){
        if(documentObjectModel == null) return false;
        if(documentObjectModel.isEmpty()) return false;
        if(!documentObjectModel.containsKey("meta")) return false;
        if(!documentObjectModel.containsKey("data")) return false;
        return true;
    }

    @Override
    public Map<String, Object> meta(){
        return asMap(documentObjectModel.get("meta"));
    }

    @Override
    public List<Map<String, Object>> data(){
        return (List<Map<String, Object>>)documentObjectModel.get("data");
    }

    @Override
    public abstract List<T> dataAsList();

    @Override
    public abstract void updateDataFromList(List<T> data);

    @Override
    public String path(){
        return path;
    }

    @Override
    public Map<String, Object> documentObjectModel(){
        return documentObjectModel;
    }
}
