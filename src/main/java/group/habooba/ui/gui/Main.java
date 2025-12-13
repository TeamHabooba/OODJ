package group.habooba.ui.gui;

import group.habooba.core.Core;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.FileOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Core core = Core.init("data/courses.txt", "data/users.txt", "data/enrollments.txt");
        core.repairAllAndSave();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/LogInScene.fxml"));
        Parent root = fxmlLoader.load();

        LogInSceneController controller = fxmlLoader.getController();
        controller.setCore(core);

        Scene scene = new Scene(root);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Log In");
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        // Set console output encoding to UTF-8
        System.setOut(new PrintStream(new FileOutputStream(java.io.FileDescriptor.out), true, StandardCharsets.UTF_8));
        launch(args);
    }
}
