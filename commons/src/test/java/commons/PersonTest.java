package commons;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PersonTest {

    @Test
    void getEmailTest() {
        Person test = new Person("test@email.com", "First", "Test",
                "iban33", new Event("", "", 1, "", new ArrayList<>(),
                new ArrayList<>()), new ArrayList<>(), new ArrayList<>());
        assertEquals("test@email.com" ,test.getEmail());
    }

    @Test
    void getFirstNameTest() {
        Person test = new Person("test@email.com", "First", "Test",
                "iban33", new Event("", "", 1, "", new ArrayList<>(),
                new ArrayList<>()), new ArrayList<>(), new ArrayList<>());
        assertEquals("First" ,test.getFirstName());
    }

    @Test
    void getLastNameTest() {
        Person test = new Person("test@email.com", "First", "Test",
                "iban33", new Event("", "", 1, "", new ArrayList<>(),
                new ArrayList<>()), new ArrayList<>(), new ArrayList<>());
        assertEquals("Test" ,test.getLastName());
    }

    @Test
    void getIbanTest() {
        Person test = new Person("test@email.com", "First", "Test",
                "iban33", new Event("", "", 1, "", new ArrayList<>(),
                new ArrayList<>()), new ArrayList<>(), new ArrayList<>());
        assertEquals("iban33" ,test.getIban());
    }

    @Test
    void getEventIDTest() {
        Person test = new Person("test@email.com", "First", "Test",
                "iban33", new Event("", "", 1, "", new ArrayList<>(),
                new ArrayList<>()), new ArrayList<>(), new ArrayList<>());
        Event event = test.getEvent();
    }

    @Test
    void getDebtTest() {
        Person test = new Person("test@email.com", "First", "Test",
                "iban33", new Event("", "", 1, "", new ArrayList<>(),
                new ArrayList<>()), new ArrayList<>(), new ArrayList<>());
        assertEquals(0 ,test.getDebt());
    }

    @Test
    void testEquals() {
        Person test = new Person("test@email.com", "First", "Test",
                "iban33", new Event("", "", 1, "", new ArrayList<>(),
                new ArrayList<>()), new ArrayList<>(), new ArrayList<>());
        Person test2 = new Person("test@email.com", "First", "Test",
                "iban33", new Event("", "", 1, "", new ArrayList<>(),
                new ArrayList<>()), new ArrayList<>(), new ArrayList<>());
        assertEquals(test, test2);
    }

    @Test
    void testNotEquals() {
        Person test = new Person("test@email.com", "First", "Test",
                "iban33", new Event("", "", 1, "", new ArrayList<>(),
                new ArrayList<>()), new ArrayList<>(), new ArrayList<>());
        Person test2 = new Person("test2@email.com", "First", "Test",
                "iban33", new Event("", "", 1, "", new ArrayList<>(),
                new ArrayList<>()), new ArrayList<>(), new ArrayList<>());
        assertNotEquals(test, test2);
    }

    @Test
    void testNullEquals() {
        Person test = new Person("test@email.com", "First", "Test",
                "iban33", new Event("", "", 1, "", new ArrayList<>(),
                new ArrayList<>()), new ArrayList<>(), new ArrayList<>());
        assertNotEquals(test, null);
    }

    @Test
    void testSameEquals() {
        Person test = new Person("test@email.com", "First", "Test",
                "iban33", new Event("", "", 1, "", new ArrayList<>(),
                new ArrayList<>()), new ArrayList<>(), new ArrayList<>());
        assertEquals(test, test);
    }

    @Test
    void testDifferentEventEquals() {
        Person test = new Person("test@email.com", "First", "Test",
                "iban33", new Event("", "", 1, "", new ArrayList<>(),
                new ArrayList<>()), new ArrayList<>(), new ArrayList<>());
        Person test2 = new Person("test@email.com", "First", "Test",
                "iban33", new Event("", "", 2, "", new ArrayList<>(),
                new ArrayList<>()), new ArrayList<>(), new ArrayList<>());
        assertNotEquals(test, test2);
    }

    @Test
    void testHashCode() {
        Person test = new Person("test@email.com", "First", "Test",
                "iban33", new Event("", "", 1, "", new ArrayList<>(),
                new ArrayList<>()), new ArrayList<>(), new ArrayList<>());
        Person test2 = new Person("test@email.com", "First", "Test",
                "iban33", new Event("", "", 1, "", new ArrayList<>(),
                new ArrayList<>()), new ArrayList<>(), new ArrayList<>());
        assertEquals(test.hashCode(), test2.hashCode());
    }

    @Test
    void addDeptTest() {
        Person test = new Person("test@email.com", "First", "Test",
                "iban33", new Event("", "", 1, "", new ArrayList<>(),
                new ArrayList<>()), new ArrayList<>(), new ArrayList<>());
        test.addDept(3);
        assertEquals(3, test.getDebt());
    }
}