package server.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.PasswordGenerator;

@RestController
public class LoginController {

    private String password;

    public LoginController(){
        password = PasswordGenerator.getPassword();
    }

//    /**
//     * Validates if the user password is correct
//     * @param userPassword the password the user implemented
//     * @return responseEntity
//     */
//    @PostMapping("/login/{pass}")
//    public ResponseEntity<Void> login(@PathVariable("pass") String userPassword){
//        if (userPassword.equals(password)){
//            return ResponseEntity.ok().build();
//        }
//        return ResponseEntity.badRequest().build();
//    }

    @GetMapping("/login")
    public ResponseEntity<String> getPassword(){
        return ResponseEntity.ok(password);
    }
}
