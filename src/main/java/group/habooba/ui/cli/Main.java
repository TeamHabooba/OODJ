package group.habooba.ui.cli;

import group.habooba.core.Core;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class Main {

    public static void main(String[] args) {
        // Set output encoding to UTF-8
        System.setOut(new PrintStream(new FileOutputStream(java.io.FileDescriptor.out), true, StandardCharsets.UTF_8));

        System.out.println("\u001B[33m========START==========\u001B[0m");

        Core core = Core.init("data/courses.txt", "data/users.txt");

        System.out.println("\u001B[33m=========END==========\u001B[0m");
    }
}
