package server.services.interfaces;

import commons.Person;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PersonService {
    public ResponseEntity<List<Person>> getAll();
    public ResponseEntity<Person> getById(int id);
    public ResponseEntity<Person> add(Person person);
    public ResponseEntity<Person> deleteById(int id);
}