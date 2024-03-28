package server.services.implementations;

import commons.Person;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import server.database.PersonRepository;
import server.services.interfaces.PersonService;

import java.util.List;

@Service
public class PersonServiceImplementation implements PersonService {

    private final PersonRepository db;
    private final SimpMessagingTemplate messagingTemplate;
    public PersonServiceImplementation(PersonRepository db, SimpMessagingTemplate messagingTemplate) {
        this.db = db;
        this.messagingTemplate = messagingTemplate;
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
        messagingTemplate.convertAndSend("/topic/events/people", saved);
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

    @Override
    public ResponseEntity<Person> updateById(int id, Person person) {
        if (id < 0 || !db.existsById(id) || person == null || person.getId() != id
                || person.getFirstName() == null || person.getLastName() == null) {
            return ResponseEntity.badRequest().build();
        }
        Person saved = db.save(person);
        messagingTemplate.convertAndSend("/topic/events/people", saved);
        return ResponseEntity.ok(saved);
    }
}
