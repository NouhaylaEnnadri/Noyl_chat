package noyl.DAO;

import noyl.DatabaseConnection.DatabaseConnection;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RegisterDao {

    public static boolean register(Long phoneNumber, String name, String password, String vPassword, File picture) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "INSERT INTO user (PhoneNumber, name, pwd, vPwd, picture) VALUES (?, ?, ?, ?, ?)")) {

            preparedStatement.setLong(1, phoneNumber);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, password);
            preparedStatement.setString(4, vPassword);
            // Convert image to byte array
            byte[] imageData = Files.readAllBytes(picture.toPath());

            // Set the byte array to the BLOB column
            preparedStatement.setBytes(5, imageData);

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0; // If rows are affected, return true

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
