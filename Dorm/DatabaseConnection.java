import java.sql.*;
import com.mysql.cj.x.protobuf.MysqlxDatatypes.Scalar.String;

public class DatabaseConnection {
    private static final java.lang.String URL = "jdbc:mysql://localhost:3306/students";
    private static final java.lang.String USER = "root"; // Default username for XAMPP
    private static final java.lang.String PASSWORD = ""; // Default password is empty

    public static Connection connect() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connection Established!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static boolean canConnect() {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            return connection != null; // If connection is not null, it's successful
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Connection failed
        }
    }

}
