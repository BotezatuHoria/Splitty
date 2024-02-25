package commons;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class TransactionTest {
    @Test
    public void checkConstructor() {
        Transaction t = new Transaction("test",
                LocalDate.of(Integer.parseInt("1970"), Integer.parseInt("10"), Integer.parseInt("10")),
                100, 947);
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
                100, 947);
        assertEquals("test", t.getName());
    }
    @Test
    public void testLocalDate() {
        Transaction t = new Transaction("test",
                LocalDate.of(Integer.parseInt("1970"), Integer.parseInt("10"), Integer.parseInt("10")),
                100, 947);
        assertEquals(1970, t.getDate().getYear());
        assertEquals(1970, t.getDate().getYear());
        assertEquals(10, t.getDate().getMonthValue());
        assertEquals(10, t.getDate().getDayOfMonth());
    }

    @Test
    public void testMoney() {
        Transaction t = new Transaction("test",
                LocalDate.of(Integer.parseInt("1970"), Integer.parseInt("10"), Integer.parseInt("10")),
                100, 947);
        assertEquals(100, t.getMoney());
    }

    @Test
    public void testCurrency() {
        Transaction t = new Transaction("test",
                LocalDate.of(Integer.parseInt("1970"), Integer.parseInt("10"), Integer.parseInt("10")),
                100, 947);
        assertEquals(947, t.getCurrency());
    }

    @Test
    public void testEquals() {
        Transaction t1 = new Transaction("test",
                LocalDate.of(Integer.parseInt("1970"), Integer.parseInt("10"), Integer.parseInt("10")),
                100, 947);
        Transaction t2 = new Transaction("test",
                LocalDate.of(Integer.parseInt("1970"), Integer.parseInt("10"), Integer.parseInt("10")),
                100, 947);
        assertEquals(t1, t2);
    }

    @Test
    public void testNotEquals() {
        Transaction t1 = new Transaction("test",
                LocalDate.of(Integer.parseInt("1970"), Integer.parseInt("10"), Integer.parseInt("10")),
                100, 947);
        Transaction t2 = new Transaction("testing",
                LocalDate.of(Integer.parseInt("1971"), Integer.parseInt("2"), Integer.parseInt("5")),
                10, 840);
        assertNotEquals(t1, t2);
    }
}