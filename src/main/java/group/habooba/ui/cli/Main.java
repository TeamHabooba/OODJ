package group.habooba.ui.cli;

import group.habooba.core.repository.TextParser;
import group.habooba.core.repository.TextSerializer;

import java.io.*;
import java.nio.charset.StandardCharsets;

import static group.habooba.core.repository.Resources.readResourceLines;

public class Main {

    public static void main(String[] args) {
        // Set output encoding to UTF-8
        System.setOut(new PrintStream(new FileOutputStream(java.io.FileDescriptor.out), true, StandardCharsets.UTF_8));


        System.out.println("\u001B[33m========START==========\u001B[0m");

        System.out.println("\u001B[33m=========END==========\u001B[0m");
    }
}
