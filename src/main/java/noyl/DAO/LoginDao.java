package noyl.DAO;

import noyl.DatabaseConnection.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginDao {

    public static boolean authentication(String phoneNumber, String password){
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM user WHERE PhoneNumber = ? AND pwd = ?")) {

            preparedStatement.setString(1, phoneNumber);
            preparedStatement.setString(2, password);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next(); // If there is a matching user, return true
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
