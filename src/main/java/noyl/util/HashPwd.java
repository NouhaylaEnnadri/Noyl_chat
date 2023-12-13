package noyl.util;


import org.mindrot.jbcrypt.BCrypt;

public class HashPwd {
    HashPwd(){

    }
    //generating salt and hashing the password
    public static String hashPassword(String plainPassword) {
        // Generate a random salt
        String salt = BCrypt.gensalt();

        // Hash the password with the salt
        return BCrypt.hashpw(plainPassword, salt);
    }

    //verifying the password
    public static boolean verifyPasswordWithSalt(String plainPassword, String hashedPassword) {
        // Use BCrypt to check if the provided password and salt match the hashed password
        return BCrypt.checkpw(plainPassword , hashedPassword);
    }


}