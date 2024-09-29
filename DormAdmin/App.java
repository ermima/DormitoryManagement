import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage primarStage) {
        if (DatabaseConnection.canConnect()) {
            System.out.println("Database connection successful!");
        } else {
            System.out.println("Failed to connect to the database.");
        }

        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("AdminLoginFXML.fxml"));
            Scene scene = new Scene(root);

            primarStage.setTitle("BIT dormitory");
            primarStage.setScene(scene);
            primarStage.show();
        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        launch(args);

    }
}
