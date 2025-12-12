package group.habooba.ui.gui;


import group.habooba.core.Core;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Core core = Core.init("data/courses.txt", "data/users.txt");

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
        launch(args);
    }
}
