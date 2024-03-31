package commons;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

public class TransactionTest {
    /**
     * Test.
     */
    @Test
    public void checkConstructor() {
        Transaction t = new Transaction("test",
                LocalDate.of(Integer.parseInt("1970"), Integer.parseInt("10"), Integer.parseInt("10")),
                100, 947, new ArrayList<>(), new Person(), null);
        assertEquals("test", t.getName());
        assertEquals(1970, t.getDate().getYear());
        assertEquals(10, t.getDate().getMonthValue());
        assertEquals(10, t.getDate().getDayOfMonth());
        assertEquals(100, t.getMoney());
        assertEquals(947, t.getCurrency());
    }

    /**
     * Test.
     */
    @Test
    public void testName() {
        Transaction t = new Transaction("test",
                LocalDate.of(Integer.parseInt("1970"), Integer.parseInt("10"), Integer.parseInt("10")),
                100, 947, new ArrayList<>(), new Person(), null);
        assertEquals("test", t.getName());
    }

    /**
     * Test.
     */
    @Test
    public void testLocalDate() {
        Transaction t = new Transaction("test",
                LocalDate.of(Integer.parseInt("1970"), Integer.parseInt("10"), Integer.parseInt("10")),
                100, 947, new ArrayList<>(), new Person(), null);
        assertEquals(1970, t.getDate().getYear());
        assertEquals(10, t.getDate().getMonthValue());
        assertEquals(10, t.getDate().getDayOfMonth());
    }

    /**
     * Test.
     */
    @Test
    public void testMoney() {
        Transaction t = new Transaction("test",
                LocalDate.of(Integer.parseInt("1970"), Integer.parseInt("10"), Integer.parseInt("10")),
                100, 947, new ArrayList<>(), new Person(), null);
        assertEquals(100, t.getMoney());
    }

    /**
     * Test.
     */
    @Test
    public void testCurrency() {
        Transaction t = new Transaction("test",
                LocalDate.of(Integer.parseInt("1970"), Integer.parseInt("10"), Integer.parseInt("10")),
                100, 947, new ArrayList<>(), new Person(), null);
        assertEquals(947, t.getCurrency());
    }

    /**
     * Test.
     */
    @Test
    public void testEquals() {
        Transaction t1 = new Transaction("test",
                LocalDate.of(Integer.parseInt("1970"), Integer.parseInt("10"), Integer.parseInt("10")),
                100, 947, new ArrayList<>(), new Person(), null);
        Transaction t2 = new Transaction("test",
                LocalDate.of(Integer.parseInt("1970"), Integer.parseInt("10"), Integer.parseInt("10")),
                100, 947, new ArrayList<>(), new Person(), null);
        assertEquals(t1, t2);
    }

    /**
     * Test.
     */
    @Test
    public void testNotEquals() {
        Transaction t1 = new Transaction("test",
                LocalDate.of(Integer.parseInt("1970"), Integer.parseInt("10"), Integer.parseInt("10")),
                100, 947, new ArrayList<>(), new Person(), null);
        assertNotEquals(t1, null);
    }

    /**
     * Test.
     */
    @Test
    public void setNameTest() {
        Transaction t1 = new Transaction("test",
                LocalDate.of(Integer.parseInt("1970"), Integer.parseInt("10"), Integer.parseInt("10")),
                100, 947, new ArrayList<>(), new Person(), null);
        t1.setName("new test");
        assertEquals("new test", t1.getName());
    }

    /**
     * Test.
     */
    @Test
    public void setLocalDateTest() {
        Transaction t = new Transaction("test",
                LocalDate.of(Integer.parseInt("1970"), Integer.parseInt("10"), Integer.parseInt("10")),
                100, 947, new ArrayList<>(), new Person(), null);
        t.setDate(LocalDate.of(Integer.parseInt("1999"), Integer.parseInt("1"), Integer.parseInt("30")));
        assertEquals(1999, t.getDate().getYear());
        assertEquals(1, t.getDate().getMonthValue());
        assertEquals(30, t.getDate().getDayOfMonth());
    }

    /**
     * Test.
     */
    @Test
    public void setMoneyTest() {
        Transaction t = new Transaction("test",
                LocalDate.of(Integer.parseInt("1970"), Integer.parseInt("10"), Integer.parseInt("10")),
                100, 947, new ArrayList<>(), new Person(), null);
        t.setMoney(90);
        assertEquals(90, t.getMoney());
    }

    /**
     * Test.
     */
    @Test
    public void setCurrencyTest() {
        Transaction t = new Transaction("test",
                LocalDate.of(Integer.parseInt("1970"), Integer.parseInt("10"), Integer.parseInt("10")),
                100, 947, new ArrayList<>(), new Person(), null);
        t.setCurrency(840);
        assertEquals(840, t.getCurrency());
    }
}