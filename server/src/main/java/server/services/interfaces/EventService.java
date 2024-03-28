package server.services.interfaces;

import commons.Event;
import commons.Person;
import commons.Transaction;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface EventService {
    public ResponseEntity<List<Event>> getAll();
    public ResponseEntity<Event> getById(long id);
    public ResponseEntity<Event> updateById(long id, Event event);
    public ResponseEntity<Event> add(Event event);
    public ResponseEntity<Event> deleteById(long id);
    public ResponseEntity<List<Person>> getPeople(long id);
    public ResponseEntity<Person> add(long id, Person person);

    public ResponseEntity<List<Transaction>> getExpenses(long idEvent);
    public ResponseEntity<Transaction> createNewExpense(long idEvent, Transaction transaction);
}
