package server.api;

import commons.Event;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EventControllerTest {



    private TestEventRepository repo;

    private EventController sut;

    @BeforeEach
    public void setup() {
        repo = new TestEventRepository();
        sut = new EventController(repo);
    }

    @Test
    void testGetAll() {
    }

    @Test
    void getById() {
    }

    @Test
    void add() {
        Event test = new Event("Event one", "Party", 1, "1234", new HashSet<>(), new HashSet<>());
        sut.add(test);
        List<Event> events = sut.getAll();
        for (Event event: events) System.out.println(event);
        events.contains(test);
    }
}