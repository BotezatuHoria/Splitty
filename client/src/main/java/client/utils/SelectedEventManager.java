package client.utils;

import jakarta.inject.Inject;
import org.springframework.stereotype.Component;

@Component
public class SelectedEventManager {
    private int eventId;

    @Inject
    public SelectedEventManager() {}

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }
}