package commons;

import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class PersonTemporaryTest {

    @Test
    void getEmailTest() {
        Person test = new Person("test@email.com", "First", "Test",
                "iban33", new Event("", "", 1, "", new HashSet<>(),
                new HashSet<>()), new HashSet<>(), new HashSet<>());
        assertEquals("test@email.com" ,test.getEmail());
    }

    @Test
    void getFirstNameTest() {
        Person test = new Person("test@email.com", "First", "Test",
                "iban33", new Event("", "", 1, "", new HashSet<>(),
                new HashSet<>()), new HashSet<>(), new HashSet<>());
        assertEquals("First" ,test.getFirstName());
    }

    @Test
    void getLastNameTest() {
        Person test = new Person("test@email.com", "First", "Test",
                "iban33", new Event("", "", 1, "", new HashSet<>(),
                new HashSet<>()), new HashSet<>(), new HashSet<>());
        assertEquals("Test" ,test.getLastName());
    }

    @Test
    void getIbanTest() {
        Person test = new Person("test@email.com", "First", "Test",
                "iban33", new Event("", "", 1, "", new HashSet<>(),
                new HashSet<>()), new HashSet<>(), new HashSet<>());
        assertEquals("iban33" ,test.getIban());
    }

    @Test
    void getEventIDTest() {
        Person test = new Person("test@email.com", "First", "Test",
                "iban33", new Event("", "", 1, "", new HashSet<>(),
                new HashSet<>()), new HashSet<>(), new HashSet<>());
        assertEquals(new Event("", "", 1, "", new HashSet<>(),
                new HashSet<>()) ,test.getEvent());
    }

    @Test
    void getDebtTest() {
        Person test = new Person("test@email.com", "First", "Test",
                "iban33", new Event("", "", 1, "", new HashSet<>(),
                new HashSet<>()), new HashSet<>(), new HashSet<>());
        assertEquals(0 ,test.getDebt());
    }

    @Test
    void testEquals() {
        Person test = new Person("test@email.com", "First", "Test",
                "iban33", new Event("", "", 1, "", new HashSet<>(),
                new HashSet<>()), new HashSet<>(), new HashSet<>());
        Person test2 = new Person("test@email.com", "First", "Test",
                "iban33", new Event("", "", 1, "", new HashSet<>(),
                new HashSet<>()), new HashSet<>(), new HashSet<>());
        assertEquals(test, test2);
    }

    @Test
    void testNotEquals() {
        Person test = new Person("test@email.com", "First", "Test",
                "iban33", new Event("", "", 1, "", new HashSet<>(),
                new HashSet<>()), new HashSet<>(), new HashSet<>());
        Person test2 = new Person("test2@email.com", "First", "Test",
                "iban33", new Event("", "", 1, "", new HashSet<>(),
                new HashSet<>()), new HashSet<>(), new HashSet<>());
        assertNotEquals(test, test2);
    }

    @Test
    void testNullEquals() {
        Person test = new Person("test@email.com", "First", "Test",
                "iban33", new Event("", "", 1, "", new HashSet<>(),
                new HashSet<>()), new HashSet<>(), new HashSet<>());
        assertNotEquals(test, null);
    }

    @Test
    void testSameEquals() {
        Person test = new Person("test@email.com", "First", "Test",
                "iban33", new Event("", "", 1, "", new HashSet<>(),
                new HashSet<>()), new HashSet<>(), new HashSet<>());
        assertEquals(test, test);
    }

    @Test
    void testDifferentEventEquals() {
        Person test = new Person("test@email.com", "First", "Test",
                "iban33", new Event("", "", 1, "", new HashSet<>(),
                new HashSet<>()), new HashSet<>(), new HashSet<>());
        Person test2 = new Person("test@email.com", "First", "Test",
                "iban33", new Event("", "", 2, "", new HashSet<>(),
                new HashSet<>()), new HashSet<>(), new HashSet<>());
        assertEquals(test, test2);
    }

    @Test
    void testHashCode() {
        Person test = new Person("test@email.com", "First", "Test",
                "iban33", new Event("", "", 1, "", new HashSet<>(),
                new HashSet<>()), new HashSet<>(), new HashSet<>());
        Person test2 = new Person("test@email.com", "First", "Test",
                "iban33", new Event("", "", 1, "", new HashSet<>(),
                new HashSet<>()), new HashSet<>(), new HashSet<>());
        assertEquals(test.hashCode(), test2.hashCode());
    }

    @Test
    void addDeptTest() {
        Person test = new Person("test@email.com", "First", "Test",
                "iban33", new Event("", "", 1, "", new HashSet<>(),
                new HashSet<>()), new HashSet<>(), new HashSet<>());
        test.addDept(3);
        assertEquals(3, test.getDebt());
    }
}