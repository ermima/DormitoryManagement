import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RoomController {

    @FXML
    private Button btnLogOut;

    @FXML
    private Hyperlink hyperlink;

    @FXML
    private Label labelWelcome;

    @FXML
    private TextField studentIdTextField; // TextField for user input

    // Method to set the welcome message
    public void setWelcomeMessage(String username) {
        labelWelcome.setText("Welcome, " + username + "!");
    }

    @FXML
    void handleRoomHyperlink(ActionEvent event) {
        // Fetch building and room information based on input from TextField
        fetchBuildingAndRoomInfo();
    }

    private void fetchBuildingAndRoomInfo() {
        String studentIdInput = studentIdTextField.getText(); // Get the input from TextField

        if (studentIdInput.isEmpty()) {
            showAlert("Error", "Please enter your Student ID.");
            return;
        }

        String query = "SELECT building_name, room_number FROM building WHERE student_id = ?";

        try (Connection conn = DatabaseConnection.connect();
                PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, studentIdInput); // Set the student_id as String
            ResultSet rs = pstmt.executeQuery();

            StringBuilder info = new StringBuilder();
            while (rs.next()) {
                String buildingName = rs.getString("building_name");
                String roomNumber = rs.getString("room_number");
                info.append("Building: ").append(buildingName)
                        .append(", Room Number: ").append(roomNumber).append("\n");
            }

            if (info.length() == 0) {
                info.append("No data found for the provided Student ID.");
            }

            // Display the information in an alert dialog
            showAlert("Building and Room Information", info.toString());

        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to retrieve data: " + e.getMessage());
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    void LogOutHandller(ActionEvent event) {
        try {
            // Load the Home Page FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("HomPageFXML.fxml"));
            Scene scene = new Scene(loader.load());

            // Get the current stage and set the new scene
            Stage stage = (Stage) btnLogOut.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Home Page"); // Set the title of the new window
            stage.show(); // Show the new scene
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load the home page: " + e.getMessage());
        }
    }
}
