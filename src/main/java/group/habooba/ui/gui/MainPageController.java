package group.habooba.ui.gui;

import group.habooba.core.Core;
import group.habooba.core.user.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class MainPageController {

    @FXML private Label lbl_uid;
    @FXML private Label lbl_firstname;
    @FXML private Label lbl_lastname;
    @FXML private Label lbl_email;

    @FXML private Button btn_change_password;
    @FXML private Button btn_edit_profile;
    @FXML private Button btn_logout;

    @FXML private AnchorPane dynamic_content;

    private Core core;
    private User activeUser;

    public void init(Core core) {
        this.core = core;
        this.activeUser = core.activeUser();
        setUserInfo();
        configureButtons();
        loadLayoutByRole();
    }

    private void setUserInfo() {
        lbl_uid.setText(String.valueOf(activeUser.uid()));
        lbl_firstname.setText(activeUser.profile().firstName());
        lbl_lastname.setText(activeUser.profile().lastName());
        lbl_email.setText(activeUser.email());
    }

    private void configureButtons() {
        boolean isAdmin = core.activeUserClass().equals("admin");
        btn_edit_profile.setVisible(isAdmin);
        btn_edit_profile.setManaged(isAdmin);
    }

    private void loadLayoutByRole() {
        try {
            String layoutPath = switch (core.activeUserClass()) {
                case "student" -> "/fxml/StudentLayout.fxml";
                case "courseAdmin" -> "/fxml/CourseAdminLayout.fxml";
                case "academicOfficer" -> "/fxml/AcademicOfficerLayout.fxml";
                case "admin" -> "/fxml/AdminLayout.fxml";
                default -> null;
            };
            if (layoutPath == null) {
                showError("Error", "Unknown user role: " + core.activeUserClass());
                return;
            }
            FXMLLoader loader = new FXMLLoader(getClass().getResource(layoutPath));
            Parent layout = loader.load();
            Object controller = loader.getController();
            controller.getClass()
                    .getMethod("init", Core.class)
                    .invoke(controller, core);
            dynamic_content.getChildren().setAll(layout);
            AnchorPane.setTopAnchor(layout, 0.0);
            AnchorPane.setBottomAnchor(layout, 0.0);
            AnchorPane.setLeftAnchor(layout, 0.0);
            AnchorPane.setRightAnchor(layout, 0.0);
        } catch (Exception e) {
            e.printStackTrace();
            showError("Error Loading Layout",
                    "Failed to load user interface: " + e.getMessage());
        }
    }

    @FXML
    private void handleChangePassword() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ChangePasswordPage.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("OODJ Assignment (Password)");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showError("Error", "Could not open Change Password page: " + e.getMessage());
        }
    }

    @FXML
    private void handleEditProfile() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/EditProfilePage.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("OODJ Assignment (Profile)");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showError("Error", "Could not open Edit Profile page: " + e.getMessage());
        }
    }

    @FXML
    private void handleLogOut() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Log Out");
        alert.setHeaderText("Are you sure you want to log out?");
        alert.setContentText("You will be returned to the login screen.");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/LogInPage.fxml"));
                Parent root = loader.load();
                Stage stage = (Stage) btn_logout.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.setTitle("OODJ Assignment - Login");
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
                showError("Error", "Could not return to login page: " + e.getMessage());
            }
        }
    }

    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}