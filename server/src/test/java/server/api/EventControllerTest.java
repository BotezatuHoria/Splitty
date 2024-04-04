package server.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import commons.Event;
import commons.Person;
import commons.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import server.services.implementations.EventServiceImplementation;
import server.services.implementations.PersonServiceImplementation;
import server.services.implementations.TransactionServiceImplementation;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

class EventControllerTest {

    private EventServiceImplementation eventService;

    private TestEventRepository eventRepository;
    private PersonServiceImplementation personService;
    private TestPersonRepository personRepository;
    private TransactionServiceImplementation transactionService;
    private TestTransactionRepository transactionRepository;

    private SimpMessagingTemplate messagingTemplate;

    @BeforeEach
    public void setup() {
        personRepository = new TestPersonRepository();
        personService = new PersonServiceImplementation(personRepository,
                new SimpMessagingTemplate(new MessageChannel() {
            @Override
            public boolean send(Message<?> message, long timeout) {
                return true;
            }
        }));



        transactionRepository = new TestTransactionRepository();
        transactionService = new TransactionServiceImplementation(transactionRepository, personService);

        eventRepository = new TestEventRepository();
        eventService = new EventServiceImplementation(eventRepository, transactionService, personService,
                new SimpMessagingTemplate(new MessageChannel() {
            @Override
            public boolean send(Message<?> message, long timeout) {
                return true;
            }
        }));
    }


    @Test
    void addTest() {
        Event event = new Event("tag", "title",1, "token", new ArrayList<>(), new ArrayList<>());
        var actual = eventService.add(event);
        assertEquals(actual.getBody(), event);
    }

    @Test
    void addNullTest() {
        Event nullEvent = null;
        Event wrongFields = new Event(null, null,-1, null, null, null);
        var actual1 = eventService.add(nullEvent);
        var actual2 = eventService.add(wrongFields);
        assertEquals(BAD_REQUEST, actual1.getStatusCode());
        assertEquals(BAD_REQUEST, actual2.getStatusCode());
    }



    @Test
    void getById() {
        Event event = new Event("tag", "title",2, "token",new ArrayList<>(), new ArrayList<>());
        eventService.add(event);
        var actual = eventService.getById(2);
        assertEquals(event, actual.getBody());
    }

    @Test
    void invalidIdTest() {
        var actual1 = eventService.getById(-1);
        var actual2 = eventService.getById(5);
        assertEquals(NOT_FOUND, actual1.getStatusCode());
        assertEquals(NOT_FOUND, actual2.getStatusCode());
    }


    @Test
    void getAllTest() {
        Event event = new Event("tag", "title",3, "token", new ArrayList<>(), new ArrayList<>());
        eventService.add(event);
        var actual = eventService.getAll().getBody();
        assertNotEquals(0, actual.size());
        assertTrue(actual.contains(event));
    }

    @Test
    void updateByIdTest() {
        Event event = new Event("tag", "title",4, "token", new ArrayList<>(), new ArrayList<>());
        eventService.add(event);
        Event eddited = new Event("tag2", "title2",4, "token2", new ArrayList<>(), new ArrayList<>());
        var actual = eventService.updateById(4, eddited);
        assertEquals(eddited, actual.getBody());
    }

    @Test
    void updateByIDInvalidTest() {
        Event event = new Event("tag", "title",6, "token", new ArrayList<>(), new ArrayList<>());
        eventService.add(event);
        Event wrongId = new Event("tag2", "title2",45, "token2", new ArrayList<>(), new ArrayList<>());
        Event wrongFields = new Event(null, null, 6, null, null, null);
        var actual1 = eventService.updateById(6, wrongId);
        var actual2 = eventService.updateById(6, wrongFields);
        var actual3 = eventService.updateById(45, wrongId);
        assertEquals(BAD_REQUEST, actual1.getStatusCode());
        assertEquals(BAD_REQUEST, actual2.getStatusCode());
        assertEquals(BAD_REQUEST, actual3.getStatusCode());
    }


    @Test
    void deleteByIdTest() {
        Event event = new Event("tag", "title",7, "token", new ArrayList<>(), new ArrayList<>());
        eventService.add(event);
        var actual = eventService.deleteById(7);
        assertEquals(event, actual.getBody());
        assertFalse(eventRepository.existsById((long) 7));
    }

    @Test
    void deleteByInvalidIDTest() {
        assertEquals(BAD_REQUEST, eventService.deleteById(46).getStatusCode());
    }

    @Test
    void getPeopleTest() {
        List<Person> people = new ArrayList<>();
        Event event = new Event("tag", "title",8, "token", people, new ArrayList<>());
        people.add(new Person("test@email.com", "First", "Test",
                "iban33"));
        eventService.add(event);
        var actual = eventService.getPeople(8);
        assertEquals(actual.getBody(), people);
    }

    @Test
    void getPeopleInvalidIDTest() {
        assertEquals(BAD_REQUEST, eventService.getPeople(47).getStatusCode());
    }


    @Test
    void getExpensesTest() {
        List<Transaction> transactions = new ArrayList<>();
        Event event = new Event("tag", "title",9, "token", new ArrayList<>(), transactions);
        transactions.add(new Transaction("name", LocalDate.now(), 4, 4,
                new ArrayList<>(), new Person(), null));
        eventService.add(event);
        var actual = eventService.getExpenses(9);
        assertEquals(actual.getBody(), transactions);
    }

    @Test
    void getEventInvalidIDTest() {
        assertEquals(BAD_REQUEST, eventService.getExpenses(48).getStatusCode());
    }

    // If we want to add it later we can add tests to see if the persons in event also get updated.
    @Test
    void createNewExpenseTest() {
    }

    @Test
    void createTransactionInvalidIDTest() {
        Transaction transaction = new Transaction("name", LocalDate.now(), 4, 4,
                new ArrayList<>(), new Person(), null);
        assertEquals(BAD_REQUEST, eventService.createNewExpense(49, transaction).getStatusCode());
    }



    // If we want we can later add a test to see if the People get updated for transactions.
    @Test
    void deleteTransactionById() {
    }

    @Test
    void addPersonTest() throws JsonProcessingException {
        Event event = new Event("tag", "title",12, "token", new ArrayList<>(), new ArrayList<>());
        Person person = new Person("test@email.com", "First", "Test",
                "iban33");
        eventService.add(event);
        var actual = eventService.add(12, person);
        assertTrue(event.getPeople().contains(person));
    }

    @Test
    void addPersonInvalidId() throws JsonProcessingException {
        Event event = new Event("tag", "title",12, "token", new ArrayList<>(), new ArrayList<>());
        eventService.add(event);
        Person person = new Person("test@email.com", "First", "Test",
                "iban33");
        assertEquals(BAD_REQUEST, eventService.add(50, person).getStatusCode());
    }

}