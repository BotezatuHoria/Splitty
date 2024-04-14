package commons;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TagTest {

    @Test
    public void testConstructor() {
        String testTitle = "Test";
        Tag tag = new Tag(testTitle);
        assertEquals(testTitle, tag.getTitle());
    }

    @Test
    public void testGetId(){
        String testTitle = "Test";
        Tag tag = new Tag(testTitle);
        assertEquals(tag.getId(), 0);
    }

    @Test
    public void testSetId(){
        String testTitle = "Funny";
        Tag tag = new Tag(testTitle);
        assertEquals(tag.getId(), 0);
        tag.setId(2);
        assertEquals(tag.getId(), 2);
    }

    @Test
    public void testSettersTitle() {
        String testTitle1 = "Test1";
        String testTitle2 = "Test2";
        Tag tag = new Tag();

        tag.setTitle(testTitle1);
        assertEquals(testTitle1, tag.getTitle());

        tag.setTitle(testTitle2);
        assertEquals(testTitle2, tag.getTitle());
    }

    @Test
    public void testEquals() {
        Tag tag1 = new Tag("Test1");
        Tag tag2 = new Tag("Test1");
        Tag tag3 = new Tag("Test2");
        assertTrue(tag1.equals(tag1));
        assertTrue(tag1.equals(tag2));
        assertFalse(tag1.equals(tag3));
        assertFalse(tag1.equals(null));
    }

    @Test
    public void testHashCode() {
        Tag tag1 = new Tag("Test1");
        Tag tag2 = new Tag("Test1");
        Tag tag3 = new Tag("Test2");
        assertEquals(tag1.hashCode(), tag2.hashCode());
        assertNotEquals(tag1.hashCode(), tag3.hashCode());
    }

    @Test
    public void testToString(){
        String testTitle = "Test";
        Tag tag = new Tag(testTitle);
        assertEquals("Test", tag.toString());
    }
}