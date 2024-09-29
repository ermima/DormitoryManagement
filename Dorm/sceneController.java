import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;

public class sceneController {

    @FXML
    private Button btnSignIn;

    @FXML
    private PasswordField pass;

    @FXML
    private TextField txtUser;

    @FXML

    void SignIn(ActionEvent event) {
        String username = txtUser.getText();
        String password = pass.getText();

        if (username.isEmpty() || password.isEmpty()) {
            showAlert("Error", "Username and password cannot be empty.");
        } else {
            if (checkLoginCredentials(username, password)) {
                openRoomPage(username); // Redirect to room page with the username
            } else {
                showAlert("Error", "Invalid username or password. Please try again.");
            }
        }
    }

    private boolean checkLoginCredentials(String username, String password) {
        String query = "SELECT * FROM computing WHERE name = ? AND password = ?";
        try (Connection conn = DatabaseConnection.connect();
                PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();

            return rs.next(); // If there is a result, the credentials are valid
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Return false if there was an error
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
        throw new UnsupportedOperationException("Unimplemented method 'showAlert'");
    }

    @FXML
    private Button btnSignUp;

    @FXML
    void initilize() {

        btnSignUp.setOnAction(event -> openRegistration());
    }

    private void openRegistration() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("RegisterFXML.fxml"));
            Parent root = loader.load();

            // create the new stage for the registration button

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Registration");
            stage.show();

            // close the current login window -- - --- This is optional
            ((Stage) btnSignUp.getScene().getWindow()).close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private Hyperlink hyperlinkSignIn;

    @FXML
    void handleCreateAccount() {
        try {
            // load the registration form
            FXMLLoader loader = new FXMLLoader(getClass().getResource("RegisterFXML.fxml"));
            Parent root = loader.load();

            // Create a new stage for the registration window

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("REgistration");
            stage.show();

            // Close the current login window
            ((Stage) hyperlinkSignIn.getScene().getWindow()).close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openRoomPage(String username) {
        try {
            // Load the RoomFXML.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("RoomFXML.fxml")); // Ensure the path is correct
            Parent root = loader.load();

            // Get the RoomController and set the welcome message
            RoomController roomController = loader.getController();
            roomController.setWelcomeMessage(username); // Pass the username to the room controller

            // Create a new stage for the Room page
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Room Page");
            stage.show();

            // Close the current login window
            Stage currentStage = (Stage) btnSignIn.getScene().getWindow();
            currentStage.close();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Failed to open the room page: " + e.getMessage());
        }
    }

}
