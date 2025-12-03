package group.habooba.core.repository;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class Resources {
    public static InputStream getResourceAsStream(String path, boolean useClassLoader){
        if (useClassLoader)
            return Resources.class.getClassLoader().getResourceAsStream(path);
        else
            return Resources.class.getResourceAsStream(path);
    }

    public static InputStream getResourceAsStream(String path){
        return getResourceAsStream(path,true);
    }

    public static BufferedReader getResourceAsBufferedReader(InputStream inputStream){
        return new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
    }

    public static ArrayList<String> readResourceLines(BufferedReader reader){
        return reader.lines().collect(Collectors.toCollection(ArrayList::new));
    }

    public static ArrayList<String> readResourceLines(String path, boolean useClassLoader){
        return readResourceLines(getResourceAsBufferedReader(getResourceAsStream(path,useClassLoader)));
    }

    public static ArrayList<String> readResourceLines(String path){
        return readResourceLines(path,true);
    }
}
