package server.services.implementations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import commons.Event;
import commons.Person;
import commons.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import server.database.EventRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class EventServiceImplementationTest {
  //Mokito mocks
  @Mock
  private EventRepository eventRepository;
  @Mock
  private SimpMessagingTemplate messagingTemplate;
  @Mock
  private PersonServiceImplementation psi;
  @Mock
  private TransactionServiceImplementation tsi;
  //inject mocks
  @InjectMocks
  private EventServiceImplementation eventServiceImplementation;

  //setup
  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  /**
   * Test the getAll method.
   */
  @Test
  void testGetAll() {
    List<Event> expected = List.of(new Event(), new Event());
    when(eventRepository.findAll()).thenReturn(expected);
    ResponseEntity<List<Event>> response = eventServiceImplementation.getAll();

    assertEquals(expected, response.getBody());
  }

  /**
   * Test the getById method with a valid id.
   */
  @Test
  void testGetById() {
    // Setup your test data and expectations
    Event event = new Event();
    event.setId(1);
    when(eventRepository.findById(1L)).thenReturn(Optional.of(event));

    ResponseEntity<Event> response = eventServiceImplementation.getById(1L);

    assertEquals(event, response.getBody());
  }

  /**
   * Test the getById method with an invalid id.
   */
  @Test
  void testUpdateById() {
    Event event = new Event("Event one", "Party", 1, "1234", new ArrayList<>(), new ArrayList<>());
    Person person = new Person("test@email.com", "First", "Test",
            "iban33");
    event.addPerson(person);

    event.setId(1);
    when(eventRepository.existsById(1L)).thenReturn(true);
    when(eventRepository.findById(1L)).thenReturn(Optional.of(event));
    when(eventRepository.save(event)).thenReturn(event);

    ResponseEntity<Event> response = eventServiceImplementation.updateById(1L, event);


    assertEquals(event, response.getBody());
  }

  /**
   * Test the updateById method with a null event.
   */
  @Test
  void testUpdateByIdWithNullEvent() {
    // Setup your test data and expectations
    Event event = new Event();
    event.setId(1);
    when(eventRepository.existsById(1L)).thenReturn(true);
    when(eventRepository.findById(1L)).thenReturn(Optional.of(event));
    when(eventRepository.save(event)).thenReturn(event);

    // Execute the method being tested
    ResponseEntity<Event> response = eventServiceImplementation.updateById(1L, null);

    // Verify the results
    assertEquals(400, response.getStatusCodeValue());
  }

  /**
   * Test the updateById method with a null title.
   */
  @Test
  void testUpdateByIdWithInvalidId() {
    // Setup your test data and expectations
    Event event = new Event();
    event.setId(1);
    when(eventRepository.existsById(1L)).thenReturn(false);
    when(eventRepository.findById(1L)).thenReturn(Optional.of(event));
    when(eventRepository.save(event)).thenReturn(event);

    // Execute the method being tested
    ResponseEntity<Event> response = eventServiceImplementation.updateById(1L, event);

    // Verify the results
    assertEquals(400, response.getStatusCodeValue());
  }

  /**
   * Test the updateById method with a null name.
   */
  @Test
  void testUpdateByIdWithNullName() {
    // Setup your test data and expectations
    Event event = new Event();
    event.setId(1);
    when(eventRepository.existsById(1L)).thenReturn(true);
    when(eventRepository.findById(1L)).thenReturn(Optional.of(event));
    when(eventRepository.save(event)).thenReturn(event);

    // Execute the method being tested
    ResponseEntity<Event> response = eventServiceImplementation.updateById(1L, event);

    // Verify the results
    assertEquals(400, response.getStatusCodeValue());
  }

  /**
   * Test the updateById method with a null tag.
   */
  @Test
  void testUpdateByIdWithNullTag() {
    // Setup your test data and expectations
    Event event = new Event();
    event.setId(1);
    when(eventRepository.existsById(1L)).thenReturn(true);
    when(eventRepository.findById(1L)).thenReturn(Optional.of(event));
    when(eventRepository.save(event)).thenReturn(event);

    // Execute the method being tested
    ResponseEntity<Event> response = eventServiceImplementation.updateById(1L, event);

    // Verify the results
    assertEquals(400, response.getStatusCodeValue());
  }

  /**
   * Test the updateById method with a null token.
   */
  @Test
  void testUpdateByIdWithNullToken() {
    // Setup your test data and expectations
    Event event = new Event();
    event.setId(1);
    when(eventRepository.existsById(1L)).thenReturn(true);
    when(eventRepository.findById(1L)).thenReturn(Optional.of(event));
    when(eventRepository.save(event)).thenReturn(event);

    // Execute the method being tested
    ResponseEntity<Event> response = eventServiceImplementation.updateById(1L, event);

    // Verify the results
    assertEquals(400, response.getStatusCodeValue());
  }

  /**
   * Test the updateById method with a null people.
   */
  @Test
  void testUpdateByIdWithNullPeople() {
    // Setup your test data and expectations
    Event event = new Event();
    event.setId(1);
    when(eventRepository.existsById(1L)).thenReturn(true);
    when(eventRepository.findById(1L)).thenReturn(Optional.of(event));
    when(eventRepository.save(event)).thenReturn(event);

    // Execute the method being tested
    ResponseEntity<Event> response = eventServiceImplementation.updateById(1L, event);

    // Verify the results
    assertEquals(400, response.getStatusCodeValue());
  }

  /**
   * Test the updateById method with a null transactions.
   */
  @Test
  void testUpdateByIdWithNullTransactions() {
    // Setup your test data and expectations
    Event event = new Event();
    event.setId(1);
    when(eventRepository.existsById(1L)).thenReturn(true);
    when(eventRepository.findById(1L)).thenReturn(Optional.of(event));
    when(eventRepository.save(event)).thenReturn(event);

    // Execute the method being tested
    ResponseEntity<Event> response = eventServiceImplementation.updateById(1L, event);

    // Verify the results
    assertEquals(400, response.getStatusCodeValue());
  }

  /**
   * Test the getPeople method with a valid id.
   */
  @Test
  void testGetPeople() {
    Event event = new Event();
    event.setId(1);
    when(eventRepository.existsById(1L)).thenReturn(true);
    when(eventRepository.findById(1L)).thenReturn(Optional.of(event));

    ResponseEntity<List<Person>> response = eventServiceImplementation.getPeople(1L);

    assertEquals(event.getPeople(), response.getBody());
  }

  /**
   * Test the add method with a valid id.
   */
  @Test
  void testAdd() {
    Event event = new Event("Event one", "Party", 1, "1234", new ArrayList<>(), new ArrayList<>());
    Person person = new Person("test@email.com", "First", "Test",
            "iban33");
    event.addPerson(person);
    when(eventRepository.existsById(1L)).thenReturn(true);
    when(eventRepository.findById(1L)).thenReturn(Optional.of(event));
    when(psi.add(person)).thenReturn(ResponseEntity.ok(person));
    when(eventRepository.save(event)).thenReturn(event);
    when(eventRepository.findById(1L)).thenReturn(Optional.of(event));
    ResponseEntity<Person> response = eventServiceImplementation.add(1L, person);

    assertEquals(person, response.getBody());
  }

  /**
   * Test the getExpenses method with a valid id.
   */
  @Test
  void testGetExpenses() {
    Event event = new Event();
    event.setId(1);
    when(eventRepository.existsById(1L)).thenReturn(true);
    when(eventRepository.findById(1L)).thenReturn(Optional.of(event));

    ResponseEntity<List<Transaction>> response = eventServiceImplementation.getExpenses(1L);

    assertEquals(event.getTransactions(), response.getBody());
  }

  /**
   * Test the createNewExpense method with a valid id.
   */
  @Test
  void testCreateNewExpense() {
    Event event = new Event("Event one", "Party", 1, "1234", new ArrayList<>(), new ArrayList<>());
    Person person = new Person("test@email.com", "First", "Test", "iban33");
    event.addPerson(person);

    Transaction transaction = new Transaction("test",
            LocalDate.of(Integer.parseInt("1970"), Integer.parseInt("10"), Integer.parseInt("10")),
            100, 947, "type22", new ArrayList<>(), person);
    List<Person> participants = new ArrayList<>();
    participants.add(person);
    transaction.setParticipants(participants);

    // Mock the getById method of PersonServiceImplementation to return a non-null value
    when(psi.getById(anyInt())).thenReturn(ResponseEntity.ok(person));

    // Mock the add method of TransactionServiceImplementation to return a non-null value
    when(tsi.add(any(Transaction.class))).thenReturn(ResponseEntity.ok(transaction));

    when(eventRepository.existsById(1L)).thenReturn(true);
    when(eventRepository.findById(1L)).thenReturn(Optional.of(event));
    when(eventRepository.save(event)).thenReturn(event);

    ResponseEntity<Transaction> response = eventServiceImplementation.createNewExpense(1L, transaction);

    assertEquals(transaction, response.getBody());
  }

  /**
   * Test the deleteById method with a valid id.
   */
  @Test
  void testDeleteById() {
    Event event = new Event();
    event.setId(1);
    when(eventRepository.existsById(1L)).thenReturn(true);
    when(eventRepository.findById(1L)).thenReturn(Optional.of(event));
    when(eventRepository.save(event)).thenReturn(event);

    ResponseEntity<Event> response = eventServiceImplementation.deleteById(1L);

    assertEquals(event, response.getBody());
  }

  /**
   * Test the deletePerson by id method with a valid id.
   */
  @Test
  void testDeletePerson() {
    Event event = new Event("Event one", "Party", 1, "1234", new ArrayList<>(), new ArrayList<>());
    Person person = new Person("test@email.com", "First", "Test", "iban33");
    event.addPerson(person);

    when(eventRepository.existsById(1L)).thenReturn(true);
    when(eventRepository.findById(1L)).thenReturn(Optional.of(event));
    when(eventRepository.save(event)).thenReturn(event);
    when(psi.deleteById(anyInt())).thenReturn(ResponseEntity.ok(person)); // Mock the deleteById method to return a non-null value
    when(psi.getById(1)).thenReturn(ResponseEntity.ok(person));

    ResponseEntity<Person> response = eventServiceImplementation.deletePerson(1L, 1);

    assertEquals(person, response.getBody());
  }

  /**
   * Test the deletePerson by id method with an invalid id.
   */
  @Test
  void testDeletePersonWithInvalidId() {
    Event event = new Event("Event one", "Party", 1, "1234", new ArrayList<>(), new ArrayList<>());
    Person person = new Person("test@email.com", "First", "Test", "iban33");
    event.addPerson(person);

    when(eventRepository.existsById(1L)).thenReturn(true);
    when(eventRepository.findById(1L)).thenReturn(Optional.of(event));
    when(eventRepository.save(event)).thenReturn(event);
    when(psi.deleteById(anyInt())).thenReturn(ResponseEntity.ok(person)); // Mock the deleteById method to return a non-null value
    when(psi.getById(1)).thenReturn(ResponseEntity.ok(person));

    ResponseEntity<Person> response = eventServiceImplementation.deletePerson(2L, 2);

    assertEquals(400, response.getStatusCodeValue());
  }

  /**
   * Test the deleteTransaction method with a valid id.
   */
  @Test
  void testDeleteTransaction() {
    Person person = new Person("test", "test", "test", "test");
    Event event = new Event("Event one", "Party", 1, "1234", new ArrayList<>(), new ArrayList<>());
    Transaction transaction = new Transaction("test",
            LocalDate.of(Integer.parseInt("1970"), Integer.parseInt("10"), Integer.parseInt("10")),
            100, 947, "type22", new ArrayList<>(), person);
    event.addTransaction(transaction);

    when(eventRepository.existsById(1L)).thenReturn(true);
    when(eventRepository.findById(1L)).thenReturn(Optional.of(event));
    when(eventRepository.save(event)).thenReturn(event);
    when(tsi.deleteById(anyInt())).thenReturn(ResponseEntity.ok(transaction)); // Mock the deleteById method to return a non-null value
    when(tsi.getById(1)).thenReturn(ResponseEntity.ok(transaction));
    when(psi.getById(0)).thenReturn(ResponseEntity.ok(person));

    ResponseEntity<Transaction> response = eventServiceImplementation.deleteTransaction(1L, 1);

    assertEquals(transaction, response.getBody());
  }

  /**
   * Test the getEventByToken method with valid id.
   */
  @Test
  void testGetEventByToken() {
    Event event = new Event();
    event.setId(1);
    when(eventRepository.findByToken("1234")).thenReturn(Optional.of(event));

    ResponseEntity<Event> response = eventServiceImplementation.getEventByToken("1234");

    assertEquals(event, response.getBody());
  }

  /**
   * Test updateTitleById method with valid id.
   */
  @Test
  void testUpdateTitleById() {
    Event event = new Event("Event one", "Party", 1, "1234", new ArrayList<>(), new ArrayList<>());
    when(eventRepository.existsById(1L)).thenReturn(true);
    when(eventRepository.findById(1L)).thenReturn(Optional.of(event));
    when(eventRepository.save(event)).thenReturn(event);
    Event event1 = new Event("Event one", "BOB", 1, "1234", new ArrayList<>(), new ArrayList<>());
    ResponseEntity<Event> response = eventServiceImplementation.updateTitleById(1L, event1);

    assertEquals(event1, response.getBody());
  }

  /**
   * Test add method with valid id.
   */
  @Test
  void testAdd1() {
    Event event = new Event("Event one", "Party", 1, "1234", new ArrayList<>(), new ArrayList<>());
    Person person = new Person("test", "test", "test", "test");
    when(eventRepository.existsById(1L)).thenReturn(true);
    when(eventRepository.findById(1L)).thenReturn(Optional.of(event));
    when(psi.add(person)).thenReturn(ResponseEntity.ok(person));
    when(eventRepository.save(event)).thenReturn(event);
    when(eventRepository.findById(1L)).thenReturn(Optional.of(event));
    ResponseEntity<Person> response = eventServiceImplementation.add(1L, person);

    assertEquals(person, response.getBody());
  }
}