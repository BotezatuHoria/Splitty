package server.api;

import commons.Event;
import commons.Person;
import commons.Transaction;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.HttpStatus.BAD_REQUEST;


class TransactionControllerTest {

    private TestTransactionRepository db;
    private TransactionController sut;
    private Person person1;
    private Person person2;
    private List<Person> participants;
    private Transaction t;

    @BeforeEach
    public void setup () {
        db = new TestTransactionRepository();
        sut = new TransactionController(db);

        // Create a sample transaction
        participants = new ArrayList<>();
        person1 = new Person("test@email.com", "First", "Test",
                "iban33", new Event("", "", 1, "", new ArrayList<>(),
                new ArrayList<>()), new ArrayList<>(), new ArrayList<>());

        person2 = new Person("test@email.com", "First", "Test",
                "iban33", new Event("", "", 2, "", new ArrayList<>(),
                new ArrayList<>()), new ArrayList<>(), new ArrayList<>());

        participants.add(person1);
        participants.add(person2);

        t = new Transaction("test",
                LocalDate.of(Integer.parseInt("1970"), Integer.parseInt("10"), Integer.parseInt("10")),
                100, 947,participants, person1, "Euro");
    }
    @Test
    public void getTransactionNullTest() {
        var actual = sut.getById(-14);
        assertEquals(BAD_REQUEST, actual.getStatusCode());
    }
    @Test
    public void deleteTransactionNullTest() {
        var actual = sut.deleteById(-1);
        assertEquals(BAD_REQUEST, actual.getStatusCode());
    }

    @Test
    public void cannotAddNullTransaction() {
        Transaction test = null;
        var actual = sut.add(test);
        assertEquals(BAD_REQUEST, actual.getStatusCode());
    }

    @Test
    public void testGetByInvalidId() {
        int invalidID = -1;
        assertEquals(sut.getById(invalidID), ResponseEntity.badRequest().build());
    }
    @Test
    public void testAddValidTransaction() {
        // Act
        ResponseEntity<Transaction> actualResponse = sut.add(t);

        // Assert
        assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        assertEquals(t, actualResponse.getBody());
    }

    /**
     * Tests adding transaction without participants.
     */
    @Test
    public void testAddInvalidTransaction() {
        List<Person> list = new ArrayList<>();
        Person personTest = new Person("test@email.com", "First", "Test",
                "iban33", new Event("", "", 1, "", new ArrayList<>(),
                new ArrayList<>()), new ArrayList<>(), new ArrayList<>());

        Person personTest2 = new Person("test@email.com", "First", "Test",
                "iban33", new Event("", "", 2, "", new ArrayList<>(),
                new ArrayList<>()), new ArrayList<>(), new ArrayList<>());

        list.add(person1);
        list.add(person2);

        Transaction test = new Transaction("test",
                LocalDate.of(Integer.parseInt("1970"), Integer.parseInt("10"), Integer.parseInt("10")),
                100, 947,list, null, "Euro");
        // Act
        ResponseEntity<Transaction> actualResponse = sut.add(test);

        // Assert
        assertEquals(BAD_REQUEST, actualResponse.getStatusCode());
    }

    /**
     * Tests adding null instead of Transaction.
     */
    @Test
    public void testAddNull() {
        // Act
        ResponseEntity<Transaction> actualResponse = sut.add(null);

        // Assert
        assertEquals(BAD_REQUEST, actualResponse.getStatusCode());
    }

    /**
     * Tests deleting Transaction by its id.
     */
    @Test
    public void testDeleteValidTransaction() {
        // Save the transaction to the repository
        sut.add(t);

        // Act: Delete the transaction by ID
        ResponseEntity<Transaction> actualResponse = sut.deleteById(t.getId());

        // Assert
        assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        assertEquals(t, actualResponse.getBody());
    }

    /**
     * Tests updating the name of already existing Transaction.
     */
    @Test
    public void testUpdateNameValidTransaction() {
        // Save the transaction to the database
        sut.add(t);

        // New name for the transaction
        String newName = "New Transaction Name";

        // Act: Update the name of the transaction by ID
        ResponseEntity<Transaction> actualResponse = sut.updateNameById(t.getId(), newName);

        // Assert
        assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        assertEquals(newName, actualResponse.getBody().getName());
    }

