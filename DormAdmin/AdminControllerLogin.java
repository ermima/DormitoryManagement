import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
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
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminControllerLogin {

    @FXML
    private Hyperlink AdminhyperSignUp;

    @FXML
    private Button btnAdminSignIn;

    @FXML
    private PasswordField txtPassAdmin;

    @FXML
    private TextField txtUserAdmin;

    @FXML
    void AdminSignIn(ActionEvent event) throws IOException {
        String username = txtUserAdmin.getText();
        String password = txtPassAdmin.getText();

        // Validate input
        if (username.isEmpty() || password.isEmpty()) {
            showAlert("Error", "Username and password must be filled out.");
            return;
        }

        String query = "SELECT * FROM admin WHERE username = ? AND password = ?";

        try (Connection conn = DatabaseConnection.connect();
                PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {

                // Here you can redirect to the main admin dashboard or another page
                Parent root = FXMLLoader.load(getClass().getResource("TableFXML.fxml"));

                // Get the current stage (window)
                Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();

                // Set the new scene
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.setTitle("Table View"); // Optional: Set the title of the new window
                stage.show(); // Show the new scene
                // Successful login
                showAlert("Success", "Login successful! Welcome, " + username + "!");
            } else {
                // Invalid credentials
                showAlert("Error", "Invalid username or password.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Database error: " + e.getMessage());
        }
    }

    @FXML
    void handleHyperSignUp(ActionEvent event) {
        try {
            // Load the Admin Registration FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminRegistrationFXML.fxml"));
            Scene scene = new Scene(loader.load());

            // Get the current stage and set the new scene
            Stage stage = (Stage) AdminhyperSignUp.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Admin Registration"); // Set the title of the new window
            stage.show(); // Show the new scene
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load the registration page: " + e.getMessage());
        }
    }

    @FXML

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
