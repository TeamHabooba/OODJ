package group.habooba.ui.cli;

import group.habooba.core.Core;
import group.habooba.core.user.User;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class Main {

    public static void main(String[] args) {
        // Set output encoding to UTF-8
        System.setOut(new PrintStream(new FileOutputStream(java.io.FileDescriptor.out), true, StandardCharsets.UTF_8));

        System.out.println("\u001B[33m========START==========\u001B[0m");

        Core core = Core.init("data/courses.txt", "data/users.txt");

        User user = new User(351843720888328L, "043912");
        boolean result = false;
        try {
            core.tryAuthenticate(user);
            result = true;
        } catch (Exception e) {
            result = false;
            System.out.println(e.getMessage());
        }

        user = new User(351843720888328L, "111");
        try {
            core.tryAuthenticate(user);
            result = true;
        } catch (Exception e) {
            result = false;
            System.out.println(e.getMessage());
        }

        user = new User(50000, "123415");
        try {
            core.tryAuthenticate(user);
            result = true;
        } catch (Exception e) {
            result = false;
            System.out.println(e.getMessage());
        }

        System.out.println("\u001B[33m=========END==========\u001B[0m");
    }
}
