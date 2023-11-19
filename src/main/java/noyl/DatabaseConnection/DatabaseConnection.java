package noyl.DatabaseConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

   private static String url = "jdbc:mysql://localhost:3306/noyl_chat";
   private static  String username = "root";
   private static String password = "2002";
    DatabaseConnection(){


        try {
            // Establish the database connection
            Connection connection = DriverManager.getConnection(url,username,password);

            // Print a success message
            System.out.println("Database connection established successfully!");

            // Close the connection
            connection.close();
        } catch (SQLException e) {
            // Print the stack trace in case of an exception
            e.printStackTrace();
        }
    }
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url,username , password);
    }
}
