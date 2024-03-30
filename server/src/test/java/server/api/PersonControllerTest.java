package server.api;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.HttpStatus.*;

import commons.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import server.services.implementations.PersonServiceImplementation;

public class PersonControllerTest {
    private TestPersonRepository db;

    private PersonServiceImplementation sut;

    private SimpMessagingTemplate messagingTemplate;
    @BeforeEach
    public void setup () {
        db = new TestPersonRepository();
        messagingTemplate = new SimpMessagingTemplate(new MessageChannel() {
            @Override
            public boolean send(Message<?> message, long timeout) {
                return true;
            }
        });
        sut = new PersonServiceImplementation(db, messagingTemplate);
    }

    @Test
    public void cannotAddNullPerson() {
        Person test = null;
        var actual = sut.add(test);
        assertEquals(BAD_REQUEST, actual.getStatusCode());
    }

    @Test
    public void addPersonTest() {
        Person test = new Person("test@email.com", "First", "Test",
                "iban33");
        var actual = sut.add(test);
        assertEquals(actual.getBody(), test);
    }

    @Test
    public void getPersonNegativeTest() {
        var actual = sut.getById(-1);
        assertEquals(NOT_FOUND, actual.getStatusCode());
    }

    @Test
    public void getPersonDoesNotExistTest() {
        var actual = sut.getById(5);
        assertEquals(NOT_FOUND, actual.getStatusCode());
    }

    @Test
    public void deletePersonNegativeTest() {
        var actual = sut.deleteById(-1);
        assertEquals(BAD_REQUEST, actual.getStatusCode());
    }

    @Test
    public void deletePersonTest() {
        Person test = new Person("test@email.com", "First", "Test",
                "iban33");
        sut.add(test);
        var actual = sut.deleteById(0);
        assertEquals(test, actual.getBody());
        assertEquals(NO_CONTENT, sut.getAll().getStatusCode());
    }

    @Test
    public void getPersonTest() {
        Person test = new Person("test@email.com", "First", "Test",
                "iban33");
        sut.add(test);
        var actual = sut.getById(0);
        assertEquals(test, actual.getBody());
    }

    @Test
    public void updatePersonNegativeTest() {
        Person test = new Person("test@email.com", "First", "Test",
                "iban33");
        var actual = sut.updateById(-1, test);
        assertEquals(BAD_REQUEST, actual.getStatusCode());
    }

    @Test
    public void updatePersonDoesNotExistTest() {
        Person test = new Person("test@email.com", "First", "Test",
                "iban33");
        sut.add(test);
        var actual = sut.updateById(2, test);
        assertEquals(BAD_REQUEST, actual.getStatusCode());
    }

    @Test
    public void updatePersonTest() {
        Person test = new Person("test@email.com", "First", "Test",
                "iban33");

        Person test2 = new Person("test2@gmail.com", "First", "Test",
                "iban33");
        sut.add(test);
        var actual = sut.updateById(0, test2);
        assertEquals(actual.getBody(), test2);
        assertEquals(actual.getBody(), sut.getById(0).getBody());
    }
}