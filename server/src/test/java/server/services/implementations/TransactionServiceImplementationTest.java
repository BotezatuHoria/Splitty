package server.services.implementations;

import commons.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.async.DeferredResult;
import server.database.TransactionRepository;
import commons.Transaction;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class TransactionServiceImplementationTest {
  @Mock
  private TransactionRepository repo;
  @Mock
  private PersonServiceImplementation psi;
  @Mock
  private TransactionServiceImplementation service;

  /**
   * Set up for the mocks and the service.
   */
  @BeforeEach
  void setUp() {
    repo = Mockito.mock(TransactionRepository.class);
    psi = Mockito.mock(PersonServiceImplementation.class);
    service = new TransactionServiceImplementation(repo, psi);
  }

  /**
   * Test the getAll method when the list is empty.
   */
  @Test
  void getAllEmpty() {
    when(repo.findAll()).thenReturn(Collections.emptyList());

    ResponseEntity<List<Transaction>> response = service.getAll();

    assertEquals(ResponseEntity.noContent().build(), response);
  }

  /**
   * Test the getAll method when the list is not empty.
   */
  @Test
  void getAll() {
    Person person = new Person("test@email.com", "First", "Test", "iban33");
    Transaction transaction = new Transaction("test",
            LocalDate.of(Integer.parseInt("1970"), Integer.parseInt("10"), Integer.parseInt("10")),
            100, 947, "type22", new ArrayList<>(), person);
    when(repo.findAll()).thenReturn(Collections.singletonList(transaction));

    ResponseEntity<List<Transaction>> response = service.getAll();

    assertEquals(ResponseEntity.ok(Collections.singletonList(transaction)), response);
  }

  /**
   * Test the getById method when the transaction is not found.
   */
  @Test
  void getByIdNotFound() {
    when(repo.findById(1)).thenReturn(java.util.Optional.empty());

    ResponseEntity<Transaction> response = service.getById(1);

    assertEquals(ResponseEntity.notFound().build(), response);
  }

  /**
   * Test the add method when the transaction is null.
   */
  @Test
  void addNull() {
    ResponseEntity<Transaction> response = service.add(null);

    assertEquals(ResponseEntity.badRequest().build(), response);
  }

  /**
   * Test the add method when the transaction is invalid.
   */
  @Test
  void addInvalid() {
    Transaction transaction = new Transaction("test",
            LocalDate.of(Integer.parseInt("1970"), Integer.parseInt("10"), Integer.parseInt("10")),
            100, 947, "type22", new ArrayList<>(), null);

    ResponseEntity<Transaction> response = service.add(transaction);

    assertEquals(ResponseEntity.badRequest().build(), response);
  }

  /**
   * Test the add method when the transaction is valid.
   */
  @Test
  void addValidTransaction() {
    // Arrange
    Person creator = new Person("test@email.com", "First", "Test", "iban33");
    List<Person> participants = new ArrayList<>();
    participants.add(creator);
    Transaction validTransaction = new Transaction("test",
            LocalDate.of(1970, 10, 10),
            100, 947, "type22", participants, creator);

    when(repo.save(validTransaction)).thenReturn(validTransaction);

    // Act
    ResponseEntity<Transaction> response = service.add(validTransaction);

    // Assert
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(validTransaction, response.getBody());
  }

  /**
   * Test the deleteById method when the transaction is not found.
   */
  @Test
  void deleteByIdNotFound() {
    when(repo.existsById(1)).thenReturn(false);

    ResponseEntity<Transaction> response = service.deleteById(1);

    assertEquals(ResponseEntity.badRequest().build(), response);
  }

  /**
   * Test the deleteById method when the transaction is found.
   */
  @Test
  void deleteByIdValid() {
    // Arrange
    int validId = 1;
    Person creator = new Person("test@email.com", "First", "Test", "iban33");
    List<Person> participants = new ArrayList<>();
    participants.add(creator);
    Transaction validTransaction = new Transaction("test",
            LocalDate.of(1970, 10, 10),
            100, 947, "type22", participants, creator);

    when(repo.existsById(validId)).thenReturn(true);
    when(repo.findById(validId)).thenReturn(Optional.of(validTransaction));

    // Act
    ResponseEntity<Transaction> response = service.deleteById(validId);

    // Assert
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(validTransaction, response.getBody());
  }

  /**
   * Test the updateNameById method when the transaction is valid.
   */
  @Test
  void updateNameByIdValid() {
    // Arrange
    int validId = 1;
    String newName = "updatedName";
    Person creator = new Person("test@email.com", "First", "Test", "iban33");
    List<Person> participants = new ArrayList<>();
    participants.add(creator);
    Transaction validTransaction = new Transaction("test",
            LocalDate.of(1970, 10, 10),
            100, 947, "type22", participants, creator);

    when(repo.existsById(validId)).thenReturn(true);
    when(repo.findById(validId)).thenReturn(Optional.of(validTransaction));

    // Act
    ResponseEntity<Transaction> response = service.updateNameById(validId, newName);

    // Assert
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(newName, response.getBody().getName());
  }

  /**
   * Test the updateNameById method when the transaction is not found.
   */
  @Test
  void updateNameByIdNotFound() {
    when(repo.existsById(1)).thenReturn(false);

    ResponseEntity<Transaction> response = service.updateNameById(1, "updatedName");

    assertEquals(ResponseEntity.badRequest().build(), response);
  }

  /**
   * Test the updateParticipantsById method when the transaction is valid.
   */
  @Test
  void updateParticipantsByIdValid() {
    // Arrange
    int validId = 1;
    Person creator = new Person("test@email.com", "First", "Test", "iban33");
    List<Person> oldParticipants = new ArrayList<>();
    oldParticipants.add(creator);
    Transaction validTransaction = new Transaction("test",
            LocalDate.of(1970, 10, 10),
            100, 947, "type22", oldParticipants, creator);

    Person newParticipant = new Person("new@email.com", "New", "Participant", "iban44");
    List<Person> newParticipants = new ArrayList<>();
    newParticipants.add(newParticipant);

    when(repo.existsById(validId)).thenReturn(true);
    when(repo.findById(validId)).thenReturn(Optional.of(validTransaction));

    // Act
    ResponseEntity<Transaction> response = service.updateParticipantsById(validId, newParticipants);

    // Assert
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(newParticipants, response.getBody().getParticipants());
  }

  /**
   * Test the updateParticipantsById method when the transaction is not found.
   */
  @Test
  void updateParticipantsByIdNotFound() {
    when(repo.existsById(1)).thenReturn(false);

    ResponseEntity<Transaction> response = service.updateParticipantsById(1, new ArrayList<>());

    assertEquals(ResponseEntity.badRequest().build(), response);
  }

  /**
   * Test the updateById method when the transaction is invalid.
   */
  @Test
  void updateByIdInvalid() {
    Transaction transaction = new Transaction("test",
            LocalDate.of(Integer.parseInt("1970"), Integer.parseInt("10"), Integer.parseInt("10")),
            100, 947, "type22", new ArrayList<>(), null);

    ResponseEntity<Transaction> response = service.updateById(1, transaction);

    assertEquals(ResponseEntity.badRequest().build(), response);
  }

  /**
   * Test the updateById method when the transaction is valid.
   */
  @Test
  void updateByIdValid() {
    // Arrange
    int validId = 1;
    Person creator = new Person("test@email.com", "First", "Test", "iban33");
    List<Person> participants = new ArrayList<>();
    participants.add(creator);
    Transaction oldTransaction = new Transaction("test",
            LocalDate.of(1970, 10, 10),
            100, 947, "type22", participants, creator);

    Transaction newTransaction = new Transaction("updatedTest",
            LocalDate.of(1970, 10, 11),
            200, 948, "type23", participants, creator);

    when(repo.existsById(validId)).thenReturn(true);
    when(repo.findById(validId)).thenReturn(Optional.of(oldTransaction));
    when(psi.getById(creator.getId())).thenReturn(ResponseEntity.ok(creator));

    // Act
    ResponseEntity<Transaction> response = service.updateById(validId, newTransaction);

    // Assert
    assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  /**
   * Test the updateMoneyById method when.
   */
  @Test
  void updateMoneyByIdValid() {
    // Arrange
    int validId = 1;
    double newMoney = 200.0;
    Person creator = new Person("test@email.com", "First", "Test", "iban33");
    List<Person> participants = new ArrayList<>();
    participants.add(creator);
    Transaction validTransaction = new Transaction("test",
            LocalDate.of(1970, 10, 10),
            100, 947, "type22", participants, creator);

    when(repo.existsById(validId)).thenReturn(true);
    when(repo.findById(validId)).thenReturn(Optional.of(validTransaction));

    // Act
    ResponseEntity<Transaction> response = service.updateMoneyById(validId, newMoney);

    // Assert
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(newMoney, response.getBody().getMoney(), 0.001);
  }

  /**
   * Test the getUpdates method.
   */
  @Test
  void getUpdatesTest() {
    // Act
    DeferredResult<ResponseEntity<Transaction>> response = service.getUpdates();

    // Assert
    assertNotNull(response);
    assertNull(response.getResult());
  }

  /**
   * Test the getUpdates method with a good response.
   */
  @Test
  void getUpdatesGoodResponseTest() {
    // Arrange
    Person creator = new Person("test@email.com", "First", "Test", "iban33");
    List<Person> participants = new ArrayList<>();
    participants.add(creator);
    Transaction transaction = new Transaction("test",
            LocalDate.of(1970, 10, 10),
            100, 947, "type22", participants, creator);

    // Act
    DeferredResult<ResponseEntity<Transaction>> response = service.getUpdates();

    // Simulate a transaction update
    service.add(transaction);

    // Assert
    assertNotNull(response);
    assertEquals(ResponseEntity.ok(null), response.getResult());
  }

  @Test
  void isNullOrEmptyTest() {
    // Act & Assert
    assertTrue(TransactionServiceImplementation.isNullOrEmpty(null));
    assertTrue(TransactionServiceImplementation.isNullOrEmpty(""));
    assertFalse(TransactionServiceImplementation.isNullOrEmpty("test"));
  }

}