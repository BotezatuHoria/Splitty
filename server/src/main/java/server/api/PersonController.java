package server.api;

import commons.Person;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.services.implementations.PersonServiceImplementation;

import java.util.List;

@RestController
@RequestMapping("/api/person")
public class PersonController {
    private final PersonServiceImplementation psi;

    /**
     * Constructor for the PersonController.
     * @param psi service implementation
     */
    public PersonController(PersonServiceImplementation psi) {
        this.psi = psi;
    }

    /**
     * Get all people.
     * @return list of people
     */
    @GetMapping(path = { "", "/" })
    public ResponseEntity<List<Person>> getAll() {
        return psi.getAll();
    }

    /**
     * Gets person entity by supplied id.
     * @param id the id of the user
     * @return person who`s id matches supplied id
     */
    @GetMapping("/{id}")
    public ResponseEntity<Person> getById(@PathVariable("id") int id) {
        return psi.getById(id);
    }

    /**
     * Adds person to the database.
     * @param person the person to add
     * @return person that was added
     */
    @PostMapping(path = { "", "/" })
    public ResponseEntity<Person> add(@RequestBody Person person) {
        return psi.add(person);
    }

    /**
     * Deletes person by their id.
     * @param id the id to delete by
     * @return person that was deleted
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Person> deleteById(@PathVariable("id") int id) {
        return psi.deleteById(id);
    }



    /**
     * Updates person based on their id.
     * @param id the id to update by
     * @return person that was updated
     */
    @PutMapping("/{id}")
    public ResponseEntity<Person> updateById(@PathVariable("id") int id, @RequestBody Person person) {
        return psi.updateById(id, person);
    }

}
