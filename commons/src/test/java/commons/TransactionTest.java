package commons;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashSet;

public class TransactionTest {
    @Test
    public void checkConstructor() {
        Transaction t = new Transaction("test",
                LocalDate.of(Integer.parseInt("1970"), Integer.parseInt("10"), Integer.parseInt("10")),
                100, 947, new HashSet<>(), new Person());
        assertEquals("test", t.getName());
        assertEquals(1970, t.getDate().getYear());
        assertEquals(10, t.getDate().getMonthValue());
        assertEquals(10, t.getDate().getDayOfMonth());
        assertEquals(100, t.getMoney());
        assertEquals(947, t.getCurrency());
    }

    @Test
    public void testName() {
        Transaction t = new Transaction("test",
                LocalDate.of(Integer.parseInt("1970"), Integer.parseInt("10"), Integer.parseInt("10")),
                100, 947, new HashSet<>(), new Person());
        assertEquals("test", t.getName());
    }
    @Test
    public void testLocalDate() {
        Transaction t = new Transaction("test",
                LocalDate.of(Integer.parseInt("1970"), Integer.parseInt("10"), Integer.parseInt("10")),
                100, 947, new HashSet<>(), new Person());
        assertEquals(1970, t.getDate().getYear());
        assertEquals(10, t.getDate().getMonthValue());
        assertEquals(10, t.getDate().getDayOfMonth());
    }

    @Test
    public void testMoney() {
        Transaction t = new Transaction("test",
                LocalDate.of(Integer.parseInt("1970"), Integer.parseInt("10"), Integer.parseInt("10")),
                100, 947, new HashSet<>(), new Person());
        assertEquals(100, t.getMoney());
    }

    @Test
    public void testCurrency() {
        Transaction t = new Transaction("test",
                LocalDate.of(Integer.parseInt("1970"), Integer.parseInt("10"), Integer.parseInt("10")),
                100, 947, new HashSet<>(), new Person());
        assertEquals(947, t.getCurrency());
    }

    @Test
    public void testEquals() {
        Transaction t1 = new Transaction("test",
                LocalDate.of(Integer.parseInt("1970"), Integer.parseInt("10"), Integer.parseInt("10")),
                100, 947, new HashSet<>(), new Person());
        Transaction t2 = new Transaction("test",
                LocalDate.of(Integer.parseInt("1970"), Integer.parseInt("10"), Integer.parseInt("10")),
                100, 947, new HashSet<>(), new Person());
        assertEquals(t1, t2);
    }

    @Test
    public void testNotEquals() {
        Transaction t1 = new Transaction("test",
                LocalDate.of(Integer.parseInt("1970"), Integer.parseInt("10"), Integer.parseInt("10")),
                100, 947, new HashSet<>(), new Person());
        Transaction t2 = new Transaction("testing",
                LocalDate.of(Integer.parseInt("1971"), Integer.parseInt("2"), Integer.parseInt("5")),
                10, 840, new HashSet<>(), new Person());
        assertNotEquals(t1, t2);
    }

    @Test
    public void setNameTest() {
        Transaction t1 = new Transaction("test",
                LocalDate.of(Integer.parseInt("1970"), Integer.parseInt("10"), Integer.parseInt("10")),
                100, 947, new HashSet<>(), new Person());
        t1.setName("new test");
        assertEquals("new test", t1.getName());
    }

    @Test
    public void setLocalDateTest() {
        Transaction t = new Transaction("test",
                LocalDate.of(Integer.parseInt("1970"), Integer.parseInt("10"), Integer.parseInt("10")),
                100, 947, new HashSet<>(), new Person());
        t.setDate(LocalDate.of(Integer.parseInt("1999"), Integer.parseInt("1"), Integer.parseInt("30")));
        assertEquals(1999, t.getDate().getYear());
        assertEquals(1, t.getDate().getMonthValue());
        assertEquals(30, t.getDate().getDayOfMonth());
    }

    @Test
    public void setMoneyTest() {
        Transaction t = new Transaction("test",
                LocalDate.of(Integer.parseInt("1970"), Integer.parseInt("10"), Integer.parseInt("10")),
                100, 947, new HashSet<>(), new Person());
        t.setMoney(90);
        assertEquals(90, t.getMoney());
    }

    @Test
    public void setCurrencyTest() {
        Transaction t = new Transaction("test",
                LocalDate.of(Integer.parseInt("1970"), Integer.parseInt("10"), Integer.parseInt("10")),
                100, 947, new HashSet<>(), new Person());
        t.setCurrency(840);
        assertEquals(840, t.getCurrency());
    }
}