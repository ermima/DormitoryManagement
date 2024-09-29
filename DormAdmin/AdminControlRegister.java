import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AdminControlRegister {

    @FXML
    private Button btnSignUpAdmin;

    @FXML
    private Hyperlink hyperLoginAdmin;

    @FXML
    private TextField txtEmailAdmin;

    @FXML
    private PasswordField txtPassAdmin;

    @FXML
    private TextField txtUserAdmin;

    @FXML
    void AdminSignUp(ActionEvent event) {
        String username = txtUserAdmin.getText();
        String password = txtPassAdmin.getText();
        String email = txtEmailAdmin.getText();

        // Validate input
        if (username.isEmpty() || password.isEmpty() || email.isEmpty()) {
            showAlert("Error", "All fields must be filled out.");
            return;
        }

        String query = "INSERT INTO admin (username, password, email) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.connect();
                PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.setString(3, email);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                showAlert("Success", "Admin account created successfully!");
            } else {
                showAlert("Error", "Failed to create admin account.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Database error: " + e.getMessage());
        }
    }

    @FXML
    void AlreadyAdminHaveAnAcount(ActionEvent event) {
        try {
            // Load the Admin Login FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminLoginFXML.fxml"));
            Scene scene = new Scene(loader.load());

            // Get the current stage and set the new scene
            Stage stage = (Stage) hyperLoginAdmin.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Admin Login"); // Set the title of the new window
            stage.show(); // Show the new scene
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load the login page: " + e.getMessage());
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
