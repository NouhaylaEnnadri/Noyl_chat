package noyl.DAO;

import noyl.DatabaseConnection.DatabaseConnection;
import noyl.util.HashPwd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginDao {

    public static boolean authentication(String phoneNumber, String password) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM user WHERE PhoneNumber = ?")) {

            preparedStatement.setString(1, phoneNumber);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    // Retrieve hashed password from the database
                    String hashedPassword = resultSet.getString("pwd");

                    // Check if the provided password matches the hashed password
                    return HashPwd.verifyPasswordWithSalt(password, hashedPassword);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
