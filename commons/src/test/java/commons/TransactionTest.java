package commons;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
}