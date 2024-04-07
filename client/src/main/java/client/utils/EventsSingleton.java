package client.utils;

import commons.Event;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class EventsSingleton {
    private static final EventsSingleton instance = new EventsSingleton();
    private List<Event> events;

    public EventsSingleton() {
        this.events = new ArrayList<>();
    }

    public static EventsSingleton getInstance() {
        return instance;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public void addEvent(Event event) { this.events.add(event); }

    public Event getEventById(int id) {
        for (Event event : events) {
            if (event.getId() == id) {
                return event;
            }
        }
        return null;
    }
}
