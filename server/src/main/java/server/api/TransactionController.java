package server.api;

import commons.Person;
import commons.Transaction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.database.TransactionRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/transaction")
public class TransactionController {

    private final TransactionRepository repo;

    /**
     * Constructor for the TransactionController.
     * @param repo The TransactionRepository
     */
    public TransactionController(TransactionRepository repo){
        this.repo = repo;
    }

    /**
     * This endpoint lists all the saved transactions.
     * @return returns a list with all the transactions
     */
    @GetMapping(path = {"", "/"})
    public List<Transaction> getAll(){
        return repo.findAll();
    }

    /**
     * This endpoint returns the transaction with the specified id if it exists.
     * @param id the id of the transaction to search for
     * @return returns the transaction with the specified id
     */
    @GetMapping("/{id}")
    public ResponseEntity<Transaction> getById(@PathVariable("id") int id){
        if (id < 0 || !repo.existsById(id)) {
            return ResponseEntity.badRequest().build();
        }
        if(!repo.findById(id).isPresent()){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(repo.findById(id).get());
    }

    /**
     * Adds a new transaction to the database.
     * @param transaction the transaction to add
     * @return returns the added transaction
     */
    @PostMapping(path = { "", "/" })
    public ResponseEntity<Transaction> add(@RequestBody Transaction transaction) {

        if (transaction == null || transaction.getCreator() == null || transaction.getParticipants().isEmpty() ||
                transaction.getId() < 0 || isNullOrEmpty(transaction.getName())|| transaction.getDate() == null ||
                transaction.getMoney() == 0 || transaction.getCurrency() == 0  ) {
            return ResponseEntity.badRequest().build();
        }

        Transaction saved = repo.save(transaction);
        return ResponseEntity.ok(saved);
    }

    /**
     * Checks strings to be either null or empty.
     * @param s string to check
     * @return returns true if the string is empty or null
     */
    private static boolean isNullOrEmpty(String s) {
        return s == null || s.isEmpty();
    }

    /**
     * Deletes a transaction from the database with a specified id.
     * @param id the id of the transaction to delete
     * @return returns the deleted transaction
     */
    @DeleteMapping(path = {"/{id}"})
    public ResponseEntity<Transaction> deleteById(@PathVariable("id") int id) {
        if (id < 0 || !repo.existsById(id)) {
            return ResponseEntity.badRequest().build();
        }
        if(!repo.findById(id).isPresent()){
            return ResponseEntity.badRequest().build();
        }
        ResponseEntity<commons.Transaction> response =  ResponseEntity.ok(repo.findById(id).get());
        repo.deleteById(id);
        return response;
    }

    /**
     * Updates the name of a transaction in the database with a specific id.
     * @param id the id of the transaction to update
     * @param name the changed name of the to update transaction.
     * @return returns the updated transaction
     */
    @PutMapping(path = {"/{id}/name"})
    public ResponseEntity<Transaction> updateNameById(@PathVariable("id") int id, @RequestBody String name){
        if (id < 0 || !repo.existsById(id) || isNullOrEmpty(name)) {
            return ResponseEntity.badRequest().build();
        }
        Transaction transaction = getById(id).getBody();
        if (transaction == null) {
            return ResponseEntity.badRequest().build();
        }
        transaction.setName(name);
        repo.save(transaction);
        return ResponseEntity.ok(transaction);
    }

    /**
     * Updates the set of participants of a transaction in the database with a specific id.
     * @param id the id of the transaction to update
     * @param participants the changed set of participants of the to update transaction.
     * @return returns the updated transaction
     */
    @PutMapping(path = {"/{id}/participants"})
    public ResponseEntity<Transaction> updateParticipantsById(@PathVariable("id") int id, @RequestBody Set<Person> participants){
        if (id < 0 || !repo.existsById(id) || participants == null) {
            return ResponseEntity.badRequest().build();
        }
        Transaction transaction = getById(id).getBody();
        if (transaction == null) {
            return ResponseEntity.badRequest().build();
        }
        transaction.setParticipants(participants);
        repo.save(transaction);
        return ResponseEntity.ok(transaction);
    }

    /**
     * Updates the creator of a transaction in the database with a specific id.
     * @param id the id of the transaction to update
     * @param creator the changed creator of the to update transaction.
     * @return returns the updated transaction
     */
    @PutMapping(path = {"/{id}/creator"})
    public ResponseEntity<Transaction> updateCreatorById(@PathVariable("id") int id, @RequestBody Person creator){
        if (id < 0 || !repo.existsById(id) || creator == null) {
            return ResponseEntity.badRequest().build();
        }
        Transaction transaction = getById(id).getBody();
        if (transaction == null) {
            return ResponseEntity.badRequest().build();
        }
        transaction.setCreator(creator);
        repo.save(transaction);
        return ResponseEntity.ok(transaction);
    }

    /**
     * Updates the creator of a transaction in the database with a specific id.
     * @param id the id of the transaction to update
     * @param currency the changed currency of the to update transaction.
     * @return returns the updated transaction
     */
    @PutMapping(path = {"/{id}/currency"})
    public ResponseEntity<Transaction> updateCurrencyById(@PathVariable("id") int id, @RequestBody int currency){
        if (id < 0 || !repo.existsById(id) || currency ==0) {
            return ResponseEntity.badRequest().build();
        }
        Transaction transaction = getById(id).getBody();
        if (transaction == null) {
            return ResponseEntity.badRequest().build();
        }
        transaction.setCurrency(currency);
        repo.save(transaction);
        return ResponseEntity.ok(transaction);
    }
    /**
     * Changes all the values of the transaction.
     * @param id id of a transaction
     * @param newData changed transaction.
     * @return badRequest/ ok + updated Transaction
     */
    @PutMapping(path = {"/{id}"})
    public ResponseEntity<Transaction> updateById(@PathVariable("id") int id, @RequestBody Transaction newData) {
        if (id < 0 || !repo.existsById(id)) {
            return ResponseEntity.badRequest().build();
        }
        return repo.findById(id)
                .map(existingTransaction -> {
                    // Update the properties of existingTransaction with newData
                    // For example:
                    // existingTransaction.setAmount(newData.getAmount());
                    // ... other property updates
                    return ResponseEntity.ok(repo.save(existingTransaction));
                })
                .orElseGet(() -> {
                    // If the transaction doesn't exist, you can choose to create a new one
                    // or return a not found response.
                    // Here's how you might create a new one:
                    return ResponseEntity.notFound().build();
                });
    }

    /**
     * Changes date of the transaction in BD.
     * @param id id of a transaction
     * @param date new date we want to change old transaction name to
     * @return badRequest/ ok + updated Transaction
     */
    @PutMapping(path = {"/{id}/date"})
    public ResponseEntity<Transaction> updateById(@PathVariable("id") int id, @RequestBody LocalDate date){
        if (id < 0 || !repo.existsById(id) || date == null) {
            return ResponseEntity.badRequest().build();
        }
        Transaction transaction = getById(id).getBody();

        //check if transaction is null, if it is return bad request
        if (transaction == null) {
            return ResponseEntity.badRequest().build();
        }
        transaction.setDate(date);
        repo.save(transaction);
        return ResponseEntity.ok(transaction);
    }

    /**
     * Changes money val of the transaction in DB.
     * @param id id of a transaction
     * @param money new money val we want to change old transaction money val to
     * @return badRequest/ ok + updated Transaction
     */
    @PutMapping(path = {"/{id}/money"})
    public ResponseEntity<Transaction> updateById(@PathVariable("id") int id, @RequestBody double money){
        if (id < 0 || !repo.existsById(id) || money == 0.0) {
            return ResponseEntity.badRequest().build();
        }
        Transaction transaction = getById(id).getBody();

        //check if transaction is null, if it is return bad request
        if (transaction == null) {
            return ResponseEntity.badRequest().build();
        }
        transaction.setMoney(money);
        repo.save(transaction);
        return ResponseEntity.ok(transaction);
    }

}

