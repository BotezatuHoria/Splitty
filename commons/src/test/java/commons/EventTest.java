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
}
