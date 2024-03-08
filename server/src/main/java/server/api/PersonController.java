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
     * Get all people.
     * @return list of people
     */
    @GetMapping(path = { "", "/" })
    public List<Person> getAll() {
        return db.findAll();
    }

    /**
     * Gets person entity by supplied id.
     * @param id the id of the user
     * @return person who`s id matches supplied id
     */
    @GetMapping("/{id}")
    public ResponseEntity<Person> getById(@PathVariable("id") int id) {
        if (id < 0 || !db.existsById(id)) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(db.findById(id).get());
    }

    /**
     * Adds person to the database.
     * @param person the person to add
     * @return person that was added
     */
    @PostMapping(path = { "", "/" })
    public ResponseEntity<Person> add(Person person) {
        if (person == null || person.getId() < 0 || person.getFirstName() == null
                || person.getLastName() == null) {
            return ResponseEntity.badRequest().build();
        }

        Person saved = db.save(person);
        return ResponseEntity.ok(saved);
    }

    /**
     * Deletes person by their id.
     * @param id the id to delete by
     * @return person that was deleted
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Person> deleteById(@PathVariable("id") int id) {
        if (id < 0 || !db.existsById(id)) {
            return ResponseEntity.badRequest().build();
        }

        ResponseEntity<Person> person = ResponseEntity.ok(db.findById(id).get());
        db.deleteById(id);
        return person;
    }

    /**
     * Updates person based on their id.
     * @param id the id to update by
     * @return person that was updated
     */
    @PatchMapping("/{id}")
    public ResponseEntity<Person> updateById(@PathVariable("id") int id, Person person) {
        if (id < 0 || !db.existsById(id) || person == null || person.getId() != id
                || person.getFirstName() == null || person.getLastName() == null) {
            return ResponseEntity.badRequest().build();
        }

        db.save(person);
        return ResponseEntity.ok(db.findById(id).get());
    }
}
