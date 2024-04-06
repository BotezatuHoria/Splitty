package commons;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
class EventTest {
    /**
     * Test the getTag method.
     */
    @Test
    void getTagTest(){
        Event test = new Event("Event one", "Party", 1, "1234", new ArrayList<>(), new ArrayList<>());
        assertEquals("Event one", test.getTag());
    }

    @Test
    void emptyConstructorTest() {
        Event test = new Event();
        assertEquals(test.getTitle(), null);
    }

    @Test
    void getTransactionTest() {
        Event test = new Event("Event one", "Party", 1, "1234", new ArrayList<>(), new ArrayList<>());
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction("test",
                LocalDate.of(Integer.parseInt("1970"), Integer.parseInt("10"), Integer.parseInt("10")),
                100, 947, new ArrayList<>(), new Person(), null));
        test.setTransactions(transactions);
        assertEquals(test.getTransactions(), transactions);
    }

    @Test
    void getPeopleTest() {
        Event test = new Event("Event one", "Party", 1, "1234", new ArrayList<>(), new ArrayList<>());
        List<Person> people = new ArrayList<>();
        people.add(new Person("test@email.com", "First", "Test",
                "iban33"));
        test.setPeople(people);
        assertEquals(test.getPeople(), people);
    }


    /**
     * Test the getTitle method.
     */
    @Test
    void getTitleTest(){
        Event test = new Event("Event one", "Party", 1, "1234", new ArrayList<>(), new ArrayList<>());
        test.setTitle("Party");
        assertEquals("Party", test.getTitle());
    }

    /**
     * Test the getId method.
     */
    @Test
    void getIdTest(){
        Event test = new Event("Event one", "Party", 1, "1234", new ArrayList<>(), new ArrayList<>());
        assertEquals(1, test.getId());
    }

    /**
     * Test the getToken method.
     */
    @Test
    void getTokenTest(){
        Event test = new Event("Event one", "Party", 1, "1234", new ArrayList<>(), new ArrayList<>());
        test.setToken("1234");
        assertEquals("1234", test.getToken());
    }

    @Test
    void addPersonTest() {
        Event test = new Event("Event one", "Party", 1, "1234", new ArrayList<>(), new ArrayList<>());
        Person person = new Person("test@email.com", "First", "Test",
                "iban33");
        test.addPerson(person);
        assertTrue(test.getPeople().contains(person));
    }

    @Test
    void removePersonTest() {
        Event test = new Event("Event one", "Party", 1, "1234", new ArrayList<>(), new ArrayList<>());
        Person person = new Person("test@email.com", "First", "Test",
                "iban33");
        test.addPerson(person);
        test.removePerson(person);
        assertTrue(!test.getPeople().contains(person));
    }

    @Test
    void addTransactionTest() {
        Event test = new Event("Event one", "Party", 1, "1234", new ArrayList<>(), new ArrayList<>());
        Transaction transaction = new Transaction("test",
                LocalDate.of(Integer.parseInt("1970"), Integer.parseInt("10"), Integer.parseInt("10")),
                100, 947, new ArrayList<>(), new Person(), null);
        test.addTransaction(transaction);
        assertTrue(test.getTransactions().contains(transaction));

    }


    @Test
    void removeTransactionTest() {
        Event test = new Event("Event one", "Party", 1, "1234", new ArrayList<>(), new ArrayList<>());
        Transaction transaction = new Transaction("test",
                LocalDate.of(Integer.parseInt("1970"), Integer.parseInt("10"), Integer.parseInt("10")),
                100, 947, new ArrayList<>(), new Person(), null);
        test.addTransaction(transaction);
        test.removeTransaction(transaction);
        assertTrue(!test.getTransactions().contains(transaction));

    }

    @Test
    void removeTransactionsTest() {
        Event test = new Event("Event one", "Party", 1, "1234", new ArrayList<>(), new ArrayList<>());
        Transaction transaction = new Transaction("test",
                LocalDate.of(Integer.parseInt("1970"), Integer.parseInt("10"), Integer.parseInt("10")),
                100, 947, new ArrayList<>(), new Person(), null);
        test.addTransaction(transaction);
        test.removeTransactions();
        assertTrue(test.getTransactions().isEmpty());
        test.setTransactions(null);
        assertEquals(test.getTransactions() , null);
    }

    @Test
    void removePeeopleTest() {
        Event test = new Event("Event one", "Party", 1, "1234", new ArrayList<>(), new ArrayList<>());
        Person person = new Person("test@email.com", "First", "Test",
                "iban33");
        test.addPerson(person);
        test.removeParticipants();
        assertTrue(test.getPeople().isEmpty());
        test.setPeople(null);
        assertEquals(test.getPeople() , null);
    }

    @Test
    void sameObjectEquals() {
        Event test = new Event("Event one", "Party", 1, "1234", new ArrayList<>(), new ArrayList<>());
        assertEquals(test, test);
    }

    @Test
    void nullTest() {
        Event test = new Event("Event one", "Party", 1, "1234", new ArrayList<>(), new ArrayList<>());
        assertNotEquals(test, null);
    }

    @Test
    void getDateTest() {
        Event test = new Event("Event one", "Party", 1, "1234", new ArrayList<>(), new ArrayList<>());
        assertNotNull(test.getCreationDate());
    }

    @Test
    void getLastModifiedTest() {
        Event test = new Event("Event one", "Party", 1, "1234", new ArrayList<>(), new ArrayList<>());
        test.setTag("bla");
        assertNotNull(test.getLastModified());
    }

    @Test
    void setLastModifiedTest() {
        Event test = new Event("Event one", "Party", 1, "1234", new ArrayList<>(), new ArrayList<>());
        LocalDate date = LocalDate.now();
        test.setLastModified(date);
        assertEquals(test.getLastModified(), date);
    }




    /**
     * Test the toString method.
     */
    @Test
    void toStringTest(){
        Event test = new Event("Event one", "Party", 1, "1234", new ArrayList<>(), new ArrayList<>());
        assertEquals("Event{tag='Event one', title='Party', id=1, token='1234'}", test.toString());
    }

    /**
     * Test the equals method with two similar events.
     */
    @Test
    void equalsTest(){
        Event test = new Event("Event one", "Party", 1, "1234", new ArrayList<>(), new ArrayList<>());
        Event test2 = new Event("Event one", "Party", 1, "1234", new ArrayList<>(), new ArrayList<>());
        assertEquals(test, test2);
    }

    /**
     * Test equals method with two events with different id's.
     */
    @Test
    void differentIdTest(){
        Event test = new Event("Event one", "Party", 1, "1234", new ArrayList<>(), new ArrayList<>());
        Event test2 = new Event("Event one", "Party", 2, "1234", new ArrayList<>(), new ArrayList<>());
        assertNotEquals(test, test2);
    }

    @Test
    void differentPeopleTest(){
        Person p1 = new Person("test@email.com", "First", "Test",
                "iban33");
        Person p2 = new Person("test@email.com", "First", "Test",
                "iban33");

        List<Person> people1 = new ArrayList<>();
        people1.add(p1);
        List<Person> people2 = new ArrayList<>();
        people2.add(p2);

        Event test = new Event("Event one", "Party", 1, "1234", people1, new ArrayList<>());
        Event test2 = new Event("Event one", "Party", 2, "1234", people2, new ArrayList<>());
        assertNotEquals(test, test2);
    }

    @Test
    void differentTransactionsTest(){
        Transaction t1 = new Transaction("test",
                LocalDate.of(Integer.parseInt("1970"), Integer.parseInt("10"), Integer.parseInt("10")),
                100, 947, new ArrayList<>(), new Person(), null);
        Transaction t2 = new Transaction("test",
                LocalDate.of(Integer.parseInt("1970"), Integer.parseInt("10"), Integer.parseInt("10")),
                100, 957, new ArrayList<>(), new Person(), null);

        List<Transaction> transactions1 = new ArrayList<>();
        transactions1.add(t1);
        List<Transaction> transactions2 = new ArrayList<>();
        transactions2.add(t2);

        Event test = new Event("Event one", "Party", 1, "1234", new ArrayList<>(), transactions1);
        Event test2 = new Event("Event one", "Party", 2, "1234", new ArrayList<>(), transactions2);
        assertNotEquals(test, test2);
    }

    /**
     * Test hashcode method with two similar events.
     */
    @Test
    void hashcodeTest(){
        Event test = new Event("Event one", "Party", 1, "1234", new ArrayList<>(), new ArrayList<>());
        Event test2 = new Event("Event one", "Party", 1, "1234", new ArrayList<>(), new ArrayList<>());
        assertEquals(test.hashCode(), test2.hashCode());
    }
}
