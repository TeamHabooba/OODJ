package group.habooba.ui.gui;

import group.habooba.core.Core;
import group.habooba.core.Logger;
import group.habooba.core.exceptions.NullValueException;
import group.habooba.core.user.User;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

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
        User user = new User();
        try{
            user.uid(Long.parseLong(loginUidTextField.getText()));
            user.password(loginPasswordTextField.getText().trim());
            if(core == null)
                throw new NullValueException("Core instance is not set.");
            core.tryAuthenticate(user);
        } catch (Exception e){
            Logger.log(e.toString());
        }
    }
}
