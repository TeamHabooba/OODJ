package group.habooba.ui.gui;

import group.habooba.core.Core;
import group.habooba.core.base.Logger;
import group.habooba.core.exceptions.NullValueException;
import group.habooba.core.user.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LogInSceneController {

    private Core core = null;

    public void setCore(Core core) {
        this.core = core;
    }

    @FXML
    private TextField loginUidTextField;

    @FXML
    private TextField loginPasswordTextField;

    @FXML
    private void handleLogInButtonClick() {
        try {
            User user = new User();
            user.uid(Long.parseLong(loginUidTextField.getText()));
            user.password(loginPasswordTextField.getText().trim());

            if (core == null)
                throw new NullValueException("Core instance is not set.");

            core.tryAuthenticate(user);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainPage.fxml"));
            Parent root = loader.load();

            MainPageController controller = loader.getController();
            controller.init(core);

            Stage stage = (Stage) loginUidTextField.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception e) {
            Logger.log(e.toString());
        }
    }
}
