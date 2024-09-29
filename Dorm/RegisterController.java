import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RegisterController {

    @FXML
    private Button btnSignUp;

    @FXML
    private Button btnBackToLogin; // Back to login button

    @FXML
    private PasswordField pass;

    @FXML
    private TextField txtUser;

    @FXML
    private TextField txtEmail;

    @FXML
    TextField txtId;

    @FXML
    void SignUp(ActionEvent event) {
        String username = txtUser.getText();
        String password = pass.getText();
        String email = txtEmail.getText();
        String id = txtId.getText();
        // Simple validaton
        if (username.isEmpty() || password.isEmpty() || email.isEmpty() || id.isEmpty()) {
            showAlert("Error", "Fields cannot be empty.");
            return;
        }
        /*
         * Add logics to save user data
         * for example saving to database or file
         * 
         */
        boolean registrationSuccess = registerUser(username, password, email, id);

        if (registrationSuccess) {
            showAlert("Success", "Registration successful!");
            // clear the fields after registration .... this is optional
            txtUser.clear();
            pass.clear();
        } else {
            showAlert("Error", "Registration field. please try agian.");
        }
    }
    /*
     * private boolean registerUser(String username, String password) {
     * return true;
     * }
     */

    private boolean registerUser(String username, String password, String email, String id) {
        // Use the DatabaseConnection class to get a connection
        try (Connection conn = DatabaseConnection.connect()) {
            if (conn == null) {
                System.out.println("Failed to establish a database connection.");
                return false; // Connection failed
            }

            // Update the SQL query to match your database structure
            String query = "INSERT INTO computing (id, name, password, email) VALUES (?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setString(1, id); // Set the ID
                pstmt.setString(2, username); // Set the name (username)
                pstmt.setString(3, password); // Set the password
                pstmt.setString(4, email); // Set the email
                int rowsAffected = pstmt.executeUpdate();

                return rowsAffected > 0; // Return true if the insert was successful
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Log the exception for debugging
            return false; // Return false if there was an error
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
    void goBackToLogin(ActionEvent event) {
        try {
            // Load the login form
            FXMLLoader loader = new FXMLLoader(getClass().getResource("SceneFXML.fxml"));
            Parent root = loader.load();

            // create new stage for login window
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Login");
            stage.show();

            // Close the current registration window-----optional

            ((Stage) btnBackToLogin.getScene().getWindow()).close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}