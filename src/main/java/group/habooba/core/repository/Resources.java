package group.habooba.core.repository;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

    public static String readResource(String path, boolean useClassLoader){
        return readResourceLines(path, useClassLoader).stream().reduce("", (a, b) -> a + '\n' + b);
    }

    public static String readResource(String path){
        return readResource(path,true);
    }


    public static boolean writeToFile(String path, String content){
        Path p =  Paths.get(path);
        try (BufferedWriter writer = Files.newBufferedWriter(p, StandardCharsets.UTF_8)) {
            writer.write(content);
            return true;
        } catch (IOException e) {
            throw new RuntimeException("Failed to open a file.", e);
        }
    }

    public static String readFromFile(String path){
        Path p =  Paths.get(path);
        try(BufferedReader reader = Files.newBufferedReader(p, StandardCharsets.UTF_8)){
            return reader.lines().collect(Collectors.joining("\n"));
        } catch (IOException e) {
            throw new RuntimeException("Failed to open a file.", e);
        }
    }

}
