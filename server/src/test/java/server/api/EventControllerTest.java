package server.api;

import commons.Event;
import commons.Person;
import commons.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

class EventControllerTest {

    private EventController eventController;

    private TestEventRepository eventRepository;
    private PersonController personController;
    private TestPersonRepository personRepository;
    private TransactionController transactionController;
    private TestTransactionRepository transactionRepository;

    @BeforeEach
    public void setup() {
        personRepository = new TestPersonRepository();
        personController = new PersonController(personRepository);

        transactionRepository = new TestTransactionRepository();
        transactionController = new TransactionController(transactionRepository);

        eventRepository = new TestEventRepository();
        eventController = new EventController(eventRepository, personController, transactionController);
    }


    @Test
    void addTest() {
        Event event = new Event("tag", "title",1, "token", new HashSet<>(), new HashSet<>());
        var actual = eventController.add(event);
        assertEquals(actual.getBody(), event);
    }

    @Test
    void addNullTest() {
        Event nullEvent = null;
        Event wrongFields = new Event(null, null,-1, null, null, null);
        var actual1 = eventController.add(nullEvent);
        var actual2 = eventController.add(wrongFields);
        assertEquals(BAD_REQUEST, actual1.getStatusCode());
        assertEquals(BAD_REQUEST, actual2.getStatusCode());
    }



    @Test
    void getById() {
        Event event = new Event("tag", "title",2, "token", new HashSet<>(), new HashSet<>());
        eventController.add(event);
        var actual = eventController.getById(2);
        assertEquals(event, actual.getBody());
    }

    @Test
    void invalidIdTest() {
        var actual1 = eventController.getById(-1);
        var actual2 = eventController.getById(5);
        assertEquals(BAD_REQUEST, actual1.getStatusCode());
        assertEquals(BAD_REQUEST, actual2.getStatusCode());
    }


    @Test
    void getAllTest() {
        Event event = new Event("tag", "title",3, "token", new HashSet<>(), new HashSet<>());
        eventController.add(event);
        var actual = eventController.getAll();
        assertNotEquals(0, actual.size());
        assertTrue(actual.contains(event));
    }

    @Test
    void updateById() {

    }

    @Test
    void deleteByIdTest() {
    }

    @Test
    void getPeople() {
    }


    @Test
    void getExpenses() {
    }

    @Test
    void createNewExpense() {
    }

    @Test
    void deleteTransactionById() {
    }
}