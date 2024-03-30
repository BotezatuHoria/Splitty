package server.services.implementations;

import commons.Event;
import commons.Person;
import commons.Transaction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import server.database.EventRepository;
import server.services.interfaces.EventService;

import java.util.List;

@Service
public class EventServiceImplementation implements EventService {

    private final EventRepository repo;
    private final TransactionServiceImplementation tsi;
    private final PersonServiceImplementation psi;

    public EventServiceImplementation(EventRepository repo, TransactionServiceImplementation tsi,
                                      PersonServiceImplementation psi) {
        this.repo = repo;
        this.tsi = tsi;
        this.psi = psi;
    }

    @Override
    public ResponseEntity<List<Event>> getAll() {
        List<Event> events = repo.findAll();
        if (events.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(events);
        }
    }

    @Override
    public ResponseEntity<Event> getById(long id) {
        var res = repo.findById(id);
        return res.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<Event> updateById(long id, Event event) {
        if (event == null || event.getId() < 0 || !repo.existsById(id) || event.getId() != id ||
                event.getTag() == null || event.getTitle() == null || event.getToken() == null ||
                event.getPeople() == null || event.getTransactions() == null) {
            return ResponseEntity.badRequest().build();
        }
        return repo.findById(id)
                .map(existingEvent -> {
                    existingEvent.setTitle(event.getTitle());
                    existingEvent.setTag(event.getTag());
                    existingEvent.setToken(event.getToken());
                    existingEvent.setPeople(event.getPeople());
                    existingEvent.setTransactions(event.getTransactions());
                    return ResponseEntity.ok(repo.save(existingEvent));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<Event> add(Event event) {
        if (event == null || repo.existsById((long) event.getId()) || event.getId() < 0 || event.getTag() == null
                || event.getTitle() == null || event.getToken() == null ||
                event.getPeople() == null || event.getTransactions() == null) {
            return ResponseEntity.badRequest().build();
        }
        Event saved = repo.save(event);
        return ResponseEntity.ok(saved);
    }

    @Override
    public ResponseEntity<Event> deleteById(long id) {
        if (id < 0 || !repo.existsById(id)) {
            return ResponseEntity.badRequest().build();
        }
        Event event = repo.findById(id).get();
        event.removeTransactions();
        event.removeParticipants();
        ResponseEntity<commons.Event> response = ResponseEntity.ok(event);
        repo.deleteById(id);
        return response;
    }

    @Override
    public ResponseEntity<List<Person>> getPeople(long id) {
        if (id < 0 || !repo.existsById(id)) {
            return ResponseEntity.badRequest().build();
        }
        if (repo.findById(id).isPresent()) {
            return ResponseEntity.ok(repo.findById(id).get().getPeople());
        }
        return ResponseEntity.internalServerError().build();
    }

    @Override
    public ResponseEntity<Person> add(long id, Person person) {
        if (id < 0 || !repo.existsById(id)) {
            return ResponseEntity.badRequest().build();
        }
        if (repo.findById(id).isPresent()) {
            Person savedPerson = psi.add(person).getBody();
            Event event = repo.findById(id).get();
            event.addPerson(savedPerson);
            repo.save(event);
            return ResponseEntity.ok(savedPerson);
        }
        return ResponseEntity.internalServerError().build();
    }


    @Override
    public ResponseEntity<List<Transaction>> getExpenses(long idEvent) {
        if (idEvent < 0 || !repo.existsById(idEvent)) {
            return ResponseEntity.badRequest().build();
        }
        if (repo.findById(idEvent).isPresent()) {
            return ResponseEntity.ok(repo.findById(idEvent).get().getTransactions());
        }
        return ResponseEntity.internalServerError().build();
    }

    @Override
    public ResponseEntity<Transaction> createNewExpense(long idEvent, Transaction transaction) {
        if (idEvent < 0 || !repo.existsById(idEvent)) {
            return ResponseEntity.badRequest().build();
        }
        if (repo.findById(idEvent).isPresent()) {
            Transaction savedTransaction = tsi.add(transaction).getBody();
            if (savedTransaction != null) {
                debtCalc(savedTransaction);
            }
            Event event = repo.findById(idEvent).get();
            event.addTransaction(savedTransaction);
            updateById(idEvent, event);
            return ResponseEntity.ok(savedTransaction);
        }
        return ResponseEntity.internalServerError().build();
    }

    public ResponseEntity<Person> deletePerson(long idEvent, int idPerson) {
        if (idEvent < 0 || !repo.existsById(idEvent)) {
            return ResponseEntity.badRequest().build();
        }
        try {
            Event event = getById(idEvent).getBody();
            Person person = psi.getById(idPerson).getBody();
            if (event != null) {
                List<Transaction> transactions = event.getTransactions();
                transactions.removeIf(t -> t.getCreator().getId() == idPerson);
                transactions = event.getTransactions();
                for (Transaction t : transactions) {
                    if (t.getParticipants().contains(person)) {
                        List<Person> participants = t.getParticipants();
                        participants.remove(person);
                        t.setParticipants(participants);
                    }
                }
                event.setTransactions(transactions);
                event.removePerson(person);
                updateById(idEvent, event);
                return ResponseEntity.ok(person);
            }
            return ResponseEntity.internalServerError().build();
        }
        catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    public ResponseEntity<Transaction> deleteTransaction(long idEvent, int idTransaction) {
        if (idEvent < 0 || !repo.existsById(idEvent) || tsi.getById(idTransaction) == null) {
            return ResponseEntity.badRequest().build();
        }
        try {
            Event event = getById(idEvent).getBody();
            Transaction t = tsi.getById(idTransaction).getBody();
            if (event != null) {
                event.removeTransaction(t);
                updateById(idEvent, event);
                System.out.println(t);
                return ResponseEntity.ok(t);
            }
            return ResponseEntity.internalServerError().build();
        }
        catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    public void debtCalc(Transaction transaction) {
        double money = transaction.getMoney();
        int people = transaction.getParticipants().size();
        Person creator = transaction.getCreator();
        double debt = 0;
        debt = money / people;
        Person crt = psi.getById(creator.getId()).getBody();
        crt.setDebt(crt.getDebt() + money);
        for (Person p : transaction.getParticipants()) {
            Person person = psi.getById(p.getId()).getBody();
            person.setDebt(person.getDebt() - debt);
            psi.updateById(p.getId(), person);
        }
    }
}
