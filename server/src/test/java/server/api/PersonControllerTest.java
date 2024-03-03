package server.api;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

import commons.Event;
import commons.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;

public class PersonControllerTest {
    private TestPersonRepository db;

    private PersonController sut;
    @BeforeEach
    public void setup () {
        db = new TestPersonRepository();
        sut = new PersonController(db);
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
                "iban33", new Event("", "", 1, "", new HashSet<>(),
                new HashSet<>()), new HashSet<>(), new HashSet<>());
        var actual = sut.add(test);
        assertEquals(actual.getBody(), test);
    }

    @Test
    public void getPersonNullTest() {
        var actual = sut.getById(null);
        assertEquals(BAD_REQUEST, actual.getStatusCode());
    }

    @Test
    public void deletePersonNullTest() {
        var actual = sut.deleteById(null);
        assertEquals(BAD_REQUEST, actual.getStatusCode());
    }

    @Test
    public void deletePersonTest() {
        Person test = new Person("test@email.com", "First", "Test",
                "iban33", new Event("", "", 1, "", new HashSet<>(),
                new HashSet<>()), new HashSet<>(), new HashSet<>());
        sut.add(test);
        var actual = sut.deleteById("test@email.com");
        assertEquals(test, actual.getBody());
        assertEquals(new ArrayList<>(), sut.getAll());
    }

    @Test
    public void getPersonTest() {
        Person test = new Person("test@email.com", "First", "Test",
                "iban33", new Event("", "", 1, "", new HashSet<>(),
                new HashSet<>()), new HashSet<>(), new HashSet<>());
        sut.add(test);
        var actual = sut.getById("test@email.com");
        assertEquals(test, actual.getBody());
    }
}