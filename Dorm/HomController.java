import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class HomController {

    @FXML
    private Button btnHomLogin;

    @FXML
    private Button btnhomSignUp;

    @FXML
    void HomLogin(ActionEvent event) {
        // Load the sceneController FXML
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("SceneFXML.fxml")); // Adjust the path as necessary
            Parent root = loader.load();

            // Create a new stage for the login scene
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Login");
            stage.show();

            // Close the current window (optional)
            ((Stage) btnHomLogin.getScene().getWindow()).close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void HomSignUp(ActionEvent event) {
        // Load the RegisterController FXML
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("RegisterFXML.fxml")); // Adjust the path as necessary
            Parent root = loader.load();

            // Create a new stage for the registration scene
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Registration");
            stage.show();

            // Close the current window (optional)
            ((Stage) btnhomSignUp.getScene().getWindow()).close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
