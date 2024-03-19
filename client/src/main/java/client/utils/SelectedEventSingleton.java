package client.utils;

import org.springframework.stereotype.Component;

@Component
public class SelectedEventSingleton {
    private static final SelectedEventSingleton instance = new SelectedEventSingleton();
    private int eventId;

    public SelectedEventSingleton() {}

    public static SelectedEventSingleton getInstance() {
        return instance;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }
}
