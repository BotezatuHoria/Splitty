package server.api;

import commons.Person;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.database.PersonRepository;

import java.util.List;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/api/person")
public class PersonController {
    private final PersonRepository db;

    /**
     * Constructor for the PersonController.
     * @param db repository for the personRepository
     */
    public PersonController(PersonRepository db) {
        this.db = db;
    }

    /**
     * Get all people as json.
     * @return list of people as json
     */
    @GetMapping(path = { "", "/" })
    public List<Person> getAll() {
        return db.findAll();
    }

    /**
     * Gets person entity by supplied email.
     * @param email the email of the user
     * @return person who`s email matches supplied email
     */
    @GetMapping("/{email}")
    public ResponseEntity<Person> getById(@PathVariable("email") String email) {
        if (email == null || !db.existsById(email)) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(db.findById(email).get());
    }

    /**
     * Adds person to the database.
     * @param person the person to add
     * @return person that was added
     */
    @PostMapping(path = { "", "/" })
    protected ResponseEntity<Person> add(Person person) {
        // The regex ensures that:
        //  - email begins with alphanumeric characters, including underscores, pluses, ampersands, asterisks, or hyphens.
        //  - may contain dots as long as they are not the first or last character and it does not come one after the other.
        //  - contains an @ symbol separating the local part from the domain part.
        //  - the domain part contains alphanumeric characters, including hyphens, but again, hyphens cannot be the first character. It then has a period . and a domain name with 2 to 7 letters to comply with domain name restrictions.

        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern emailPattern = Pattern.compile(emailRegex);

        if (person == null || person.getEmail() == null || !emailPattern.matcher(person.getEmail()).matches()) {
            return ResponseEntity.badRequest().build();
        }

        Person saved = db.save(person);
        return ResponseEntity.ok(saved);
    }

    /**
     * Deletes person by their email.
     * @param email the email to delete by
     * @return person that was deleted
     */
    @DeleteMapping("/{email}")
    protected ResponseEntity<Person> deleteById(@PathVariable("email") String email) {
        if (email == null || !db.existsById(email)) {
            return ResponseEntity.badRequest().build();
        }

        ResponseEntity<Person> person = ResponseEntity.ok(db.findById(email).get());
        db.deleteById(email);
        return person;
    }

}
