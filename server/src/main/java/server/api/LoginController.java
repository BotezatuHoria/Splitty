package server.api;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import server.PasswordGenerator;

@Controller
@RequestMapping("/api/login")
public class LoginController {

    private String password;

    public LoginController(){
        password = PasswordGenerator.getPassword();
    }

    @GetMapping(path = {"/", ""})
    public ResponseEntity<String> getPassword(){
        password = PasswordGenerator.getPassword();
        return ResponseEntity.ok(password);
    }

    @GetMapping(path = {"log", "/log"})
    public ResponseEntity<Void> sendPassword(){
        PasswordGenerator.logPassword();
        return ResponseEntity.ok().build();
    }
}
