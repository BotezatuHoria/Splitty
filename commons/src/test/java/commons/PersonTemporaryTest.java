package commons;

import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class PersonTemporaryTest {

    @Test
    void getEmailTest() {
        PersonTemporary test = new PersonTemporary("test@email.com", "First", "Test",
                "iban33", new Event("", "", 1, "", new HashSet<>(),
                new HashSet<>()), new HashSet<>(), new HashSet<>());
        assertEquals("test@email.com" ,test.getEmail());
    }

    @Test
    void getFirstNameTest() {
        PersonTemporary test = new PersonTemporary("test@email.com", "First", "Test",
                "iban33", new Event("", "", 1, "", new HashSet<>(),
                new HashSet<>()), new HashSet<>(), new HashSet<>());
        assertEquals("First" ,test.getFirstName());
    }

    @Test
    void getLastNameTest() {
        PersonTemporary test = new PersonTemporary("test@email.com", "First", "Test",
                "iban33", new Event("", "", 1, "", new HashSet<>(),
                new HashSet<>()), new HashSet<>(), new HashSet<>());
        assertEquals("Test" ,test.getLastName());
    }

    @Test
    void getIbanTest() {
        PersonTemporary test = new PersonTemporary("test@email.com", "First", "Test",
                "iban33", new Event("", "", 1, "", new HashSet<>(),
                new HashSet<>()), new HashSet<>(), new HashSet<>());
        assertEquals("iban33" ,test.getIban());
    }

    @Test
    void getEventIDTest() {
        PersonTemporary test = new PersonTemporary("test@email.com", "First", "Test",
                "iban33", new Event("", "", 1, "", new HashSet<>(),
                new HashSet<>()), new HashSet<>(), new HashSet<>());
        assertEquals(new Event("", "", 1, "", new HashSet<>(),
                new HashSet<>()) ,test.getEvent());
    }

    @Test
    void getDebtTest() {
        PersonTemporary test = new PersonTemporary("test@email.com", "First", "Test",
                "iban33", new Event("", "", 1, "", new HashSet<>(),
                new HashSet<>()), new HashSet<>(), new HashSet<>());
        assertEquals(0 ,test.getDebt());
    }

    @Test
    void testEquals() {
        PersonTemporary test = new PersonTemporary("test@email.com", "First", "Test",
                "iban33", new Event("", "", 1, "", new HashSet<>(),
                new HashSet<>()), new HashSet<>(), new HashSet<>());
        PersonTemporary test2 = new PersonTemporary("test@email.com", "First", "Test",
                "iban33", new Event("", "", 1, "", new HashSet<>(),
                new HashSet<>()), new HashSet<>(), new HashSet<>());
        assertEquals(test, test2);
    }

    @Test
    void testNotEquals() {
        PersonTemporary test = new PersonTemporary("test@email.com", "First", "Test",
                "iban33", new Event("", "", 1, "", new HashSet<>(),
                new HashSet<>()), new HashSet<>(), new HashSet<>());
        PersonTemporary test2 = new PersonTemporary("test2@email.com", "First", "Test",
                "iban33", new Event("", "", 1, "", new HashSet<>(),
                new HashSet<>()), new HashSet<>(), new HashSet<>());
        assertNotEquals(test, test2);
    }

    @Test
    void testNullEquals() {
        PersonTemporary test = new PersonTemporary("test@email.com", "First", "Test",
                "iban33", new Event("", "", 1, "", new HashSet<>(),
                new HashSet<>()), new HashSet<>(), new HashSet<>());
        assertNotEquals(test, null);
    }

    @Test
    void testSameEquals() {
        PersonTemporary test = new PersonTemporary("test@email.com", "First", "Test",
                "iban33", new Event("", "", 1, "", new HashSet<>(),
                new HashSet<>()), new HashSet<>(), new HashSet<>());
        assertEquals(test, test);
    }

    @Test
    void testDifferentEventEquals() {
        PersonTemporary test = new PersonTemporary("test@email.com", "First", "Test",
                "iban33", new Event("", "", 1, "", new HashSet<>(),
                new HashSet<>()), new HashSet<>(), new HashSet<>());
        PersonTemporary test2 = new PersonTemporary("test@email.com", "First", "Test",
                "iban33", new Event("", "", 2, "", new HashSet<>(),
                new HashSet<>()), new HashSet<>(), new HashSet<>());
        assertEquals(test, test2);
    }

    @Test
    void testHashCode() {
        PersonTemporary test = new PersonTemporary("test@email.com", "First", "Test",
                "iban33", new Event("", "", 1, "", new HashSet<>(),
                new HashSet<>()), new HashSet<>(), new HashSet<>());
        PersonTemporary test2 = new PersonTemporary("test@email.com", "First", "Test",
                "iban33", new Event("", "", 1, "", new HashSet<>(),
                new HashSet<>()), new HashSet<>(), new HashSet<>());
        assertEquals(test.hashCode(), test2.hashCode());
    }

    @Test
    void addDeptTest() {
        PersonTemporary test = new PersonTemporary("test@email.com", "First", "Test",
                "iban33", new Event("", "", 1, "", new HashSet<>(),
                new HashSet<>()), new HashSet<>(), new HashSet<>());
        test.addDept(3);
        assertEquals(3, test.getDebt());
    }
}