    /**
     * Tests updating all the values of the transaction at once.
     */
    @Test
    public void testUpdateAllValuesOfTransactionById() {

        // Save the transaction to the database
        sut.add(t);

        // New data for the transaction
        String newName = "New Transaction Name";
        LocalDate newDate = LocalDate.of(2004,10,27);
        int newMoney = 70;
        int newCurrency = 1001;
        List<Person> newParticipants = new ArrayList<>();
        newParticipants.add(person2);
        String newExpenseType = "Dollars";
        Transaction newTransaction = new Transaction(newName, newDate,newMoney, newCurrency, newParticipants, person2, newExpenseType);

        // Act: Update all the data of the transaction by ID
        ResponseEntity<Transaction> actualResponse = sut.updateById(1, newTransaction);

        // Assert
        assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        assertEquals(newTransaction.toString(), actualResponse.getBody().toString());
    }
    /**
     * Tests updating all the values of the transaction at once.
     */
    @Test
    public void testUpdateAllValuesOfTransactionByIdIncorrectly() {
        // Save the transaction to the database
        sut.add(t);

        // New data for the transaction
        String newName = "New Transaction Name";
        LocalDate newDate = LocalDate.of(2004,10,27);
        int newMoney = 70;
        int newCurrency = 1001;
        List<Person> newParticipants = new ArrayList<>();
        newParticipants.add(person2);
        Transaction newTransaction = new Transaction(newName, null ,newMoney, newCurrency, newParticipants, person2, null);

        // Act: Update all the data of the transaction by ID
        ResponseEntity<Transaction> actualResponse = sut.updateById(1, newTransaction);

        // Assert
        assertEquals(BAD_REQUEST, actualResponse.getStatusCode());
    }
    /**
     * Tests updating the participants of already existing Transaction.
     */
    @Test
    public void testUpdateParticipants() {


        // Save the transaction to the database
        sut.add(t);

        // New participants for the transaction
        Person person3 = new Person("tesshfh@l.com", "BOB", "Kevin",
                "iban33", new Event("", "", 3, "", new ArrayList<>(),
                new ArrayList<>()), new ArrayList<>(),new ArrayList<>());

        participants.add(person3);
        // Act: Update the participants of the transaction by ID
        ResponseEntity<Transaction> actualResponse = sut.updateParticipantsById(t.getId(), participants);

        // Assert
        assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        assertEquals(participants, actualResponse.getBody().getParticipants());
    }

    /**
     * Tests updating the participants of already existing Transaction.
     */
    @Test
    public void testUpdateMoney() {
        // Save the transaction to the database
        sut.add(t);

        // New participants for the transaction
        int newMoney = 999999;

        // Act: Update the participants of the transaction by ID
        ResponseEntity<Transaction> actualResponse = sut.updateMoneyById(t.getId(), newMoney);

        // Assert
        assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        assertEquals(newMoney, actualResponse.getBody().getMoney());
    }
    /**
     * Tests updating the participants of already existing Transaction.
     */
    @Test
    public void testGetAll() {
        Person person3 = new Person("tesshfh@l.com", "BOB", "Kevin",
                "iban33", new Event("", "", 3, "", new ArrayList<>(),
                new ArrayList<>()), new ArrayList<>(), new ArrayList<>());
        participants.add(person3);
        Transaction t1 = new Transaction("test1",
                LocalDate.of(Integer.parseInt("1970"), Integer.parseInt("12"), Integer.parseInt("10")),
                1055550, 95747,participants, person3, "Egsuro");

        // Save the transaction to the database
        sut.add(t);
        sut.add(t1);
        List<Transaction> expectedList = new ArrayList<>();
        expectedList.add(t);
        expectedList.add(t1);

        // Act: Update the participants of the transaction by ID
        List<Transaction> actualResponse = sut.getAll();

        // Assert
        assertEquals(expectedList.get(0), actualResponse.get(0));
        assertEquals(expectedList.get(1), actualResponse.get(1));
    }

}