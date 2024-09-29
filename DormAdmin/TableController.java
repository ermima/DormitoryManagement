import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TableController {

    @FXML
    private TableColumn<?, ?> bldCol;

    @FXML
    private Button brnAdd;

    @FXML
    private Button btnDelet;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<?, ?> idcol;

    @FXML
    private TableColumn<?, ?> roomNCol;

    @FXML
    private TableView<?> table;

    @FXML
    private TextField txtBuildingName;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtRoomNumber;

    @FXML
    private TextField txtRoomNumber1;

    @FXML
    void Add(ActionEvent event) {
        String id = txtId.getText();
        String buildingName = txtBuildingName.getText();
        String roomNumber = txtRoomNumber.getText();
        String studentId = txtRoomNumber1.getText();

        String sql = "INSERT INTO computing (id, building_name, room_number, student_id) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            pstmt.setString(2, buildingName);
            pstmt.setString(3, roomNumber);
            pstmt.setString(4, studentId);
            pstmt.executeUpdate();
            System.out.println("Record added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void Delete(ActionEvent event) {
        String id = txtId.getText();

        String sql = "DELETE FROM  WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            pstmt.executeUpdate();
            System.out.println("Record deleted successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void Update(ActionEvent event) {
        String id = txtId.getText();
        String buildingName = txtBuildingName.getText();
        String roomNumber = txtRoomNumber.getText();
        String studentId = txtRoomNumber1.getText();

        String sql = "UPDATE building SET building_name = ?, room_number = ?, student_id = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, buildingName);
            pstmt.setString(2, roomNumber);
            pstmt.setString(3, studentId);
            pstmt.setString(4, id);
            pstmt.executeUpdate();
            System.out.println("Record updated successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
