package Connectivity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static Connection connection;

    public static Connection connection() {

        if (connection == null) {
            try {
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/student_registration_system ", "ssfs", "123456");
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return connection;
        } else {
            return connection;
        }

    }
}
