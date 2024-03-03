package commons;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
class EventTest {
    /**
     * Test the getTag method.
     */
    @Test
    void getTagTest(){
        Event test = new Event("Event one", "Party", 1, "1234", new HashSet<>(), new HashSet<>());
        assertEquals("Event one", test.getTag());
    }

    /**
     * Test the getTitle method.
     */
    @Test
    void getTitleTest(){
        Event test = new Event("Event one", "Party", 1, "1234", new HashSet<>(), new HashSet<>());
        assertEquals("Party", test.getTitle());
    }

    /**
     * Test the getId method.
     */
    @Test
    void getIdTest(){
        Event test = new Event("Event one", "Party", 1, "1234", new HashSet<>(), new HashSet<>());
        assertEquals(1, test.getId());
    }

    /**
     * Test the getToken method.
     */
    @Test
    void getTokenTest(){
        Event test = new Event("Event one", "Party", 1, "1234", new HashSet<>(), new HashSet<>());
        assertEquals("1234", test.getToken());
    }

    /**
     * Test the toString method.
     */
    @Test
    void toStringTest(){
        Event test = new Event("Event one", "Party", 1, "1234", new HashSet<>(), new HashSet<>());
        assertEquals("Event{tag='Event one', title='Party', id=1, token='1234'}", test.toString());
    }

    /**
     * Test the equals method with two similar events.
     */
    @Test
    void equalsTest(){
        Event test = new Event("Event one", "Party", 1, "1234", new HashSet<>(), new HashSet<>());
        Event test2 = new Event("Event one", "Party", 1, "1234", new HashSet<>(), new HashSet<>());
        assertEquals(test, test2);
    }

    /**
     * Test equals method with two events with different tags.
     */
    @Test
    void differentTagTest(){
        Event test = new Event("Event one", "Party", 1, "1234", new HashSet<>(), new HashSet<>());
        Event test2 = new Event("Event two", "Party", 1, "1234", new HashSet<>(), new HashSet<>());
        assertNotEquals(test, test2);
    }

    /**
     * Test equals method with two events with different titles.
     */
    @Test
    void differentTitleTest(){
        Event test = new Event("Event one", "Party", 1, "1234", new HashSet<>(), new HashSet<>());
        Event test2 = new Event("Event one", "BBQ", 1, "1234", new HashSet<>(), new HashSet<>());
        assertNotEquals(test, test2);
    }

    /**
     * Test equals method with two events with different id's.
     */
    @Test
    void differentIdTest(){
        Event test = new Event("Event one", "Party", 1, "1234", new HashSet<>(), new HashSet<>());
        Event test2 = new Event("Event one", "Party", 2, "1234", new HashSet<>(), new HashSet<>());
        assertNotEquals(test, test2);
    }

    @Test
    void differentPeopleTest(){
        Person p1 = new Person("test@email.com", "First", "Test",
                "iban33", new Event("", "", 1, "", new HashSet<>(),
                new HashSet<>()), new HashSet<>(), new HashSet<>());
        Person p2 = new Person("test@email.com", "First", "Test",
                "iban33", new Event("", "", 1, "", new HashSet<>(),
                new HashSet<>()), new HashSet<>(), new HashSet<>());

        Set<Person> people1 = new HashSet<>();
        people1.add(p1);
        Set<Person> people2 = new HashSet<>();
        people2.add(p2);

        Event test = new Event("Event one", "Party", 1, "1234", people1, new HashSet<>());
        Event test2 = new Event("Event one", "Party", 2, "1234", people2, new HashSet<>());
        assertNotEquals(test, test2);
    }

    @Test
    void differentTransactionsTest(){
        Transaction t1 = new Transaction("test",
                LocalDate.of(Integer.parseInt("1970"), Integer.parseInt("10"), Integer.parseInt("10")),
                100, 947, new HashSet<>(), new Person());
        Transaction t2 = new Transaction("test",
                LocalDate.of(Integer.parseInt("1970"), Integer.parseInt("10"), Integer.parseInt("10")),
                100, 957, new HashSet<>(), new Person());

        Set<Transaction> transactions1 = new HashSet<>();
        transactions1.add(t1);
        Set<Transaction> transactions2 = new HashSet<>();
        transactions2.add(t2);

        Event test = new Event("Event one", "Party", 1, "1234", new HashSet<>(), transactions1);
        Event test2 = new Event("Event one", "Party", 2, "1234", new HashSet<>(), transactions2);
        assertNotEquals(test, test2);
    }

    /**
     * Test equals method with two events with different tokens.
     */
    @Test
    void differentTokenTest(){
        Event test = new Event("Event one", "Party", 1, "1234", new HashSet<>(), new HashSet<>());
        Event test2 = new Event("Event one", "Party", 1, "4321", new HashSet<>(), new HashSet<>());
        assertNotEquals(test, test2);
    }

    /**
     * Test hashcode method with two similar events.
     */
    @Test
    void hashcodeTest(){
        Event test = new Event("Event one", "Party", 1, "1234", new HashSet<>(), new HashSet<>());
        Event test2 = new Event("Event one", "Party", 1, "1234", new HashSet<>(), new HashSet<>());
        assertEquals(test.hashCode(), test2.hashCode());
    }
}
