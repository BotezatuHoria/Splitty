package commons;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PersonTest {

    @Test
    void getEmailTest() {
        Person test = new Person("test@email.com", "First", "Test",
                "iban33");
        assertEquals("test@email.com" ,test.getEmail());
    }

    @Test
    void getFirstNameTest() {
        Person test = new Person("test@email.com", "First", "Test",
                "iban33");
        assertEquals("First" ,test.getFirstName());
    }

    @Test
    void getLastNameTest() {
        Person test = new Person("test@email.com", "First", "Test",
                "iban33");
        assertEquals("Test" ,test.getLastName());
    }

    @Test
    void getIbanTest() {
        Person test = new Person("test@email.com", "First", "Test",
                "iban33");
        assertEquals("iban33" ,test.getIban());
    }

    @Test
    void getDebtTest() {
        Person test = new Person("test@email.com", "First", "Test",
                "iban33");
        assertEquals(0 ,test.getDebt());
    }

    @Test
    void testEquals() {
        Person test = new Person("test@email.com", "First", "Test",
                "iban33");
        Person test2 = new Person("test@email.com", "First", "Test",
                "iban33");
        assertEquals(test, test2);
    }

    @Test
    void testNullEquals() {
        Person test = new Person("test@email.com", "First", "Test",
                "iban33");
        assertNotEquals(test, null);
    }

    @Test
    void testSameEquals() {
        Person test = new Person("test@email.com", "First", "Test",
                "iban33");
        assertEquals(test, test);
    }

    @Test
    void testHashCode() {
        Person test = new Person("test@email.com", "First", "Test",
                "iban33");
        Person test2 = new Person("test@email.com", "First", "Test",
                "iban33");
        assertEquals(test.hashCode(), test2.hashCode());
    }

    @Test
    void addDeptTest() {
        Person test = new Person("test@email.com", "First", "Test",
                "iban33");
        test.addDept(3);
        assertEquals(3, test.getDebt());
    }
}