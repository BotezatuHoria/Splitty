package server.api;

import java.util.List;
import java.util.Random;

import commons.Event;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import commons.Quote;
import server.database.EventRepository;
import server.database.QuoteRepository;


@RestController
@RequestMapping("/api/event")
public class EventController {

    private final EventRepository repo;

    /**
     * Constructor for the EventController
     * @param repo repository for the eventRepository
     */
    public EventController(EventRepository repo) {
        this.repo = repo;
    }

    @GetMapping(path = { "", "/" })
    public List<Event> getAll() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Event> getById(@PathVariable("id") long id) {
        if (id < 0 || !repo.existsById(id)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(repo.findById(id).get());
    }

    @PostMapping(path = { "", "/" })
    public ResponseEntity<Event> add(@RequestBody Event event) {

        if (event.getId() < 0 || event.getTag() == null || event.getTitle() == null || event.getToken() == null||
        event.getPeople() == null || event.getTransactions() == null) {
            return ResponseEntity.badRequest().build();
        }

        Event saved = repo.save(event);
        return ResponseEntity.ok(saved);
    }


}
