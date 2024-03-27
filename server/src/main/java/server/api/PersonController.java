package server.api;

import commons.Person;
import commons.Transaction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.database.PersonRepository;

import java.util.List;

@RestController
@RequestMapping("/api/person")
public class PersonController {
    private final PersonRepository db;

    private final TransactionController tc;

    /**
     * Constructor for the PersonController.
     * @param db repository for the personRepository
     */
    public PersonController(PersonRepository db, TransactionController tc) {
        this.db = db;
        this.tc = tc;
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
    public ResponseEntity<Person> add(@RequestBody Person person) {
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
    @PutMapping("/{id}")
    public ResponseEntity<Person> updateById(@PathVariable("id") int id, @RequestBody Person person) {
        if (id < 0 || !db.existsById(id) || person == null || person.getId() != id
                || person.getFirstName() == null || person.getLastName() == null) {
            return ResponseEntity.badRequest().build();
        }

        db.save(person);
        return ResponseEntity.ok(db.findById(id).get());
    }

    public ResponseEntity<Person> updateByIdTransactions(@PathVariable("id") int id, @RequestBody Person person) {
        if (id < 0 || !db.existsById(id) || person == null || person.getId() != id
                || person.getFirstName() == null || person.getLastName() == null) {
            return ResponseEntity.badRequest().build();
        }
        Person old = getById(id).getBody();
        List<Transaction> created = old.getCreatedTransactions();
        for (Transaction t : created) {
            t.setCreator(person);
            tc.updateById(t.getId(), t);
        }
        person.setCreatedTransactions(created);

        List<Transaction> participates = old.getTransactions();
        for (Transaction t : participates) {
            if (t.getParticipants().contains(old)) {
                List<Person> personList = t.getParticipants();
                personList.remove(old);
                personList.add(person);
                t.setParticipants(personList);
                tc.updateById(t.getId(), t);
            }
        }
        person.setTransactions(participates);

        db.save(person);
        return ResponseEntity.ok(db.findById(id).get());
    }
}
