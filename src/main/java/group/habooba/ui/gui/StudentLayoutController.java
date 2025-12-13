package group.habooba.ui.gui;

import group.habooba.core.Core;
import group.habooba.core.domain.Component;
import group.habooba.core.domain.Course;
import group.habooba.core.domain.Enrollment;
import group.habooba.core.user.Student;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StudentLayoutController {

    @FXML private ComboBox<String> yearComboBox;
    @FXML private CheckBox filterActive;
    @FXML private CheckBox filterFailed;
    @FXML private CheckBox filterPassed;
    @FXML private Button btnDetails;
    @FXML private TableView<EnrollmentRow> coursesTable;
    @FXML private TableColumn<EnrollmentRow, String> colCourseName;
    @FXML private TableColumn<EnrollmentRow, String> colStatus;
    @FXML private TableColumn<EnrollmentRow, Double> colCurrentGPA;
    @FXML private TableColumn<EnrollmentRow, Double> colRequiredGPA;

    private Core core;
    private Student currentStudent;
    private ObservableList<EnrollmentRow> allEnrollments;

    @FXML
    public void initialize() {
        setupTableColumns();
        setupListeners();
    }

    public void init(Core core) {
        this.core = core;
        this.currentStudent = (Student) core.activeUser();
        loadData();
    }

    private void setupTableColumns() {
        colCourseName.setCellValueFactory(cellData -> cellData.getValue().courseNameProperty());
        colStatus.setCellValueFactory(cellData -> cellData.getValue().statusProperty());
        colCurrentGPA.setCellValueFactory(cellData -> cellData.getValue().currentGPAProperty().asObject());
        colRequiredGPA.setCellValueFactory(cellData -> cellData.getValue().requiredGPAProperty().asObject());

        coursesTable.setRowFactory(tv -> new TableRow<EnrollmentRow>() {
            @Override
            protected void updateItem(EnrollmentRow item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setStyle("");
                } else if (item.getStatus().equalsIgnoreCase("Failed")) {
                    setStyle("-fx-background-color: #ffcccc;");
                    setTextFill(Color.RED);
                } else {
                    setStyle("");
                }
            }
        });
    }

    private void setupListeners() {
        coursesTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            btnDetails.setDisable(newSelection == null);
        });

        filterActive.selectedProperty().addListener((obs, oldVal, newVal) -> applyFilters());
        filterFailed.selectedProperty().addListener((obs, oldVal, newVal) -> applyFilters());
        filterPassed.selectedProperty().addListener((obs, oldVal, newVal) -> applyFilters());

        yearComboBox.setOnAction(e -> applyFilters());
    }

    private void loadData() {
        if (core == null || currentStudent == null) {
            return;
        }

        allEnrollments = FXCollections.observableArrayList();

        int successCount = 0;
        int failedCount = 0;

        // Get all enrollments and filter by student UID
        for (Enrollment enrollment : core.getActiveStudentEnrollments()) {
            try {
                // Get course by UID from enrollment
                long courseUid = enrollment.course().uid();
                Course course = core.getCourseByUid(courseUid);

                // Validation: Check if course exists
                if (course == null) {
                    System.err.println("Warning: Enrollment " + enrollment.uid() + " has null course (UID: " + courseUid + "). Skipping.");
                    failedCount++;
                    continue;
                }

                // Validation: Check if course has valid data
                if (course.name() == null || course.name().isBlank()) {
                    System.err.println("Warning: Course " + course.uid() + " has invalid name. Skipping enrollment.");
                    failedCount++;
                    continue;
                }

                // Validation: Check if enrollment can calculate grade without exceptions
                boolean canCalculateGrade = validateEnrollmentComponents(enrollment, course);

                if (canCalculateGrade) {
                    EnrollmentRow row = new EnrollmentRow(enrollment, course, core);
                    allEnrollments.add(row);
                    successCount++;
                } else {
                    System.err.println("Warning: Enrollment " + enrollment.uid() + " has invalid components. Skipping.");
                    failedCount++;
                }

            } catch (Exception e) {
                System.err.println("Error loading enrollment " + enrollment.uid() + ": " + e.getMessage());
                failedCount++;
            }
        }

        // Log summary
        System.out.println("Successfully loaded " + successCount + " enrollments.");
        if (failedCount > 0) {
            System.out.println("Skipped " + failedCount + " invalid enrollments.");
        }

        setupYearComboBox();
        applyFilters();
    }

    /**
     * Temporary validation to check if enrollment components are valid
     * This prevents InvalidValueException when calculating grades
     */
    private boolean validateEnrollmentComponents(Enrollment enrollment, Course course) {
        try {
            // Check if enrollment has results
            if (enrollment.results() == null || enrollment.results().isEmpty()) {
                // Empty results is valid - just means no grades yet
                return true;
            }

            // Validate each component result
            for (var result : enrollment.results()) {
                long componentUid = result.componentUid();

                // Check if component exists in course
                var componentOpt = course.componentByUid(componentUid);

                if (componentOpt.isEmpty()) {
                    // Component not found in course - invalid data
                    System.err.println("  -> Component " + componentUid + " not found in course " + course.uid());
                    return false;
                }
            }

            // All components are valid - try to calculate grade to be sure
            double grade = enrollment.currentGrade();

            // If we got here without exception, it's valid
            return true;

        } catch (Exception e) {
            // Any exception means invalid data
            System.err.println("  -> Validation failed: " + e.getMessage());
            return false;
        }
    }

    private void setupYearComboBox() {
        yearComboBox.getItems().clear();

        int maxYear = 4;

        for (int i = 1; i <= maxYear; i++) {
            yearComboBox.getItems().add("Year " + i);
        }
        yearComboBox.getItems().add("All time");

        yearComboBox.getSelectionModel().select("All time");
    }

    private void applyFilters() {
        if (allEnrollments == null) {
            return;
        }

        List<EnrollmentRow> filtered = new ArrayList<>();
        String selectedYear = yearComboBox.getValue();

        for (EnrollmentRow row : allEnrollments) {
            if (selectedYear != null && !selectedYear.equals("All time")) {
                // TODO: Add year filtering logic based on enrollment timestamp
            }

            String status = row.getStatus();
            boolean includeRow = false;

            if (filterActive.isSelected() && status.equalsIgnoreCase("Active")) {
                includeRow = true;
            }
            if (filterFailed.isSelected() && status.equalsIgnoreCase("Failed")) {
                includeRow = true;
            }
            if (filterPassed.isSelected() && status.equalsIgnoreCase("Passed")) {
                includeRow = true;
            }

            if (!filterActive.isSelected() && !filterFailed.isSelected() && !filterPassed.isSelected()) {
                includeRow = true;
            }

            if (includeRow) {
                filtered.add(row);
            }
        }

        coursesTable.setItems(FXCollections.observableArrayList(filtered));
    }

    @FXML
    private void handleDetails(ActionEvent event) {
        EnrollmentRow selected = coursesTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/CoursePage.fxml"));
            Parent root = loader.load();

            // CoursePageController controller = loader.getController();
            // controller.init(core);
            // controller.setCourse(selected.getCourse());

            Stage stage = new Stage();
            stage.setTitle("Course");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            showError("Error", "Could not open Course page: " + e.getMessage());
        }
    }

    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static class EnrollmentRow {
        private final SimpleStringProperty courseName;
        private final SimpleStringProperty status;
        private final SimpleDoubleProperty currentGPA;
        private final SimpleDoubleProperty requiredGPA;
        private final Enrollment enrollment;
        private final Course course;

        public EnrollmentRow(Enrollment enrollment, Course course, Core core) {
            this.enrollment = enrollment;
            this.course = course;
            this.courseName = new SimpleStringProperty(course.name());

            this.status = new SimpleStringProperty(calculateStatus(enrollment, course));

            // Safely get current grade with validation
            double currentGrade = safelyGetCurrentGrade(enrollment);
            this.currentGPA = new SimpleDoubleProperty(currentGrade);

            // Get required GPA from enrollment
            double requiredGPA = enrollment.requiredGrade();
            this.requiredGPA = new SimpleDoubleProperty(requiredGPA);
        }

        /**
         * Safely get current grade, returning 0.0 if calculation fails
         */
        private double safelyGetCurrentGrade(Enrollment enrollment) {
            try {
                return enrollment.currentGrade();
            } catch (Exception e) {
                System.err.println("Warning: Could not calculate grade for enrollment " + enrollment.uid() + ". Using 0.0");
                return 0.0;
            }
        }

        private String calculateStatus(Enrollment enrollment, Course course) {
            try {
                double current = safelyGetCurrentGrade(enrollment);
                double required = enrollment.requiredGrade();

                if (current >= required) {
                    return "Passed";
                } else if (hasActiveRecoveryMilestone(enrollment)) {
                    return "Active";
                } else if (hasFailedAllRecoveryAttempts(enrollment)) {
                    return "Failed";
                } else {
                    return "Active";
                }
            } catch (Exception e) {
                // If we can't determine status, default to Active
                return "Active";
            }
        }

        private boolean hasActiveRecoveryMilestone(Enrollment enrollment) {
            // TODO: Check if enrollment has active recovery milestone
            // Check enrollment.attributes() for recovery milestone data
            return false;
        }

        private boolean hasFailedAllRecoveryAttempts(Enrollment enrollment) {
            // TODO: Check if enrollment has 2 failed recovery milestones
            // Check enrollment.attributes() for recovery milestone data
            return false;
        }

        public SimpleStringProperty courseNameProperty() { return courseName; }
        public SimpleStringProperty statusProperty() { return status; }
        public SimpleDoubleProperty currentGPAProperty() { return currentGPA; }
        public SimpleDoubleProperty requiredGPAProperty() { return requiredGPA; }

        public String getCourseName() { return courseName.get(); }
        public String getStatus() { return status.get(); }
        public double getCurrentGPA() { return currentGPA.get(); }
        public double getRequiredGPA() { return requiredGPA.get(); }
        public Course getCourse() { return course; }
        public Enrollment getEnrollment() { return enrollment; }
    }
}