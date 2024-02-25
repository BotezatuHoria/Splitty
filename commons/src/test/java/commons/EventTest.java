package commons;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class EventTest {

    @Test
    void getTagTest(){
        Event test = new Event("Event one", "Party", 1, "1234");
        assertEquals("Event one", test.getTag());
    }

    @Test
    void getTitleTest(){
        Event test = new Event("Event one", "Party", 1, "1234");
        assertEquals("Party", test.getTitle());
    }

    @Test
    void getIdTest(){
        Event test = new Event("Event one", "Party", 1, "1234");
        assertEquals(1, test.getId());
    }

    @Test
    void getTokenTest(){
        Event test = new Event("Event one", "Party", 1, "1234");
        assertEquals("1234", test.getToken());
    }

    @Test
    void toStringTest(){
        Event test = new Event("Event one", "Party", 1, "1234");
        assertEquals("Event{tag='Event one', title='Party', id=1, token='1234'}", test.toString());
    }

    @Test
    void equalsTest(){
        Event test = new Event("Event one", "Party", 1, "1234");
        Event test2 = new Event("Event one", "Party", 1, "1234");
        assertEquals(test, test2);
    }

    @Test
    void DifferentTagTest(){
        Event test = new Event("Event one", "Party", 1, "1234");
        Event test2 = new Event("Event two", "Party", 1, "1234");
        assertNotEquals(test, test2);
    }

    @Test
    void DifferentTitleTest(){
        Event test = new Event("Event one", "Party", 1, "1234");
        Event test2 = new Event("Event one", "BBQ", 1, "1234");
        assertNotEquals(test, test2);
    }

    @Test
    void DifferentIdTest(){
        Event test = new Event("Event one", "Party", 1, "1234");
        Event test2 = new Event("Event one", "Party", 2, "1234");
        assertNotEquals(test, test2);
    }

    @Test
    void DifferentTokenTest(){
        Event test = new Event("Event one", "Party", 1, "1234");
        Event test2 = new Event("Event one", "Party", 1, "ABCD");
        assertNotEquals(test, test2);
    }

    @Test
    void HashcodeTest(){
        Event test = new Event("Event one", "Party", 1, "1234");
        Event test2 = new Event("Event one", "Party", 1, "1234");
        assertEquals(test.hashCode(), test2.hashCode());
    }
}
