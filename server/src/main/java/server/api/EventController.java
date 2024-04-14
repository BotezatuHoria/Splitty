package server.api;

import java.util.*;

import commons.Event;

import commons.Person;
import commons.Tag;
import commons.Transaction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.services.implementations.EventServiceImplementation;


@RestController
@RequestMapping("/api/event")
public class EventController {

    private final EventServiceImplementation esi;

    /**
     * Constructor for the EventController.
     *
     * @param esi repository for the eventRepository
     */
    public EventController(EventServiceImplementation esi) {
        this.esi = esi;
    }

    /**
     * Gets all the events as a Jason file.
     *
     * @return the event
     */
    @GetMapping(path = {"", "/"})
    public ResponseEntity<List<Event>> getAll() {
        return esi.getAll();
    }


    /**
     * Method for getting and event by id from url.
     *
     * @param id of the event you want to get
     * @return the event
     */
    @GetMapping("/{id}")
    public ResponseEntity<Event> getById(@PathVariable("id") long id) {
        return esi.getById(id);
    }

    /**
     * Update method for updating an event.
     * @param id - the id of the event
     * @param event - the new structure of the event
     * @return - the event as a JSON
     */
    @PutMapping("/{id}")
    public ResponseEntity<Event> updateById(@PathVariable("id") long id, @RequestBody Event event) {
        return esi.updateById(id, event);
    }

    @PutMapping(path = {"/{id}/title"})
    public ResponseEntity<Event> updateTitleById(@PathVariable("id") long id, @RequestBody Event event){
        return esi.updateTitleById(id, event);
    }

    /**
     * Method for adding the event to a repository.
     *
     * @param event you want to add
     * @return the event you added
     */
    @PostMapping(path = {"", "/"})
    public ResponseEntity<Event> add(@RequestBody Event event) {
        return esi.add(event);
    }


    /**
     * Deletes the event with the id you provided in the url.
     *
     * @param id of the event you want to delete
     * @return the event you deleted
     */
    @DeleteMapping(path = {"/{id}"})
    public ResponseEntity<Event> deleteById(@PathVariable("id") long id) {
       return esi.deleteById(id);
    }

    /**
     * Get function for all the people in a certain event.
     * @param id - id of the event
     * @return - a set of all the people in the event
     */
    @GetMapping("/{id}/person")
    public ResponseEntity<List<Person>> getPeople(@PathVariable("id") long id) {
        return esi.getPeople(id);
    }

    /**
     * Method for adding a person to an event.
     * @param id - id of the event
     * @param person - person to be added to the event
     * @return - current state of the event
     */
    @PostMapping(path = {"/{id}/person"})
    public ResponseEntity<Person> add(@PathVariable("id") long id, @RequestBody Person person) {
       return esi.add(id, person);
    }

    /**
     * Method for retrieving all the expenses that are into an event.
     * @param idEvent - id of the event
     * @return - a set of all expenses in the event
     */
    @GetMapping(path = {"/{idEvent}/expenses"})
    public ResponseEntity<List<Transaction>> getExpenses(@PathVariable("idEvent") long idEvent) {
        return esi.getExpenses(idEvent);
    }

    /**
     * Method for adding a new expense to the event.
     * @param idEvent - id of the event.
     * @param transaction - transaction to be added.
     * @return - the state of the new event.
     */
    @PostMapping(path = {"/{idEvent}/expenses"})
    public ResponseEntity<Transaction> createNewExpense(@PathVariable("idEvent") long idEvent,
                                                        @RequestBody Transaction transaction) {
        return esi.createNewExpense(idEvent, transaction);
    }

    /**
     * deletes an transaction.
     * @param idEvent - id of the event.
     * @param idTransaction id of transaction.
     * @return deleted transaction.
     */
    @DeleteMapping(path = "/{idEvent}/expenses")
    public ResponseEntity<Transaction> deleteTransaction(@PathVariable("idEvent") long idEvent,
                                                         @RequestParam("id") int idTransaction) {
        return esi.deleteTransaction(idEvent, idTransaction);
    }

    /**
     * deletes an person.
     * @param idEvent - id of the event.
     * @param idPerson id of person.
     * @return deleted person.
     */
    @DeleteMapping(path = {"/{idEvent}/person"})
    public ResponseEntity<Person> deletePerson(@PathVariable("idEvent") long idEvent,
                                               @RequestParam("id") int idPerson) {
        return esi.deletePerson(idEvent, idPerson);
    }

    /**
     * gets the token of session.
     * @param token token of user session.
     * @return token.
     */
    @GetMapping(path = {"/token/{token}"})
    public ResponseEntity<Event> getEventByToken(@PathVariable("token") String token){
        return esi.getEventByToken(token);
    }

    /**
     * gets tag from event.
     * @param id id of event.
     * @return tags of the event.
     */
    @GetMapping(path = {"/{idEvent}/tag"})
    public ResponseEntity<List<Tag>> getAllTags(@PathVariable("idEvent") long id) {
        return esi.getTags(id);
    }

    /**
     * creates new tag for event.
     * @param id event id.
     * @param tag tag entity.
     * @return tag entity thats added.
     */
    @PostMapping(path = "/{idEvent}/tag")
    public ResponseEntity<Tag> createTag(@PathVariable("idEvent") long id, @RequestBody Tag tag) {
        return esi.createTag(id, tag);
    }

    /**
     * deletes tag from event.
     * @param id event id.
     * @param idTag id of the tag.
     * @return deleted id.
     */
    @DeleteMapping(path = "/{idEvent}/tag")
    public ResponseEntity<Tag> removeTag(@PathVariable("idEvent") long id, @RequestParam("id") int idTag) {
        return esi.deleteTag(id, idTag);
    }

}
