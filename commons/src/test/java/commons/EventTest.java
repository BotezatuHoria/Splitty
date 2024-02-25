package commons;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class EventTest {
    /**
     * Test the getTag method
     */
    @Test
    void getTagTest(){
        Event test = new Event("Event one", "Party", 1, "1234");
        assertEquals("Event one", test.getTag());
    }

    /**
     * Test the getTitle method
     */
    @Test
    void getTitleTest(){
        Event test = new Event("Event one", "Party", 1, "1234");
        assertEquals("Party", test.getTitle());
    }

    /**
     * Test the getId method
     */
    @Test
    void getIdTest(){
        Event test = new Event("Event one", "Party", 1, "1234");
        assertEquals(1, test.getId());
    }

    /**
     * Test the getToken method
     */
    @Test
    void getTokenTest(){
        Event test = new Event("Event one", "Party", 1, "1234");
        assertEquals("1234", test.getToken());
    }

    /**
     * Test the toString method
     */
    @Test
    void toStringTest(){
        Event test = new Event("Event one", "Party", 1, "1234");
        assertEquals("Event{tag='Event one', title='Party', id=1, token='1234'}", test.toString());
    }

    /**
     * Test the equals method with two similar events
     */
    @Test
    void equalsTest(){
        Event test = new Event("Event one", "Party", 1, "1234");
        Event test2 = new Event("Event one", "Party", 1, "1234");
        assertEquals(test, test2);
    }

    /**
     * Test equals method with two events with different tags
     */
    @Test
    void DifferentTagTest(){
        Event test = new Event("Event one", "Party", 1, "1234");
        Event test2 = new Event("Event two", "Party", 1, "1234");
        assertNotEquals(test, test2);
    }

    /**
     * Test equals method with two events with different titles
     */
    @Test
    void DifferentTitleTest(){
        Event test = new Event("Event one", "Party", 1, "1234");
        Event test2 = new Event("Event one", "BBQ", 1, "1234");
        assertNotEquals(test, test2);
    }

    /**
     * Test equals method with two events with different id's
     */
    @Test
    void DifferentIdTest(){
        Event test = new Event("Event one", "Party", 1, "1234");
        Event test2 = new Event("Event one", "Party", 2, "1234");
        assertNotEquals(test, test2);
    }

    /**
     * Test equals method with two events with different tokens
     */
    @Test
    void DifferentTokenTest(){
        Event test = new Event("Event one", "Party", 1, "1234");
        Event test2 = new Event("Event one", "Party", 1, "4321");
        assertNotEquals(test, test2);
    }

    /**
     * Test hashcode method with two similar events
     */
    @Test
    void HashcodeTest(){
        Event test = new Event("Event one", "Party", 1, "1234");
        Event test2 = new Event("Event one", "Party", 1, "1234");
        assertEquals(test.hashCode(), test2.hashCode());
    }
}
