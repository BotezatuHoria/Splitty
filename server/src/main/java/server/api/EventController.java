package server.api;


import java.util.List;
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
    private  PersonController pc;

    /**
     * Constructor for the EventController.
     * @param repo repository for the eventRepository
     */
    public EventController(EventRepository repo, PersonController pc) {
        this.repo = repo;
        this.pc = pc;
    }

    /**
     * Gets all the events as a Jason file.
     * @return
     */
    @GetMapping(path = { "", "/" })
    public List<Event> getAll() {
        return repo.findAll();
    }


    /**
     * Method for getting and event by id from url.
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
     * @param event you want to add
     * @return the event you added
     */
    @PostMapping(path = { "", "/" })
    public ResponseEntity<Event> add(@RequestBody Event event) {

        if (event.getId() < 0 || event.getTag() == null || event.getTitle() == null || event.getToken() == null||
        event.getPeople() == null || event.getTransactions() == null) {
            return ResponseEntity.badRequest().build();
        }

        Event saved = repo.save(event);
        return ResponseEntity.ok(saved);
    }


    /**
     * Deletes the event with the id you provided in the url.
     * @param id of the event you want to delete
     * @return the event you deleted
     */
    @DeleteMapping(path = {"/{id}"})
    public ResponseEntity<Event> deleteById(@PathVariable("id") long id) {
        if (id < 0 || !repo.existsById(id)) {
            return ResponseEntity.badRequest().build();
        }
        ResponseEntity<commons.Event> response =  ResponseEntity.ok(repo.findById(id).get());
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



    //@GetMapping("/{id}/people")
    //public ResponseEntity<Set<Person>> getPeople(@PathVariable("id") long id) {
        //if (id < 0 || repo.existsById(id)) {
            //System.out.println(id);
            //return ResponseEntity.badRequest().build();
        }
        //return null;
        //return ResponseEntity.ok(repo.findById(id).get().getPeople());
    //}
     //Method for adding person
    //@PostMapping(path = {"/{id}/person/"})
    //public ResponseEntity<Person> addPerson(@RequestBody Person person, @PathVariable("id") long id) {
        //if (id < 0 || !repo.existsById(id)) {
            //return ResponseEntity.badRequest().build();
        //}

        // Add to repository for person
        // Access the event and add the person
        //return null;
    //}

    // Add method for deleting person
    // Add method for editing person


//}
