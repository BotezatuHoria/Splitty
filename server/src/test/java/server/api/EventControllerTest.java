//package server.api;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import commons.Event;
//import commons.Person;
//import commons.Transaction;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.springframework.http.HttpStatus.BAD_REQUEST;
//
//class EventControllerTest {
//
//    private EventController eventController;
//
//    private TestEventRepository eventRepository;
//    private PersonController personController;
//    private TestPersonRepository personRepository;
//    private TransactionController transactionController;
//    private TestTransactionRepository transactionRepository;
//
//    @BeforeEach
//    public void setup() {
//        personRepository = new TestPersonRepository();
//        personController = new PersonController(personRepository);
//
//        transactionRepository = new TestTransactionRepository();
//        transactionController = new TransactionController(transactionRepository);
//
//        eventRepository = new TestEventRepository();
//        eventController = new EventController(eventRepository, personController, transactionController);
//    }
//
//
//    @Test
//    void addTest() {
//        Event event = new Event("tag", "title",1, "token", new ArrayList<>(), new ArrayList<>());
//        var actual = eventController.add(event);
//        assertEquals(actual.getBody(), event);
//    }
//
//    @Test
//    void addNullTest() {
//        Event nullEvent = null;
//        Event wrongFields = new Event(null, null,-1, null, null, null);
//        var actual1 = eventController.add(nullEvent);
//        var actual2 = eventController.add(wrongFields);
//        assertEquals(BAD_REQUEST, actual1.getStatusCode());
//        assertEquals(BAD_REQUEST, actual2.getStatusCode());
//    }
//
//
//
//    @Test
//    void getById() {
//        Event event = new Event("tag", "title",2, "token",new ArrayList<>(), new ArrayList<>());
//        eventController.add(event);
//        var actual = eventController.getById(2);
//        assertEquals(event, actual.getBody());
//    }
//
//    @Test
//    void invalidIdTest() {
//        var actual1 = eventController.getById(-1);
//        var actual2 = eventController.getById(5);
//        assertEquals(BAD_REQUEST, actual1.getStatusCode());
//        assertEquals(BAD_REQUEST, actual2.getStatusCode());
//    }
//
//
//    @Test
//    void getAllTest() {
//        Event event = new Event("tag", "title",3, "token", new ArrayList<>(), new ArrayList<>());
//        eventController.add(event);
//        var actual = eventController.getAll();
//        assertNotEquals(0, actual.size());
//        assertTrue(actual.contains(event));
//    }
//
//    @Test
//    void updateByIdTest() {
//        Event event = new Event("tag", "title",4, "token", new ArrayList<>(), new ArrayList<>());
//        eventController.add(event);
//        Event eddited = new Event("tag2", "title2",4, "token2", new ArrayList<>(), new ArrayList<>());
//        var actual = eventController.updateById(4, eddited);
//        assertEquals(eddited, actual.getBody());
//    }
//
//    @Test
//    void updateByIDInvalidTest() {
//        Event event = new Event("tag", "title",6, "token", new ArrayList<>(), new ArrayList<>());
//        eventController.add(event);
//        Event wrongId = new Event("tag2", "title2",45, "token2", new ArrayList<>(), new ArrayList<>());
//        Event wrongFields = new Event(null, null, 6, null, null, null);
//        var actual1 = eventController.updateById(6, wrongId);
//        var actual2 = eventController.updateById(6, wrongFields);
//        var actual3 = eventController.updateById(45, wrongId);
//        assertEquals(BAD_REQUEST, actual1.getStatusCode());
//        assertEquals(BAD_REQUEST, actual2.getStatusCode());
//        assertEquals(BAD_REQUEST, actual3.getStatusCode());
//    }
//
//
//    @Test
//    void deleteByIdTest() {
//        Event event = new Event("tag", "title",7, "token", new ArrayList<>(), new ArrayList<>());
//        eventController.add(event);
//        var actual = eventController.deleteById(7);
//        assertEquals(event, actual.getBody());
//        assertFalse(eventRepository.existsById((long) 7));
//    }
//
//    @Test
//    void deleteByInvalidIDTest() {
//        assertEquals(BAD_REQUEST, eventController.deleteById(46).getStatusCode());
//    }
//
//    @Test
//    void getPeopleTest() {
//        List<Person> people = new ArrayList<>();
//        Event event = new Event("tag", "title",8, "token", people, new ArrayList<>());
//        people.add(new Person("test@email.com", "First", "Test",
//                "iban33",event, new ArrayList<>(), new ArrayList<>()));
//        eventController.add(event);
//        var actual = eventController.getPeople(8);
//        assertEquals(actual.getBody(), people);
//    }
//
//    @Test
//    void getPeopleInvalidIDTest() {
//        assertEquals(BAD_REQUEST, eventController.getPeople(47).getStatusCode());
//    }
//
//
//    @Test
//    void getExpensesTest() {
//        List<Transaction> transactions = new ArrayList<>();
//        Event event = new Event("tag", "title",9, "token", new ArrayList<>(), transactions);
//        transactions.add(new Transaction("name", LocalDate.now(), 4, 4,
//                new ArrayList<>(), new Person(), null));
//        eventController.add(event);
//        var actual = eventController.getExpenses(9);
//        assertEquals(actual.getBody(), transactions);
//    }
//
//    @Test
//    void getEventInvalidIDTest() {
//        assertEquals(BAD_REQUEST, eventController.getExpenses(48).getStatusCode());
//    }
//
//    // If we want to add it later we can add tests to see if the persons in event also get updated.
//    @Test
//    void createNewExpenseTest() {
//    }
//
//    @Test
//    void createTransactionInvalidIDTest() {
//        Transaction transaction = new Transaction("name", LocalDate.now(), 4, 4,
//                new ArrayList<>(), new Person(), null);
//        assertEquals(BAD_REQUEST, eventController.createNewExpense(49, transaction).getStatusCode());
//    }
//
//
//
//    // If we want we can later add a test to see if the People get updated for transactions.
//    @Test
//    void deleteTransactionById() {
//    }
//
//    @Test
//    void addPersonTest() throws JsonProcessingException {
//        Event event = new Event("tag", "title",12, "token", new ArrayList<>(), new ArrayList<>());
//        Person person = new Person("test@email.com", "First", "Test",
//                "iban33",event, new ArrayList<>(), new ArrayList<>());
//        eventController.add(event);
//        var actual = eventController.add(12, person);
//        assertEquals(actual.getBody().getEvent(), event);
//        assertTrue(event.getPeople().contains(person));
//    }
//
//    @Test
//    void addPersonInvalidId() throws JsonProcessingException {
//        Event event = new Event("tag", "title",12, "token", new ArrayList<>(), new ArrayList<>());
//        eventController.add(event);
//        Person person = new Person("test@email.com", "First", "Test",
//                "iban33",event, new ArrayList<>(), new ArrayList<>());
//        assertEquals(BAD_REQUEST, eventController.add(50, person).getStatusCode());
//    }
//
//
//
//    @Test
//    void deleteByIdPersonTest() throws JsonProcessingException {
//        Event event = new Event("tag", "title",14, "token", new ArrayList<>(), new ArrayList<>());
//        Person person = new Person("test@email.com", "First", "Test",
//                "iban33",event, new ArrayList<>(), new ArrayList<>());
//        eventController.add(event);
//        eventController.add(14, person);
//        var actual = eventController.deleteById(14, person.getId());
//        assertEquals(actual.getBody().getEvent(), event);
//        assertFalse(event.getPeople().contains(person));
//
//    }
//
//    @Test
//    void deletePersonInvalidId() throws JsonProcessingException {
//        Event event = new Event("tag", "title",15, "token", new ArrayList<>(), new ArrayList<>());
//        eventController.add(event);
//        Person person = new Person("test@email.com", "First", "Test",
//                "iban33",event, new ArrayList<>(), new ArrayList<>());
//        eventController.add(15, person);
//        assertEquals(BAD_REQUEST, eventController.deleteById(50, person.getId()).getStatusCode());
//        assertEquals(BAD_REQUEST, eventController.deleteById(15, 252).getStatusCode());
//    }
//}