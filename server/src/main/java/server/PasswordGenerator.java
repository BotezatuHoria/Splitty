package server;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class PasswordGenerator {

    static Logger logger = LoggerFactory.getLogger(PasswordGenerator.class);

    private static String password;
    /**
     * Generates a new random password for the admin.
     */
    @PostConstruct
    void generatePassword(){
        String allChars = "abcdefghijklmnopqrstuvwxyz1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        password = "";
        while (password.length() < 8){
            Random random = new Random();
            int charIndex = random.nextInt(allChars.length());
            password += allChars.charAt(charIndex);
        }
        logger.info("| Your admin password: " + password + " |");
    }

    /**
     * Getter for the password.
     * @return returns the password
     */
    public static String getPassword(){
        return password;
    }

    /**
     * Log the password to the console.
     */
    public static void logPassword(){
        logger.info("| Your admin password: " + password + " |");
    }
}
