package commons;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PersonTemporaryTest {

    @Test
    void getEmailTest() {
        PersonTemporary test = new PersonTemporary("test@email.com", "First", "Test", "iban33", 33);
        assertEquals("test@email.com" ,test.getEmail());
    }

    @Test
    void getFirstNameTest() {
        PersonTemporary test = new PersonTemporary("test@email.com", "First", "Test", "iban33", 33);
        assertEquals("First" ,test.getFirstName());
    }

    @Test
    void getLastNameTest() {
        PersonTemporary test = new PersonTemporary("test@email.com", "First", "Test", "iban33", 33);
        assertEquals("Test" ,test.getLastName());
    }

    @Test
    void getIbanTest() {
        PersonTemporary test = new PersonTemporary("test@email.com", "First", "Test", "iban33", 33);
        assertEquals("iban33" ,test.getIban());
    }

    @Test
    void getEventIDTest() {
        PersonTemporary test = new PersonTemporary("test@email.com", "First", "Test", "iban33", 33);
        assertEquals(33 ,test.getEventID());
    }

    @Test
    void getDebtTest() {
        PersonTemporary test = new PersonTemporary("test@email.com", "First", "Test", "iban33", 33);
        assertEquals(0 ,test.getDebt());
    }

    @Test
    void testEquals() {
        PersonTemporary test = new PersonTemporary("test@email.com", "First", "Test", "iban33", 33);
        PersonTemporary test2 = new PersonTemporary("test@email.com", "First", "Test", "iban33", 33);
        assertEquals(test, test2);
    }

    @Test
    void testNotEquals() {
        PersonTemporary test = new PersonTemporary("test@email.com", "First", "Test", "iban33", 33);
        PersonTemporary test2 = new PersonTemporary("test2@email.com", "First", "Test", "iban33", 33);
        assertNotEquals(test, test2);
    }

    @Test
    void testNullEquals() {
        PersonTemporary test = new PersonTemporary("test@email.com", "First", "Test", "iban33", 33);
        assertNotEquals(test, null);
    }

    @Test
    void testSameEquals() {
        PersonTemporary test = new PersonTemporary("test@email.com", "First", "Test", "iban33", 33);
        assertEquals(test, test);
    }

    @Test
    void testDifferentEventEquals() {
        PersonTemporary test = new PersonTemporary("test@email.com", "First", "Test", "iban33", 33);
        PersonTemporary test2 = new PersonTemporary("test@email.com", "First", "Test", "iban33", 44);
        assertEquals(test, test2);
    }

    @Test
    void testHashCode() {
        PersonTemporary test = new PersonTemporary("test@email.com", "First", "Test", "iban33", 33);
        PersonTemporary test2 = new PersonTemporary("test@email.com", "First", "Test", "iban33", 33);
        assertEquals(test.hashCode(), test2.hashCode());
    }

    @Test
    void addDeptTest() {
        PersonTemporary test = new PersonTemporary("test@email.com", "First", "Test", "iban33", 33);
        test.addDept(3);
        assertEquals(3, test.getDebt());
    }
}