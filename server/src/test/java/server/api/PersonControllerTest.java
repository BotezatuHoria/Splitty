//package server.api;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.springframework.http.HttpStatus.BAD_REQUEST;
//
//import commons.Event;
//import commons.Person;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.util.ArrayList;
//
//public class PersonControllerTest {
//    private TestPersonRepository db;
//
//    private PersonController sut;
//    @BeforeEach
//    public void setup () {
//        db = new TestPersonRepository();
//        sut = new PersonController(db, new TransactionController(new TestTransactionRepository()));
//    }
//
//    @Test
//    public void cannotAddNullPerson() {
//        Person test = null;
//        var actual = sut.add(test);
//        assertEquals(BAD_REQUEST, actual.getStatusCode());
//    }
//
//    @Test
//    public void addPersonTest() {
//        Person test = new Person("test@email.com", "First", "Test",
//                "iban33", new Event("", "", 1, "", new ArrayList<>(),
//                new ArrayList<>()), new ArrayList<>(), new ArrayList<>());
//        var actual = sut.add(test);
//        assertEquals(actual.getBody(), test);
//    }
//
//    @Test
//    public void getPersonNegativeTest() {
//        var actual = sut.getById(-1);
//        assertEquals(BAD_REQUEST, actual.getStatusCode());
//    }
//
//    @Test
//    public void getPersonDoesNotExistTest() {
//        var actual = sut.getById(5);
//        assertEquals(BAD_REQUEST, actual.getStatusCode());
//    }
//
//    @Test
//    public void deletePersonNegativeTest() {
//        var actual = sut.deleteById(-1);
//        assertEquals(BAD_REQUEST, actual.getStatusCode());
//    }
//
//    @Test
//    public void deletePersonTest() {
//        Person test = new Person("test@email.com", "First", "Test",
//                "iban33", new Event("", "", 1, "", new ArrayList<>(),
//                new ArrayList<>()), new ArrayList<>(), new ArrayList<>());
//        sut.add(test);
//        var actual = sut.deleteById(0);
//        assertEquals(test, actual.getBody());
//        assertEquals(new ArrayList<>(), sut.getAll());
//    }
//
//    @Test
//    public void getPersonTest() {
//        Person test = new Person("test@email.com", "First", "Test",
//                "iban33", new Event("", "", 1, "", new ArrayList<>(),
//                new ArrayList<>()), new ArrayList<>(), new ArrayList<>());
//        sut.add(test);
//        var actual = sut.getById(0);
//        assertEquals(test, actual.getBody());
//    }
//
//    @Test
//    public void updatePersonNegativeTest() {
//        Person test = new Person("test@email.com", "First", "Test",
//                "iban33", new Event("", "", 1, "", new ArrayList<>(),
//                new ArrayList<>()), new ArrayList<>(), new ArrayList<>());
//        var actual = sut.updateById(-1, test);
//        assertEquals(BAD_REQUEST, actual.getStatusCode());
//    }
//
//    @Test
//    public void updatePersonDoesNotExistTest() {
//        Person test = new Person("test@email.com", "First", "Test",
//                "iban33", new Event("", "", 1, "", new ArrayList<>(),
//                new ArrayList<>()), new ArrayList<>(), new ArrayList<>());
//        sut.add(test);
//        var actual = sut.updateById(2, test);
//        assertEquals(BAD_REQUEST, actual.getStatusCode());
//    }
//
//    @Test
//    public void updatePersonTest() {
//        Person test = new Person("test@email.com", "First", "Test",
//                "iban33", new Event("", "", 1, "", new ArrayList<>(),
//               new ArrayList<>()), new ArrayList<>(), new ArrayList<>());
//
//        Person test2 = new Person("test2@gmail.com", "First", "Test",
//                "iban33", new Event("", "", 1, "", new ArrayList<>(),
//                new ArrayList<>()), new ArrayList<>(), new ArrayList<>());
//        sut.add(test);
//        var actual = sut.updateById(0, test2);
//        assertEquals(actual.getBody(), test2);
//        assertEquals(actual.getBody(), sut.getById(0).getBody());
//    }
//}