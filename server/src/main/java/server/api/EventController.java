package server.api;


import java.util.List;
import java.util.Objects;
import java.util.Set;


import commons.Event;

import commons.Person;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import server.database.EventRepository;


@RestController
@RequestMapping("/api/event")
public class EventController {

    private final EventRepository repo;
    private PersonController pc;

    /**
     * Constructor for the EventController.
     *
     * @param repo repository for the eventRepository
     */
    public EventController(EventRepository repo, PersonController pc) {
        this.repo = repo;
        this.pc = pc;
    }

    /**
     * Gets all the events as a Jason file.
     *
     * @return
     */
    @GetMapping(path = {"", "/"})
    public List<Event> getAll() {
        return repo.findAll();
    }


    /**
     * Method for getting and event by id from url.
     *
     * @param id of the event you want to get
     * @return the event
     */
    @GetMapping("/{id}")
    public ResponseEntity<Event> getById(@PathVariable("id") long id) {
        if (id < 0 || !repo.existsById(id)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(repo.findById(id).get());
    }

    /**
     * Method for adding the event to a repository.
     *
     * @param event you want to add
     * @return the event you added
     */
    @PostMapping(path = {"", "/"})
    public ResponseEntity<Event> add(@RequestBody Event event) {

        if (event.getId() < 0 || event.getTag() == null || event.getTitle() == null || event.getToken() == null ||
                event.getPeople() == null || event.getTransactions() == null) {
            return ResponseEntity.badRequest().build();
        }

        Event saved = repo.save(event);
        return ResponseEntity.ok(saved);
    }


    /**
     * Deletes the event with the id you provided in the url.
     *
     * @param id of the event you want to delete
     * @return the event you deleted
     */
    @DeleteMapping(path = {"/{id}"})
    public ResponseEntity<Event> deleteById(@PathVariable("id") long id) {
        if (id < 0 || !repo.existsById(id)) {
            return ResponseEntity.badRequest().build();
        }
        ResponseEntity<commons.Event> response = ResponseEntity.ok(repo.findById(id).get());
        repo.deleteById(id);
        return response;
    }

    @GetMapping("/{id}/people")
    public ResponseEntity<Set<Person>> getPeople(@PathVariable("id") long id) {
        if (id < 0 || !repo.existsById(id)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(repo.findById(id).get().getPeople());
    }

    @PutMapping(path = {"/{id}/person"})
    public ResponseEntity<Event> add(@PathVariable("id") long id, @RequestBody Person person) {
        person.setEvent((int) id);
        pc.add(person);
        Event event = getById(id).getBody();
        event.addPerson(person);
        Event saved = repo.save(event);
        return ResponseEntity.ok(saved);
    }

    @DeleteMapping(path = {"/{idEvent}/person/{idPerson}"})
    public ResponseEntity<Event> deleteById(@PathVariable("idEvent") long idEvent,
                                            @PathVariable("idPerson") int idPerson) {
        if (Objects.equals(pc.getById(idPerson), ResponseEntity.badRequest())) {
            return ResponseEntity.badRequest().build();
        }
        if ((idEvent < 0 || !repo.existsById(idEvent) || idPerson < 0)) {
            return ResponseEntity.badRequest().build();
        }
        Event event = getById(idEvent).getBody();
        event.removePerson(pc.getById(idPerson).getBody());
        Event saved = repo.save(event);
        ResponseEntity<Event> response = ResponseEntity.ok(saved);
        pc.deleteById(idPerson);
        return response;
    }
}