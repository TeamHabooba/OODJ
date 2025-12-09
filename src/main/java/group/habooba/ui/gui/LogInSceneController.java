package group.habooba.ui.gui;

import group.habooba.core.domain.RecoveryMilestone;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class LogInSceneController {
    @FXML
    private TextField loginUidTextField;

    public String retSth(String s){
        String s1 = loginUidTextField.getText();
        loginUidTextField.setText(s);
        return s1;
    }

    @FXML
    private void handleLogInButtonClick() {
    }
}
