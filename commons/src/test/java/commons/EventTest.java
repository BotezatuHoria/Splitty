package commons;

import org.junit.jupiter.api.Test;

import java.util.HashSet;

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
