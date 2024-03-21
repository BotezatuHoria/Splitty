package server;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class PasswordGenerator {

    Logger logger = LoggerFactory.getLogger(PasswordGenerator.class);

    private static String password;
    /**
     * Generates a new random password for the admin.
     */
    @PostConstruct
    private void generatePassword(){
        String allChars = "abcdefghijklmnopqrstuvwxyz1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        password = "";
        while (password.length() < 8){
            Random random = new Random();
            int charIndex = random.nextInt(allChars.length());
            password += allChars.charAt(charIndex);
        }
        System.out.println(password);
        logger.info("| Your admin password: " + password + " |");
    }
}
