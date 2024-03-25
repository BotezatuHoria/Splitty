package server.api;


import java.util.*;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import commons.Event;

import commons.Person;
import commons.Transaction;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.bind.annotation.*;


import server.database.EventRepository;


@RestController
@RequestMapping("/api/event")
public class EventController {
    @PersistenceContext
    private EntityManager entityManager;

    private final EventRepository repo;
    private PersonController pc;

    private TransactionController tc;

    /**
     * Constructor for the EventController.
     *
     * @param repo repository for the eventRepository
     */
    public EventController(EventRepository repo, PersonController pc, TransactionController tc) {
        this.repo = repo;
        this.pc = pc;
        this.tc = tc;
    }

    /**
     * Gets all the events as a Jason file.
     *
     * @return the event
     */
    @GetMapping(path = {"", "/"})
    public List<Event> getAll() {
        return repo.findAll();
    }


    /**
     * Method for getting and event by id from url.
     *
     * @param id of the event you want to get
     * @return the event
     */
    @GetMapping("/{id}")
    public ResponseEntity<Event> getById(@PathVariable("id") long id) {
        if (id < 0 || !repo.existsById(id)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(repo.findById(id).get());
    }

    /**
     * Update method for updating an event.
     * @param id - the id of the event
     * @param event - the new structure of the event
     * @return - the event as a JSON
     */
    @PutMapping("/{id}")
    public ResponseEntity<Event> updateById(@PathVariable("id") long id, @RequestBody Event event) {
        if (event == null || event.getId() < 0 || !repo.existsById(id) || event.getId() != id || event.getTag() == null || event.getTitle() == null
                || event.getToken() == null ||
                event.getPeople() == null || event.getTransactions() == null) {
            return ResponseEntity.badRequest().build();
        }
        for (Person person : event.getPeople()) {
            person.setEvent(event);
            pc.updateById(person.getId(), person);
        }
        Event saved = repo.save(event);
        return ResponseEntity.ok(saved);
    }

    /**
     * Method for adding the event to a repository.
     *
     * @param event you want to add
     * @return the event you added
     */
    @PostMapping(path = {"", "/"})
    public ResponseEntity<Event> add(@RequestBody Event event) {

        if (event == null || repo.existsById((long) event.getId()) || event.getId() < 0 || event.getTag() == null
                || event.getTitle() == null || event.getToken() == null ||
                event.getPeople() == null || event.getTransactions() == null) {
            return ResponseEntity.badRequest().build();
        }
        Event saved = repo.save(event);
        return ResponseEntity.ok(saved);
    }


    /**
     * Deletes the event with the id you provided in the url.
     *
     * @param id of the event you want to delete
     * @return the event you deleted
     */
    @DeleteMapping(path = {"/{id}"})
    public ResponseEntity<Event> deleteById(@PathVariable("id") long id) {
        if (id < 0 || !repo.existsById(id)) {
            return ResponseEntity.badRequest().build();
        }
        ResponseEntity<commons.Event> response = ResponseEntity.ok(repo.findById(id).get());
        repo.deleteById(id);
        return response;
    }

    /**
     * Get function for all the people in a certain event.
     * @param id - id of the event
     * @return - a set of all the people in the event
     */
    @GetMapping("/{id}/person")
    public ResponseEntity<List<Person>> getPeople(@PathVariable("id") long id) {
        if (id < 0 || !repo.existsById(id)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(repo.findById(id).get().getPeople());
    }

    /**
     * Method for adding a person to an event.
     * @param id - id of the event
     * @param person - person to be added to the event
     * @return - current state of the event
     */
    @PostMapping(path = {"/{id}/person"})
    public ResponseEntity<Person> add(@PathVariable("id") long id, @RequestBody Person person) throws JsonProcessingException {
        if (id < 0 || !repo.existsById(id)) {
            return ResponseEntity.badRequest().build();
        }
        ObjectMapper objectMapper = new Jackson2ObjectMapperBuilder().json().build();
        String result = objectMapper.writeValueAsString(person);
        System.out.println(result);

        person.setEvent(getById(id).getBody());
        person.setTransactions(new ArrayList<>());
        person.setCreatedTransactions(new ArrayList<>());
        Person newPerson = pc.add(person).getBody();
        Event event = getById(id).getBody();
        event.addPerson(newPerson);
        repo.save(event);
        return ResponseEntity.ok(pc.getById(newPerson.getId()).getBody());
    }



    @PutMapping(path = {"/{idEvent}/person"})
    public ResponseEntity<Person> updatePerson(@PathVariable("idEvent") long idEvent, @RequestParam int id,
                                               @RequestBody Person update) {
        if (Objects.equals(pc.getById(id), ResponseEntity.badRequest().build())) {
            return ResponseEntity.badRequest().build();
        }
        if ((idEvent < 0 || !repo.existsById(idEvent) || id < 0)) {
            return ResponseEntity.badRequest().build();
        }
        pc.updateById(id, update);
        Event event = getById(idEvent).getBody();
        Person old = pc.getById(id).getBody();
        List<Person> people = event.getPeople();
        if (people.contains(old)) {
            people.remove(old);
            people.add(update);
        }
        List<Transaction> transactions = getExpenses(idEvent).getBody();
        event.setPeople(people);
        event.setTransactions(transactions);
        update.setEvent(event);
        pc.updateById(id, update);
        return ResponseEntity.ok(pc.getById(id).getBody());
    }

    /**
     * Method for deleting a person from the event.
     * @param idEvent - id of the event
     * @param id - id of the person to be deleted
     * @return - the new state of the event
     */
    @DeleteMapping(path = {"/{idEvent}/person"})
    public ResponseEntity<Person> deleteById(@PathVariable("idEvent") long idEvent,
                                           @RequestParam int id) {
        if (Objects.equals(pc.getById(id), ResponseEntity.badRequest().build())) {
            return ResponseEntity.badRequest().build();
        }
        if ((idEvent < 0 || !repo.existsById(idEvent) || id < 0)) {
            return ResponseEntity.badRequest().build();
        }
        Event event = getById(idEvent).getBody();
        Person rmv = pc.getById(id).getBody();
        event.removePerson(rmv);
        updateById(idEvent, event);
        pc.deleteById(id);
        return ResponseEntity.ok(rmv);
    }

    /**
     * Method for retrieving all the expenses that are into an event.
     * @param idEvent - id of the event
     * @return - a set of all expenses in the event
     */
    @GetMapping(path = {"/{idEvent}/expenses"})
    public ResponseEntity<List<Transaction>> getExpenses(@PathVariable("idEvent") long idEvent) {
        if (idEvent < 0 || !repo.existsById(idEvent)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(repo.findById(idEvent).get().getTransactions());
    }

    /**
     * Method for adding a new expense to the event.
     * @param idEvent - id of the event
     * @param transaction - transaction to be added
     * @return - the state of the new event
     */
    @PostMapping(path = {"/{idEvent}/expenses"})
    public ResponseEntity<Transaction> createNewExpense(@PathVariable("idEvent") long idEvent,
                                                        @RequestBody Transaction transaction) {
        if (idEvent < 0 || !repo.existsById(idEvent)) {
            return ResponseEntity.badRequest().build();
        }

        tc.add(transaction);

        Person person = pc.getById(transaction.getCreator().getId()).getBody();
        List<Transaction> createdTransactions;
        if (person.getCreatedTransactions() == null) {
            person.setCreatedTransactions(Collections.emptyList());
        }
        createdTransactions = person.getCreatedTransactions();
        createdTransactions.add(transaction);
        person.setCreatedTransactions(createdTransactions);
        pc.updateById(person.getId(), person);
        transaction.setCreator(person);

        tc.updateById(transaction.getId(), transaction);

        List<Person> involvedPeople = tc.getById(transaction.getId()).getBody().getParticipants();
        for (Person p : involvedPeople) {
            List<Transaction> transactions;
            if (p.getTransactions() == null) {
                p.setTransactions(new ArrayList<>());
            }
            transactions = p.getTransactions();
            transactions.add(transaction);
            p.setTransactions(transactions);
            pc.updateById(p.getId(), p);
        }
        transaction.setParticipants(involvedPeople);

        tc.updateById(transaction.getId(), transaction);

        Event event = getById(idEvent).getBody();
        List<Transaction> transactions = event.getTransactions();
        transactions.add(transaction);
        event.setTransactions(transactions);

        tc.updateById(transaction.getId(), transaction);
        repo.save(event);
        return ResponseEntity.ok(tc.getById(transaction.getId()).getBody());
    }

    /**
     * Method for deleting a person from an event.
     * @param idEvent - id of the event
     * @param idTransaction - id of the transaction to be deleted
     * @return - the current state of the event
     */
    @DeleteMapping(path = {"/{idEvent}/expenses"})
    public ResponseEntity<Event> deleteTransactionById(@PathVariable("idEvent") long idEvent,
                                                       @RequestParam int idTransaction) {
        if (Objects.equals(tc.getById(idTransaction), ResponseEntity.badRequest().build())) {
            return ResponseEntity.badRequest().build();
        }
        if ((idEvent < 0 || !repo.existsById(idEvent) || idTransaction < 0)) {
            return ResponseEntity.badRequest().build();
        }
        Transaction tr = tc.getById(idTransaction).getBody();
        Event event = getById(idEvent).getBody();
        event.removeTransaction(tr);

        Person person = pc.getById(tr.getCreator().getId()).getBody();
        List<Transaction> createdTransactions = person.getCreatedTransactions();
        createdTransactions.remove(tr);
        pc.updateById(person.getId(), person);

        for (Person p : tr.getParticipants()) {
            if (p.getTransactions() != null) {
                List<Transaction> transactions = p.getTransactions();
                transactions.remove(tr);
                p.setTransactions(transactions);
                pc.updateById(p.getId(), p);
            }
        }

        Event saved = repo.save(event);
        ResponseEntity<Event> response = ResponseEntity.ok(saved);
        tc.deleteById(idTransaction);
        return response;
    }
}
