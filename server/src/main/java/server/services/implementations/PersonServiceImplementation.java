package server.services.implementations;

import commons.Person;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import server.database.PersonRepository;
import server.services.interfaces.PersonService;

import java.util.List;

@Service
public class PersonServiceImplementation implements PersonService {

    private final PersonRepository db;
    public PersonServiceImplementation(PersonRepository db) {
        this.db = db;
    }
    @Override
    public ResponseEntity<List<Person>> getAll() {
        List<Person> people = db.findAll();
        if (people.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(people);
        }
    }

    @Override
    public ResponseEntity<Person> getById(int id) {
        var res = db.findById(id);
        return res.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<Person> add(Person person) {
        if (person == null || person.getId() < 0 || person.getFirstName() == null
                || person.getLastName() == null) {
            return ResponseEntity.badRequest().build();
        }
        Person saved = db.save(person);
        return ResponseEntity.ok(saved);
    }

    @Override
    public ResponseEntity<Person> deleteById(int id) {
        if (id < 0 || !db.existsById(id)) {
            return ResponseEntity.badRequest().build();
        }
        ResponseEntity<Person> person = ResponseEntity.ok(db.findById(id).get());
        db.deleteById(id);
        return person;
    }
}
